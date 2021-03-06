package com.lowes.notificationservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lowes.notificationservice.model.OrderDao;
import com.lowes.notificationservice.repository.OrderRepository;

@Service
public class NotificationService {
	
	@Autowired
	private  OrderRepository orderRepository;
	 

	public void processOrderStatus(String orderId) {
        if(orderId!=null) {
        	Optional<OrderDao> orderDao = orderRepository.findById(orderId);
        	if (orderDao.isPresent()) {
        		orderDao.get().setOrderStatus("PROCESSED");
        		orderRepository.save(orderDao.get());
        	}        	
        }
	}
	
	

}
