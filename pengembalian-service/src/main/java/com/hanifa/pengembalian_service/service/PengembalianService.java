package com.hanifa.pengembalian_service.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public List<Pengembalian> getAllPengembalians(){
    return PengembalianRepository.findAll();
    }

    public Pengembalian getPengembalianById(Long id) {
    return PengembalianRepository.findById(id).orElse(null);
    }

    public Pengembalian createPengembalian(Pengembalian pengembalian){
    return PengembalianRepository.save(pengembalian);
    }

    public List<ResponseTemplate> getPengembalianWithPeminjamanById(Long id){
        List<ResponseTemplate> responseList = new ArrayList<>();
        Pengembalian pengembalian = getPengembalianById(id);
        ServiceInstance serviceInstance = discoveryClient.getInstances("peminjaman-service").get(0);
        Peminjaman peminjaman = restTemplate.getForObject(serviceInstance.getUri() + "/api/peminjaman/"
                + pengembalian.getPeminjamanId(), Peminjaman.class);
                serviceInstance = discoveryClient.getInstances("buku-service").get(0);
        Buku buku = restTemplate.getForObject(serviceInstance.getUri() + "/api/buku/"
                + pengembalian.getBukuId(), Buku.class);
                serviceInstance = discoveryClient.getInstances("anggota-service").get(0);
        Anggota anggota = restTemplate.getForObject(serviceInstance.getUri() + "/api/anggota/"
                + pengembalian.getAnggotaId(), Anggota.class);
        ResponseTemplate vo = new ResponseTemplate();
        vo.setPengembalian(pengembalian);
        vo.setPeminjaman(peminjaman);
        vo.setBuku(buku);
        vo.setAnggota(anggota);
        responseList.add(vo);
        return responseList;
    }

    public void deletePengembalian (Long id){
    PengembalianRepository.deleteById(id);
    }
}