package com.hanifa.buku.repository;
import com.hanifa.buku.cqrs.query.model.BukuDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BukuMongoRepository extends MongoRepository<BukuDocument, String> { }