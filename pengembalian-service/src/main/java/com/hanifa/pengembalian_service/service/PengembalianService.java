package com.hanifa.pengembalian_service.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.hanifa.pengembalian_service.model.Pengembalian;
import com.hanifa.pengembalian_service.repository.PengembalianRepository;
import com.hanifa.pengembalian_service.vo.Anggota;
import com.hanifa.pengembalian_service.vo.Buku;
import com.hanifa.pengembalian_service.vo.Peminjaman;
import com.hanifa.pengembalian_service.vo.ResponseTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;

@Service
public class PengembalianService {
        @Autowired
        private PengembalianRepository PengembalianRepository;

        @Autowired
        private RestTemplate restTemplate;

        @Autowired
        private DiscoveryClient discoveryClient;
        private static final double FINE_PER_DAY = 1000.0;

        public List<Pengembalian> getAllPengembalians() {
                return PengembalianRepository.findAll();
        }

        public Pengembalian getPengembalianById(Long id) {
                return PengembalianRepository.findById(id).orElse(null);
        }

        public Pengembalian createPengembalian(Pengembalian pengembalian) {
                // Fetch Peminjaman details to get the expected return date
                ServiceInstance peminjamanServiceInstance = discoveryClient.getInstances("peminjaman-service").get(0);
                Peminjaman peminjaman = restTemplate
                                .getForObject(peminjamanServiceInstance.getUri() + "/api/peminjaman/"
                                                + pengembalian.getPeminjamanId(), Peminjaman.class);

                if (peminjaman != null && peminjaman.getTanggalPengembalian() != null) {
                        LocalDate expectedReturnDate = peminjaman.getTanggalPengembalian(); // <--- FIXED: Removed
                                                                                            // .toLocalDate()
                        LocalDate actualReturnDate = pengembalian.getTanggal_dikembalikan().toLocalDate();

                        long daysLate = ChronoUnit.DAYS.between(expectedReturnDate, actualReturnDate);

                        if (daysLate > 0) {
                                pengembalian.setTerlambat(daysLate + " hari");
                                double fine = daysLate * FINE_PER_DAY;
                                pengembalian.setDenda(fine);
                        } else {
                                pengembalian.setTerlambat("Tidak terlambat");
                                pengembalian.setDenda(0.0);
                        }
                } else {
                        // Handle cases where peminjaman or its return date is not found
                        pengembalian.setTerlambat(
                                        "Data peminjaman tidak ditemukan atau tanggal pengembalian belum ditentukan");
                        pengembalian.setDenda(0.0);
                }

                return PengembalianRepository.save(pengembalian);
        }

    public List<ResponseTemplate> getPengembalianWithPeminjamanById(Long id){
        List<ResponseTemplate> responseList = new ArrayList<>();
        Pengembalian pengembalian = getPengembalianById(id);
        

        if (pengembalian == null) {
            return responseList; // Return empty if pengembalian not found
        }

        ServiceInstance peminjamanServiceInstance = discoveryClient.getInstances("peminjaman-service").get(0);
        Peminjaman peminjaman = restTemplate.getForObject(peminjamanServiceInstance.getUri() + "/api/peminjaman/"
                + pengembalian.getPeminjamanId(), Peminjaman.class);
                
        ServiceInstance bukuServiceInstance = discoveryClient.getInstances("buku-service").get(0);
        Buku buku = restTemplate.getForObject(bukuServiceInstance.getUri() + "/api/buku/"
                + pengembalian.getBukuId(), Buku.class);
                
        ServiceInstance anggotaServiceInstance = discoveryClient.getInstances("anggota-service").get(0);
        Anggota anggota = restTemplate.getForObject(anggotaServiceInstance.getUri() + "/api/anggota/"
                + pengembalian.getAnggotaId(), Anggota.class);

        ResponseTemplate vo = new ResponseTemplate();
        vo.setPengembalian(pengembalian);
        vo.setPeminjaman(peminjaman);
        vo.setBuku(buku);
        vo.setAnggota(anggota);
        responseList.add(vo);
        return responseList;
    }

        public void deletePengembalian(Long id) {
                PengembalianRepository.deleteById(id);
        }
}