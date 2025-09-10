package com.rafi.order_service.vo;

public class Product {
    private Long id;
    private String nama;
    private String satuan;
    private double harga;

    public Product() {}

    public Product(Long id, String nama, String satuan, double harga) {
        this.id = id;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }


    
}
