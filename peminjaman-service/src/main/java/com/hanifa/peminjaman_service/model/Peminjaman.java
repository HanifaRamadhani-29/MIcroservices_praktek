package com.hanifa.peminjaman_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Table(name = "peminjamans")
@Entity
public class Peminjaman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long anggotaId;
    private long bukuId;
    private String tanggal_pinjam;
    private String tanggal_kembali;
    private Long pengembalianId;
    
}