package com.hanifa.anggota_service.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hanifa.anggota_service.model.Anggota;
import com.hanifa.anggota_service.repository.AnggotaRepository;

@Service
public class AnggotaService {
    @Autowired
    private AnggotaRepository AnggotaRepository;

    public List<Anggota> getAllAnggotas(){
    return AnggotaRepository.findAll();
    }

    public Anggota getAnggotaById(Long id) {
    return AnggotaRepository.findById(id).orElse(null);
    }

    public Anggota createAnggota(Anggota anggota){
    return AnggotaRepository.save(anggota);
    }

    public void deleteAnggota (Long id){
    AnggotaRepository.deleteById(id);
    }
}