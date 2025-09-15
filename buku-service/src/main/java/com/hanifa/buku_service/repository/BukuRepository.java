package com.hanifa.buku_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hanifa.buku_service.model.Buku;

@Repository
public interface BukuRepository extends JpaRepository<Buku, Long> {

}
