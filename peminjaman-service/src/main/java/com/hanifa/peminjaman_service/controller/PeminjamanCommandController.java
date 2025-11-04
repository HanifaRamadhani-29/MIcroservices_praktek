package com.hanifa.peminjaman_service.controller;

import com.hanifa.peminjaman_service.model.Peminjaman;
import com.hanifa.peminjaman_service.service.PeminjamanCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/peminjaman/command")

public class PeminjamanCommandController {

    @Autowired
    private PeminjamanCommandService peminjamanCommandService; // Renamed

    @PostMapping("/")
    public ResponseEntity<Peminjaman> createPeminjaman(@RequestBody Peminjaman peminjaman){
        Peminjaman createdPeminjaman = peminjamanCommandService.createPeminjaman(peminjaman);
        return new ResponseEntity<>(createdPeminjaman, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeminjaman(@PathVariable Long id){
        peminjamanCommandService.deletePeminjaman(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
