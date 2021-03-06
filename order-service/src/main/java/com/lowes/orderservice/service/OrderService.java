package com.lowes.orderservice.service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.lowes.orderservice.client.InventoryClient;
import com.lowes.orderservice.model.OrderDao;
import com.lowes.orderservice.model.OrderDto;
import com.lowes.orderservice.repository.OrderRepository;
import com.lowes.orderservice.util.constants.ApplicationStatus;
import com.lowes.orderservice.util.model.ResponseModel;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private InventoryClient inventoryClient;
	@Autowired
	private Resilience4JCircuitBreakerFactory circuitBreakerFactory;
	@Autowired
	private StreamBridge streamBridge;

	public ResponseModel<OrderDto> createOrder(@Valid OrderDto orderDto) {
		try {
			// circuit breaker initialization
			Resilience4JCircuitBreaker circuitBreaker = circuitBreakerFactory.create("inventory");
			// check stock
			java.util.function.Supplier<Boolean> booleanSupplier = () -> inventoryClient
					.checkStock(orderDto.getProductId());
			// run feign client with circuit breaker if it failes then return
			// handleErrorCase()
			boolean productsInStock = circuitBreaker.run(booleanSupplier, throwable -> handleErrorCase());
			// if stock exists
			if (productsInStock) {
				OrderDao orderDao = new OrderDao();
				BeanUtils.copyProperties(orderDto, orderDao);
				if (orderDao != null) {
					orderDao.setOrderStatus(ApplicationStatus.PLACED.getStatusMessage());
					OrderDao resultOrderDao = orderRepository.save(orderDao);
					// send message to notification-service
					streamBridge.send("notificationEventSupplier-out-0",
							MessageBuilder.withPayload(resultOrderDao.getId()).build());

				}
				return new ResponseModel<OrderDto>(ApplicationStatus.CREATED.getStatusCode(),
						ApplicationStatus.CREATED.getStatusMessage(), null);
			} else {
				return new ResponseModel<OrderDto>(ApplicationStatus.NO_STOCK.getStatusCode(),
						ApplicationStatus.NO_STOCK.getStatusMessage(), null);
			}
		} catch (Exception ex) {
			return new ResponseModel<OrderDto>(ApplicationStatus.ERROR.getStatusCode(), ex.getLocalizedMessage(), null);
		}
	}

	private Boolean handleErrorCase() {
		return false;
	}

}
