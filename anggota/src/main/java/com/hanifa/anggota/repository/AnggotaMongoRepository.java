package com.hanifa.anggota.repository;
import com.hanifa.anggota.cqrs.query.model.AnggotaDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnggotaMongoRepository extends MongoRepository<AnggotaDocument, String> { }