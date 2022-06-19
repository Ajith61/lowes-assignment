package com.lowes.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lowes.productservice.model.dao.ProductDao;

public interface ProductRepository extends MongoRepository<ProductDao, String> {
	
    //@Query("{productName:'?0'}")
    ProductDao findProductByProductName(String productName);
    
    ProductDao findProductByProductId(String productId);
}
