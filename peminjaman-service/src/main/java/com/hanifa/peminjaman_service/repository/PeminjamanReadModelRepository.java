package com.hanifa.peminjaman_service.repository;

import com.hanifa.peminjaman_service.model.PeminjamanReadModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeminjamanReadModelRepository extends MongoRepository<PeminjamanReadModel, String> {
    Optional<PeminjamanReadModel> findByPeminjamanIdSql(Long peminjamanIdSql);
    List<PeminjamanReadModel> findByAnggotaId(Long anggotaId);
    List<PeminjamanReadModel> findByBukuId(Long bukuId);
}
