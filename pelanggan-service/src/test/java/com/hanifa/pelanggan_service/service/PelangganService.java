package com.hanifa.pelanggan_service.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hanifa.pelanggan_service.model.Pelanggan;
import com.hanifa.pelanggan_service.repository.PelangganRepository;

@Service
public class PelangganService {
    @Autowired
    private PelangganRepository pelangganRepository;

    public List<Pelanggan> getAllPelanggans(){
    return pelangganRepository.findAll();
    }

    public Pelanggan getPelangganById(Long id) {
    return pelangganRepository.findById(id).orElse(null);
    }

    public Pelanggan createPelanggan(Pelanggan pelanggan){
    return pelangganRepository.save(pelanggan);
    }

    public void deletePelanggan (Long id){
    pelangganRepository.deleteById(id);
}
}