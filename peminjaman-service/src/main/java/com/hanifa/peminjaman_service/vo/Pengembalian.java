package com.hanifa.peminjaman_service.vo;

public class Pengembalian {
    private Long id;
    private String tanggal_dikembalikan;
    private String terlambat;
    private double denda;

    public Pengembalian(double denda, Long id, String tanggal_dikembalikan, String terlambat) {
        this.denda = denda;
        this.id = id;
        this.tanggal_dikembalikan = tanggal_dikembalikan;
        this.terlambat = terlambat;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setTanggalKembali(String tanggal_dikembalikan) {
        this.tanggal_dikembalikan = tanggal_dikembalikan;
    }

    public void setTerlambat(String terlambat) {
        this.terlambat = terlambat;
    }

    public void setDenda(double denda) {
        this.denda = denda;
    }

    public Long getId() {
        return id;
    }

    public String getTanggalKembali() {
        return tanggal_dikembalikan;
    }

    public String getTerlambat() {
        return terlambat;
    }

    public double getDenda() {
        return denda;
    }
}
