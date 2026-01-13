package com.hanifa.anggota.repository;
import com.hanifa.anggota.cqrs.command.model.Anggota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnggotaJpaRepository extends JpaRepository<Anggota, Long> { }