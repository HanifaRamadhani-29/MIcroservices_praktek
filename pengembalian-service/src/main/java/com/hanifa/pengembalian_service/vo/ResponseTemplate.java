package com.hanifa.pengembalian_service.vo;
import com.hanifa.pengembalian_service.model.Pengembalian;

public class ResponseTemplate {
    Pengembalian pengembalian;
    Buku buku;
    Anggota anggota;
    Peminjaman peminjaman;

    public ResponseTemplate(){

    }


    public ResponseTemplate(Pengembalian pengembalian, Buku buku, Anggota anggota, Peminjaman peminjaman) {
        this.pengembalian = pengembalian;
        this.buku = buku;
        this.anggota = anggota;
        this.peminjaman = peminjaman;
    }

    public Pengembalian getPengembalian() {
        return pengembalian;
    }


    public void setPengembalian(Pengembalian pengembalian) {
        this.pengembalian = pengembalian;
    }


    public Buku getBuku() {
        return buku;
    }


    public void setBuku(Buku buku) {
        this.buku = buku;
    }


    public Anggota getAnggota() {
        return anggota;
    }


    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }


    public Peminjaman getPeminjaman() {
        return peminjaman;
    }


    public void setPeminjaman(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
    }


    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
    
}