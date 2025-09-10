package com.hanifa.order_service.vo;

public class Produk {
    private Long id;
    private String nama;
    private String satuan;
    private double harga;

    public Produk() {
    }

    public Produk(Long id, String nama, String satuan, double harga) {
        this.id = id;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
    }

    public Long getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public double getHarga() {
        return harga;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

}
