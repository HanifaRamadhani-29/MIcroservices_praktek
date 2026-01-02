package com.hanifa.anggota_service.controller; // Sesuaikan dengan package Hanifa

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "<h1>Service Anggota Pustaka</h1><p>Status: Berhasil dijalankan otomatis via Jenkins CI/CD!</p>";
    }
}