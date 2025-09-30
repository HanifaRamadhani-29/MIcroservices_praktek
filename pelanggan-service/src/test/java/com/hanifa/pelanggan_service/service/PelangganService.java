package com.hanifa.pelanggan_service.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hanifa.pelanggan_service.model.Pelanggan;
import com.hanifa.pelanggan_service.repository.PelangganRepository;

@Service
public class PelangganService {
    @Autowired
    private PelangganRepository PelangganRepository;

    public List<Pelanggan> getAllPelanggans(){
    return PelangganRepository.findAll();
    }

    public Pelanggan getPelangganById(Long id) {
    return PelangganRepository.findById(id).orElse(null);
    }

    public Pelanggan createPelanggan(Pelanggan pelanggan){
    return PelangganRepository.save(pelanggan);
    }

    public void deletePelanggan (Long id){
    PelangganRepository.deleteById(id);
    }
}