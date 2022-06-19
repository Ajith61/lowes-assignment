package com.lowes.notificationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lowes.notificationservice.model.OrderDao;


public interface OrderRepository extends MongoRepository<OrderDao, String> {
}
