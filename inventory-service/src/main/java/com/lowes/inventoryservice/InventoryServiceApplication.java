package com.lowes.inventoryservice;

import java.util.function.Consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lowes.inventoryservice.service.InventoryService;


@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
    public Consumer<Message<String>> notificationEventSupplier() {
        return message -> {
			try {
				//process message (after adding product,product-service send data to this stream to add a data in a inventory table)
				new InventoryService().saveProductInventory(message.getPayload());
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		};
    }
	

}
