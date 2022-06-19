package com.lowes.orderservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lowes.orderservice.model.OrderDao;


public interface OrderRepository extends MongoRepository<OrderDao, String> {
}

