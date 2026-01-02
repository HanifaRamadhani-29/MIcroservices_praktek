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

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMMM-yyyy",
            new Locale("id", "ID"));

    @Transactional
    public Peminjaman createPeminjaman(Peminjaman peminjaman) {
        System.out.println("Start createPeminjaman");

        // Set tanggal peminjaman sekarang
        if (peminjaman.getTanggalPeminjaman() == null) {
            peminjaman.setTanggalPeminjaman(LocalDate.now());
        }

        // Simpan ke database
        Peminjaman savedPeminjaman;
        try {
            savedPeminjaman = PeminjamanRepository.save(peminjaman);
        } catch (Exception e) {
            System.out.println("Error saat menyimpan peminjaman: " + e.getMessage());
            throw e; // lempar lagi supaya Spring menandai 500
        }

        System.out.println("Peminjaman disimpan: " + savedPeminjaman.getId());

        Anggota anggota = null;
        Buku buku = null;

        try {
            // Ambil anggota
            if (!discoveryClient.getInstances("anggota-service").isEmpty()) {
                ServiceInstance anggotaInstance = discoveryClient.getInstances("anggota-service").get(0);
                anggota = restTemplate.getForObject(
                        anggotaInstance.getUri() + "/api/anggota/" + savedPeminjaman.getAnggotaId(),
                        Anggota.class);
            }

            // Ambil buku
            if (!discoveryClient.getInstances("buku-service").isEmpty()) {
                ServiceInstance bukuInstance = discoveryClient.getInstances("buku-service").get(0);
                buku = restTemplate.getForObject(
                        bukuInstance.getUri() + "/api/buku/" + savedPeminjaman.getBukuId(),
                        Buku.class);
            }
        } catch (Exception e) {
            System.out.println("Warning: gagal ambil data anggota/buku: " + e.getMessage());
        }

        // Format tanggal aman
        String formattedTanggalPeminjaman = savedPeminjaman.getTanggalPeminjaman() != null
                ? savedPeminjaman.getTanggalPeminjaman().format(DATE_FORMATTER)
                : "Tanggal Tidak Tersedia";

        String formattedTanggalPengembalian = savedPeminjaman.getTanggalPengembalian() != null
                ? savedPeminjaman.getTanggalPengembalian().format(DATE_FORMATTER)
                : "Tanggal Tidak Tersedia";

        // Kirim email
        try {
            if (anggota != null && anggota.getEmail() != null) {
                String subject = "Peminjaman Buku Berhasil!";
                String body = String.format(
                        "Yth. Bapak/Ibu %s,\n\n" +
                                "Anda telah berhasil melakukan peminjaman buku dengan rincian sebagai berikut:\n\n" +
                                "--- Rincian Peminjaman ---\n" +
                                "ID Peminjaman: %d\n" +
                                "Judul Buku: %s\n" +
                                "Tanggal Pinjam: %s\n" +
                                "Tanggal Kembali: %s\n" +
                                "--------------------------\n\n" +
                                "Mohon untuk mengembalikan buku tepat waktu untuk menghindari denda. Terima kasih.\n\n"
                                +
                                "Salam,\nAdmin Perpustakaan",
                        anggota.getNama(),
                        savedPeminjaman.getId(),
                        buku != null ? buku.getJudul() : "Judul Tidak Diketahui",
                        formattedTanggalPeminjaman,
                        formattedTanggalPengembalian);
                emailService.sendEmail(anggota.getEmail(), subject, body);
                System.out.println("Email berhasil dikirim ke: " + anggota.getEmail());
            }
        } catch (Exception e) {
            System.out.println("Warning: gagal kirim email: " + e.getMessage());
        }

        // Publish event
        try {
            PeminjamanCreatedEvent event = new PeminjamanCreatedEvent(
                    savedPeminjaman.getId(),
                    savedPeminjaman.getAnggotaId(),
                    savedPeminjaman.getBukuId(),
                    savedPeminjaman.getTanggalPeminjaman(),
                    savedPeminjaman.getTanggalPengembalian(),
                    anggota != null ? anggota.getNama() : null,
                    anggota != null ? anggota.getEmail() : null,
                    buku != null ? buku.getJudul() : null);
            rabbitMQProducer.sendEvent(event, "created");
            System.out.println("Event created dipublish ke RabbitMQ");
        } catch (Exception e) {
            System.out.println("Warning: gagal publish event: " + e.getMessage());
        }

        return savedPeminjaman;
    }

    @Transactional
    public void deletePeminjaman(Long id) {
        if (PeminjamanRepository.existsById(id)) {
            PeminjamanRepository.deleteById(id);
            System.out.println("Peminjaman dengan ID " + id + " dihapus");

            try {
                PeminjamanDeletedEvent event = new PeminjamanDeletedEvent(id);
                rabbitMQProducer.sendEvent(event, "deleted");
                System.out.println("Event deleted dipublish ke RabbitMQ");
            } catch (Exception e) {
                System.out.println("Warning: gagal publish delete event: " + e.getMessage());
            }
        } else {
            System.out.println("Peminjaman dengan ID " + id + " tidak ditemukan.");
        }
    }

}