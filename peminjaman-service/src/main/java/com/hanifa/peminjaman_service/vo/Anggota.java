package com.hanifa.peminjaman_service.vo;

public class Anggota {
    private Long id;
    private String nim;
    private String nama;
    private String alamat;
    private String jenis_kelamin;
    private String email;   // ✅ sudah ada

    // Constructor
    public Anggota(Long id, String nim, String nama, String alamat, String jenis_kelamin, String email) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
        this.email = email;
    }

    public Anggota() {
        // default constructor (dibutuhkan RestTemplate untuk mapping JSON)
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNim() {
        return nim;
    }
    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenisKelamin() {
        return jenis_kelamin;
    }
    public void setJenisKelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getEmail() {   // ✅ ini yang bikin getEmail() tidak error lagi
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Anggota{" +
                "id=" + id +
                ", nim='" + nim + '\'' +
                ", nama='" + nama + '\'' +
                ", alamat='" + alamat + '\'' +
                ", jenis_kelamin='" + jenis_kelamin + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
