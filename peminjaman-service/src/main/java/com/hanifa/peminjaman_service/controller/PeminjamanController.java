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

    // Endpoint ini akan mengembalikan daftar peminjaman dasar tanpa detail
    @GetMapping
    public List<Peminjaman> getAllPeminjamans() {
        return peminjamanService.getAllPeminjamans();
    }

    // Endpoint ini akan mengembalikan detail lengkap peminjaman
    // URL: http://127.0.0.1:8083/api/peminjaman/1 (jika id=1)
    @GetMapping("/{id}")
    public ResponseEntity<ResponseTemplate> getPeminjamanWithDetailsById(@PathVariable Long id) {
        ResponseTemplate response = peminjamanService.getPeminjamanWithDetailsById(id);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public Peminjaman createPeminjaman(@RequestBody Peminjaman peminjaman) {
        return peminjamanService.createPeminjaman(peminjaman);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeminjaman(@PathVariable Long id) {
        peminjamanService.deletePeminjaman(id);
        return ResponseEntity.ok().build();
    }
}