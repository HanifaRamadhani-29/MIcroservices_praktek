package com.hanifa.order_service.vo;

public class Pelanggan {
    private Long id;
    private String kode;
    private String nama;
    private String alamat;

    public Pelanggan() {
    }

    public Pelanggan(Long id, String kode, String nama, String alamat) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
    }

    public Long getId() {
        return id;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    
}
