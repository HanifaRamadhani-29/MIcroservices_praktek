package com.hanifa.peminjaman_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hanifa.peminjaman_service.model.Peminjaman;
import com.hanifa.peminjaman_service.repository.PeminjamanRepository;
import com.hanifa.peminjaman_service.vo.Anggota;
import com.hanifa.peminjaman_service.vo.Buku;
import com.hanifa.peminjaman_service.vo.Pengembalian;
import com.hanifa.peminjaman_service.vo.ResponseTemplate;

@Service
public class PeminjamanService {
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Peminjaman createPeminjaman(Peminjaman peminjaman) {
        return peminjamanRepository.save(peminjaman);
    }

    public List<Peminjaman> getAllPeminjamans() {
        return peminjamanRepository.findAll();
    }

    public Peminjaman getPeminjamanById(Long id) {
        return peminjamanRepository.findById(id).orElse(null);
    }

    public ResponseTemplate getPeminjamanWithDetailsById(Long id) {
        Peminjaman peminjaman = peminjamanRepository.findById(id).orElse(null);
        if (peminjaman == null) {
            return null;
        }

        // --- Anggota ---
        List<ServiceInstance> anggotaInstances = discoveryClient.getInstances("anggota-service");
        if (anggotaInstances.isEmpty()) {
            throw new IllegalStateException("Service ANGGOTA tidak tersedia");
        }
        Anggota anggota = restTemplate.getForObject(
                anggotaInstances.get(0).getUri() + "/api/anggota/" + peminjaman.getAnggotaId(),
                Anggota.class);

        // --- Buku ---
        List<ServiceInstance> bukuInstances = discoveryClient.getInstances("buku-service");
        if (bukuInstances.isEmpty()) {
            throw new IllegalStateException("Service BUKU tidak tersedia");
        }
        Buku buku = restTemplate.getForObject(
                bukuInstances.get(0).getUri() + "/api/buku/" + peminjaman.getBukuId(),
                Buku.class);

        // --- Pengembalian ---
        Pengembalian pengembalian = null;
        if (peminjaman.getPengembalianId() != null) {
            List<ServiceInstance> pengembalianInstances = discoveryClient.getInstances("pengembalian-service");
            if (pengembalianInstances.isEmpty()) {
                throw new IllegalStateException("Service PENGEMBALIAN tidak tersedia");
            }
            pengembalian = restTemplate.getForObject(
                    pengembalianInstances.get(0).getUri() + "/api/pengembalian/" + peminjaman.getPengembalianId(),
                    Pengembalian.class);
        }

        ResponseTemplate response = new ResponseTemplate();
        response.setPeminjaman(peminjaman);
        response.setAnggota(anggota);
        response.setBuku(buku);
        response.setPengembalian(pengembalian);

        return response;
    }

    public void deletePeminjaman(Long id) {
        peminjamanRepository.deleteById(id);
    }

    public Pengembalian createPengembalian(Pengembalian pengembalian) {
        List<ServiceInstance> pengembalianInstances = discoveryClient.getInstances("pengembalian_service");
        if (pengembalianInstances.isEmpty()) {
            throw new IllegalStateException("Service PENGEMBALIAN tidak tersedia");
        }
        return restTemplate.postForObject(
                pengembalianInstances.get(0).getUri() + "/api/pengembalian",
                pengembalian, Pengembalian.class);
    }

    public Pengembalian getPengembalianById(Long id) {
        List<ServiceInstance> pengembalianInstances = discoveryClient.getInstances("pengembalian_service");
        if (pengembalianInstances.isEmpty()) {
            throw new IllegalStateException("Service PENGEMBALIAN tidak tersedia");
        }
        return restTemplate.getForObject(
                pengembalianInstances.get(0).getUri() + "/api/pengembalian/" + id,
                Pengembalian.class);
    }
}
