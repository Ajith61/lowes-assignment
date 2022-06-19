package com.lowes.inventoryservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lowes.inventoryservice.model.dao.InventoryDao;


public interface InventoryRepository extends MongoRepository<InventoryDao, String> {
	
	Optional<InventoryDao> findByProductId(String productId);
}

