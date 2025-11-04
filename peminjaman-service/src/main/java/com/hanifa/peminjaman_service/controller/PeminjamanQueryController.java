package com.hanifa.peminjaman_service.controller;


import com.hanifa.peminjaman_service.model.PeminjamanReadModel;
import com.hanifa.peminjaman_service.service.PeminjamanQueryService;
import com.hanifa.peminjaman_service.vo.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/peminjaman/query")

public class PeminjamanQueryController {

    private final PeminjamanQueryService peminjamanQueryService;

    /**
     * Menggunakan constructor injection adalah praktik terbaik.
     * Ini memastikan bahwa service yang dibutuhkan selalu ada saat controller dibuat.
     *
     * @param peminjamanQueryService Service yang akan di-inject oleh Spring.
     */
    @Autowired
    public PeminjamanQueryController(PeminjamanQueryService peminjamanQueryService) {
        this.peminjamanQueryService = peminjamanQueryService;
    }

    /**
     * Mengambil semua data peminjaman yang ada di read model (MongoDB).
     * URL: GET /api/peminjaman/query
     * @return Daftar semua PeminjamanReadModel.
     */
    @GetMapping
    public ResponseEntity<List<PeminjamanReadModel>> getAllPeminjamans() {
        List<PeminjamanReadModel> peminjamans = peminjamanQueryService.getAllPeminjamans();
        return new ResponseEntity<>(peminjamans, HttpStatus.OK);
    }

    /**
     * Mengambil satu data peminjaman berdasarkan ID unik dari MongoDB (yang berupa String).
     * URL: GET /api/peminjaman/query/{id}
     * @param id ID unik dari dokumen di MongoDB.
     * @return PeminjamanReadModel jika ditemukan, atau 404 Not Found jika tidak.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PeminjamanReadModel> getPeminjamanById(@PathVariable String id) {
        PeminjamanReadModel peminjaman = peminjamanQueryService.getPeminjamanById(id);
        if (peminjaman != null) {
            return new ResponseEntity<>(peminjaman, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Mengambil satu data peminjaman berdasarkan ID asli dari database SQL (yang berupa Long).
     * Ini berguna untuk mencari data di read model menggunakan ID dari command model.
     * URL: GET /api/peminjaman/query/sqlId/{sqlId}
     * @param sqlId ID asli dari tabel peminjaman di database SQL.
     * @return PeminjamanReadModel jika ditemukan, atau 404 Not Found jika tidak.
     */
    @GetMapping("/sqlId/{sqlId}")
    public ResponseEntity<PeminjamanReadModel> getPeminjamanBySqlId(@PathVariable Long sqlId) {
        PeminjamanReadModel peminjaman = peminjamanQueryService.getPeminjamanBySqlId(sqlId);
        if (peminjaman != null) {
            return new ResponseEntity<>(peminjaman, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Mengambil detail lengkap dari satu peminjaman (termasuk data Anggota dan Buku).
     * URL: GET /api/peminjaman/query/with-details/{sqlId}
     * @param sqlId ID asli dari database SQL.
     * @return ResponseTemplate yang berisi gabungan data, atau 404 Not Found.
     */
    @GetMapping("/with-details/{sqlId}")
    public ResponseEntity<List<ResponseTemplate>> getPeminjamanWithDetails(@PathVariable Long sqlId) {
        List<ResponseTemplate> response = peminjamanQueryService.getPeminjamanWithBookAndAnggotaDetails(sqlId);
        if (response != null && !response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Mengambil detail lengkap dari SEMUA peminjaman.
     * URL: GET /api/peminjaman/query/all-with-details
     * @return Daftar ResponseTemplate yang berisi gabungan data.
     */
    @GetMapping("/all-with-details")
    public ResponseEntity<List<ResponseTemplate>> getAllPeminjamanWithDetails() {
        List<ResponseTemplate> response = peminjamanQueryService.getAllPeminjamanWithDetails();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
