package com.hanifa.peminjaman_service.service;

import com.hanifa.peminjaman_service.model.PeminjamanReadModel;
import com.hanifa.peminjaman_service.repository.PeminjamanReadModelRepository;
import com.hanifa.peminjaman_service.vo.Anggota;
import com.hanifa.peminjaman_service.vo.Buku;
import com.hanifa.peminjaman_service.vo.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PeminjamanQueryService { // NEW
    @Autowired
    private PeminjamanReadModelRepository peminjamanReadModelRepository; // NEW

    @Autowired
    private RestTemplate restTemplate; // Masih dibutuhkan untuk mengambil detail Anggota/Buku dari VO (untuk ResponseTemplate)

    @Autowired
    private DiscoveryClient discoveryClient; // Masih dibutuhkan

    public List<PeminjamanReadModel> getAllPeminjamans(){
        return peminjamanReadModelRepository.findAll();
    }

    public PeminjamanReadModel getPeminjamanById(String id) { // ID di MongoDB adalah String
        return peminjamanReadModelRepository.findById(id).orElse(null);
    }

    // NEW: Get by SQL ID
    public PeminjamanReadModel getPeminjamanBySqlId(Long sqlId) {
        return peminjamanReadModelRepository.findByPeminjamanIdSql(sqlId).orElse(null);
    }

    // Metode ini mengembalikan ResponseTemplate yang menggabungkan PeminjamanReadModel dengan Buku/Anggota VO
    // Ini mungkin tidak sepenuhnya CQRS untuk Read Model, tapi cocok jika Anda masih ingin menggabungkan data
    // Namun, idealnya, Read Model sudah berisi semua data yang dibutuhkan agar tidak perlu call service lain.
    // Jika Anda ingin read model yang benar-benar denormalisasi, PeminjamanReadModel harus memiliki semua field Buku dan Anggota.
    public List<ResponseTemplate> getPeminjamanWithBookAndAnggotaDetails(Long peminjamanIdSql){
        List<ResponseTemplate> responseList = new ArrayList<>();
        PeminjamanReadModel readModel = getPeminjamanBySqlId(peminjamanIdSql);
        
        if (readModel == null) {
            return null; // Or throw an exception
        }

        // Jika PeminjamanReadModel sudah memiliki Anggota dan Buku detail, tidak perlu panggil service lain lagi.
        // Tapi jika belum, kita panggil seperti biasa.
        // Untuk contoh ini, saya masih memanggil service lain karena PeminjamanReadModel saat ini hanya punya 'anggotaNama' dan 'bukuJudul'
        // dan VO Anggota/Buku mungkin punya lebih banyak detail yang Anda inginkan.

        Anggota anggota = null;
        if (readModel.getAnggotaId() != null) {
            try {
                 ServiceInstance anggotaServiceInstance = discoveryClient.getInstances("anggota-service").get(0);
                 anggota = restTemplate.getForObject(anggotaServiceInstance.getUri() + "/api/anggota/"
                         + readModel.getAnggotaId(), Anggota.class);
            } catch (Exception e) {
                System.err.println("Error fetching Anggota details: " + e.getMessage());
                // Handle case where Anggota service is down or Anggota not found
            }
        }

        Buku buku = null;
        if (readModel.getBukuId() != null) {
            try {
                ServiceInstance bukuServiceInstance = discoveryClient.getInstances("buku-service").get(0);
                buku = restTemplate.getForObject(bukuServiceInstance.getUri() + "/api/buku/"
                        + readModel.getBukuId(), Buku.class);
            } catch (Exception e) {
                System.err.println("Error fetching Buku details: " + e.getMessage());
                // Handle case where Buku service is down or Buku not found
            }
        }
        
        // Buat objek Peminjaman sementara dari read model untuk ResponseTemplate
        // Atau, jika ResponseTemplate bisa langsung menerima PeminjamanReadModel, itu lebih baik.
        // Untuk saat ini, kita akan buat objek Peminjaman "dummy" dari PeminjamanReadModel
        // agar sesuai dengan ResponseTemplate yang ada.
        com.hanifa.peminjaman_service.model.Peminjaman dummyPeminjaman = new com.hanifa.peminjaman_service.model.Peminjaman();
        dummyPeminjaman.setId(readModel.getPeminjamanIdSql());
        dummyPeminjaman.setAnggotaId(readModel.getAnggotaId());
        dummyPeminjaman.setBukuId(readModel.getBukuId());
        dummyPeminjaman.setTanggalPeminjaman(readModel.getTanggalPeminjaman());
        dummyPeminjaman.setTanggalPengembalian(readModel.getTanggalPengembalian());


        ResponseTemplate vo = new ResponseTemplate();
        vo.setPeminjaman(dummyPeminjaman); // Menggunakan dummyPeminjaman
        vo.setBuku(buku);
        vo.setAnggota(anggota);
        responseList.add(vo);
        return responseList;
    }

    // NEW: Metode untuk mendapatkan semua peminjaman dengan detail Buku/Anggota
    public List<ResponseTemplate> getAllPeminjamanWithDetails() {
        return peminjamanReadModelRepository.findAll().stream()
                .map(readModel -> {
                    Anggota anggota = null;
                    if (readModel.getAnggotaId() != null) {
                        try {
                             ServiceInstance anggotaServiceInstance = discoveryClient.getInstances("anggota-service").get(0);
                             anggota = restTemplate.getForObject(anggotaServiceInstance.getUri() + "/api/anggota/"
                                     + readModel.getAnggotaId(), Anggota.class);
                        } catch (Exception e) {
                            System.err.println("Error fetching Anggota details for ID " + readModel.getAnggotaId() + ": " + e.getMessage());
                        }
                    }

                    Buku buku = null;
                    if (readModel.getBukuId() != null) {
                        try {
                            ServiceInstance bukuServiceInstance = discoveryClient.getInstances("buku-service").get(0);
                            buku = restTemplate.getForObject(bukuServiceInstance.getUri() + "/api/buku/"
                                    + readModel.getBukuId(), Buku.class);
                        } catch (Exception e) {
                            System.err.println("Error fetching Buku details for ID " + readModel.getBukuId() + ": " + e.getMessage());
                        }
                    }

                    com.hanifa.peminjaman_service.model.Peminjaman dummyPeminjaman = new com.hanifa.peminjaman_service.model.Peminjaman();
                    dummyPeminjaman.setId(readModel.getPeminjamanIdSql());
                    dummyPeminjaman.setAnggotaId(readModel.getAnggotaId());
                    dummyPeminjaman.setBukuId(readModel.getBukuId());
                    dummyPeminjaman.setTanggalPeminjaman(readModel.getTanggalPeminjaman());
                    dummyPeminjaman.setTanggalPengembalian(readModel.getTanggalPengembalian());

                    ResponseTemplate vo = new ResponseTemplate();
                    vo.setPeminjaman(dummyPeminjaman);
                    vo.setBuku(buku);
                    vo.setAnggota(anggota);
                return vo;
                })
                .collect(Collectors.toList());
    }
}