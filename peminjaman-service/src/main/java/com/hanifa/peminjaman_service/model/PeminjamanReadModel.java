package com.hanifa.peminjaman_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "peminjaman") // Specify MongoDB collection name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PeminjamanReadModel {
    @Id // Mongo DB uses String for Id
    private String id; // Use String for MongoDB _id
    private Long peminjamanIdSql; // To link back to SQL ID if needed
    private Long anggotaId;
    private String anggotaNama;
    private String anggotaEmail; // Mungkin tidak perlu di read model jika hanya untuk notifikasi
    private Long bukuId;
    private String bukuJudul;
    private LocalDate tanggalPeminjaman;
    private LocalDate tanggalPengembalian;
}