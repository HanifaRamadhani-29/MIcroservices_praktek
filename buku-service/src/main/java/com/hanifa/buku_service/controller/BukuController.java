package com.hanifa.buku_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hanifa.buku_service.model.Buku;
import com.hanifa.buku_service.service.BukuService;

@RestController
@RequestMapping("/api/buku")
public class BukuController {

    @Autowired
    private BukuService bukuService;

    // GET /api/buku
    @GetMapping
    public ResponseEntity<List<Buku>> getAllBuku() {
        List<Buku> bukus = bukuService.getAllBukus();
        return ResponseEntity.ok(bukus);
    }

    // GET /api/buku/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Buku> getBukuById(@PathVariable Long id) {
        Buku buku = bukuService.getBukuById(id);

        if (buku == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(buku);
    }

    // POST /api/buku
    @PostMapping
    public ResponseEntity<Buku> createBuku(@RequestBody Buku buku) {
        Buku savedBuku = bukuService.createBuku(buku);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBuku);
    }

    // DELETE /api/buku/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuku(@PathVariable Long id) {
        Buku buku = bukuService.getBukuById(id);

        if (buku == null) {
            return ResponseEntity.notFound().build();
        }

        bukuService.deleteBuku(id);
        return ResponseEntity.noContent().build();
    }
}
