package com.rafi.order_service.vo;

public class Customer {
    private Long id;
    private String kode;
    private String nama;
    private String alamat;

    public Customer() {}

    public Customer(Long id, String kode, String nama, String alamat) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.alamat = alamat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
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

    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
