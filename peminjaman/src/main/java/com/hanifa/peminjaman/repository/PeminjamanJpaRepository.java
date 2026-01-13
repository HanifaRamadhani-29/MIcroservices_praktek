package com.hanifa.peminjaman.repository;
import com.hanifa.peminjaman.cqrs.command.model.Peminjaman;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PeminjamanJpaRepository extends JpaRepository<Peminjaman, Long> {}