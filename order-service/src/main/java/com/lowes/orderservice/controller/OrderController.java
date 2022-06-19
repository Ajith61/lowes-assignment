package com.lowes.orderservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lowes.orderservice.client.InventoryClient;
import com.lowes.orderservice.model.OrderDao;
import com.lowes.orderservice.model.OrderDto;
import com.lowes.orderservice.repository.OrderRepository;
import com.lowes.orderservice.service.OrderService;
import com.lowes.orderservice.util.model.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.stream.function.StreamBridge;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1.0.0/order")
@CrossOrigin(origins = "*")
public class OrderController {

	@Autowired
	private OrderService orderService;
	private Logger logger = LoggerFactory.getLogger(OrderController.class);


	@PostMapping("/create")
	public ResponseModel<OrderDto> createOrder(@Valid @RequestBody OrderDto orderRequestDto) {
		ResponseModel<OrderDto> orderDto = orderService.createOrder(orderRequestDto);
		logger.info(orderDto.toString());
		return orderDto;
	}
}
