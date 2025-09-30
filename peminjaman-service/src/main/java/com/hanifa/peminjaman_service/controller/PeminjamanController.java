package com.hanifa.peminjaman_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanifa.peminjaman_service.model.Peminjaman;
import com.hanifa.peminjaman_service.service.PeminjamanService;
import com.hanifa.peminjaman_service.vo.ResponseTemplate;

@RestController
@RequestMapping("/api/peminjaman")
public class PeminjamanController {
    @Autowired
    private PeminjamanService peminjamanService;

    @GetMapping
    public List<Peminjaman> getAllPeminjamans() {
        return peminjamanService.getAllPeminjamans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peminjaman> getPeminjamanById(@PathVariable Long id) {
        Peminjaman peminjaman = peminjamanService.getPeminjamanById(id);
        return peminjaman != null ? ResponseEntity.ok(peminjaman) : ResponseEntity.notFound().build();
    }

    @PostMapping // Modified to accept Peminjaman object directly
    public Peminjaman createPeminjaman(@RequestBody Peminjaman peminjaman) {
        return peminjamanService.createPeminjaman(peminjaman);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePeminjaman(@PathVariable Long id) {
        peminjamanService.deletePeminjaman(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buku/{id}")
    public ResponseEntity<List<ResponseTemplate>> getPeminjamanWithBookById(@PathVariable Long id) {
        List<ResponseTemplate> responseTemplate = peminjamanService.getPeminjamanWithBookById(id);
        return responseTemplate != null ? ResponseEntity.ok(responseTemplate): ResponseEntity.notFound().build();
    }
}