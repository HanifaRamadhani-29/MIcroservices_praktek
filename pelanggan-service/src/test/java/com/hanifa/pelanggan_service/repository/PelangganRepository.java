package com.hanifa.pelanggan_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.stereotype.Repository;

import com.hanifa.pelanggan_service.model.Pelanggan;

public interface PelangganRepository extends JpaRepository<Pelanggan,Long>{

}
