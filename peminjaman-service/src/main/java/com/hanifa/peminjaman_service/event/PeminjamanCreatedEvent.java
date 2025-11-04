package com.hanifa.peminjaman_service.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeminjamanCreatedEvent {
    private Long id;
    private Long anggotaId;
    private Long bukuId;
    private LocalDate tanggalPeminjaman;
    private LocalDate tanggalPengembalian;
    private String anggotaNama; // Tambahan untuk read model
    private String anggotaEmail; // Tambahan untuk read model
    private String bukuJudul;    // Tambahan untuk read model
    // Anda bisa menambahkan detail lain yang relevan untuk read model
}