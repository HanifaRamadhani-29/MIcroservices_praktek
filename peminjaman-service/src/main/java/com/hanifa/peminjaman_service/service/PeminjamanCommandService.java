package com.hanifa.peminjaman_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanifa.peminjaman_service.event.PeminjamanCreatedEvent;
import com.hanifa.peminjaman_service.event.PeminjamanDeletedEvent;
import com.hanifa.peminjaman_service.model.Peminjaman;
import com.hanifa.peminjaman_service.repository.PeminjamanRepository;
import com.hanifa.peminjaman_service.vo.Anggota;
import com.hanifa.peminjaman_service.vo.Buku;
import com.hanifa.peminjaman_service.vo.ResponseTemplate;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // NEW
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

@Service
public class PeminjamanCommandService {
    @Autowired
    private PeminjamanRepository PeminjamanRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Autowired
    private EmailService emailService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMMM-yyyy", new Locale("id", "ID"));

    @Transactional // Ensures atomicity: save + send event + send email

    public Peminjaman createPeminjaman(Peminjaman peminjaman) {
        peminjaman.setTanggalPeminjaman(LocalDate.now());

        Peminjaman savedPeminjaman = PeminjamanRepository.save(peminjaman);

        ServiceInstance anggotaServiceInstance = discoveryClient.getInstances("anggota-service").get(0);
        Anggota anggota = restTemplate.getForObject(anggotaServiceInstance.getUri() + "/api/anggota/"
                + savedPeminjaman.getAnggotaId(), Anggota.class);

        ServiceInstance bukuServiceInstance = discoveryClient.getInstances("buku-service").get(0);
        Buku buku = restTemplate.getForObject(bukuServiceInstance.getUri() + "/api/buku/"
                + savedPeminjaman.getBukuId(), Buku.class);

        if (anggota != null && anggota.getEmail() != null) {
            String subject = "Peminjaman Buku Berhasil!";

            String formattedTanggalPeminjaman = savedPeminjaman.getTanggalPeminjaman().format(DATE_FORMATTER);
            String formattedTanggalPengembalian = savedPeminjaman.getTanggalPengembalian().format(DATE_FORMATTER);

            String body = String.format(
                "Yth. Bapak/Ibu %s,\n\n" +
                "Anda telah berhasil melakukan peminjaman buku dengan rincian sebagai berikut:\n\n" +
                "--- Rincian Peminjaman ---\n" +
                "ID Peminjaman: %d\n" +
                "Judul Buku: %s\n" +
                "Tanggal Pinjam: %s\n" +
                "Tanggal Kembali: %s\n" +
                "\n\n" +
                "Tanggal Harus Kembali: %s\n" +
                "--------------------------\n\n" +
                "Mohon untuk mengembalikan buku tepat waktu untuk menghindari denda. Terima kasih.\n\n" +
                "Salam,\n" +
                "Admin Perpustakaan",
                anggota.getNama(),
                savedPeminjaman.getId(),
                buku != null ? buku.getJudul() : "Judul Tidak Diketahui",
                formattedTanggalPeminjaman,
                formattedTanggalPengembalian
            );

            emailService.sendEmail(anggota.getEmail(), subject, body);
        }

        PeminjamanCreatedEvent event = new PeminjamanCreatedEvent(
                savedPeminjaman.getId(),
                savedPeminjaman.getAnggotaId(),
                savedPeminjaman.getBukuId(),
                savedPeminjaman.getTanggalPeminjaman(),
                savedPeminjaman.getTanggalPengembalian(),
                anggota != null ? anggota.getNama() : null,
                anggota != null ? anggota.getEmail() : null,
                buku != null ? buku.getJudul() : null
        );

        return savedPeminjaman;
    }

    @Transactional

    public void deletePeminjaman(Long id) {
        PeminjamanRepository.deleteById(id);
        // NEW: Publish PeminjamanDeletedEvent to RabbitMQ
        PeminjamanDeletedEvent event = new PeminjamanDeletedEvent(id);
        rabbitMQProducer.sendEvent(event, "deleted"); // Send event with type "deleted"
    }
}