package com.hanifa.peminjaman.repository;
import com.hanifa.peminjaman.cqrs.query.model.PeminjamanDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface PeminjamanMongoRepository extends MongoRepository<PeminjamanDocument, String> {}