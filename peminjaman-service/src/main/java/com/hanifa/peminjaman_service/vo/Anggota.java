package com.hanifa.peminjaman_service.vo;

public class Anggota {
    private Long id;
    private String nim;
    private String nama;
    private String alamat;
    private String jenis_kelamin;

    

    public Anggota(Long id, String nim, String nama, String alamat, String jenis_kelamin) {
        this.id = id;
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
        this.jenis_kelamin = jenis_kelamin;
    }



    public void setId(Long id) {
        this.id = id;
    }



    public void setNim(String nim) {
        this.nim = nim;
    }



    public void setNama(String nama) {
        this.nama = nama;
    }



    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setJenisKelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    public Long getId() {
        return id;
    }

    public String getNim() {
        return nim;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }
    public String getJenisKelamin() {
        return jenis_kelamin;
    }

}
