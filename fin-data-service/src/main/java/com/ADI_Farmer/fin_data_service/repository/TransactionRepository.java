package com.ADI_Farmer.fin_data_service.repository;

import com.ADI_Farmer.fin_data_service.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByStatus(String status);
    List<Transaction> findByType(String type);
}