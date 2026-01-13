package com.hanifa.anggota.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanifa.anggota.cqrs.command.model.Anggota;
import com.hanifa.anggota.repository.AnggotaJpaRepository;

@Service
public class AnggotaService {
    @Autowired
    private AnggotaJpaRepository anggotaRepository;

    public List<Anggota> getAllAnggota(){
        return anggotaRepository.findAll();
    }
    public Anggota getAnggotaById(Long id){
        return anggotaRepository.findById(id).orElse(null);
    }
    public Anggota createAnggota(Anggota anggota){
        return anggotaRepository.save(anggota);
    }
    public void deleteAnggota (Long id){
        anggotaRepository.deleteById(id);
    }
}

