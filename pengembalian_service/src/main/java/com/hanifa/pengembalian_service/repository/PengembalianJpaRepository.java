package com.hanifa.pengembalian_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hanifa.pengembalian_service.cqrs.command.model.Pengembalian;

public interface PengembalianJpaRepository extends JpaRepository<Pengembalian, Long> {
}
