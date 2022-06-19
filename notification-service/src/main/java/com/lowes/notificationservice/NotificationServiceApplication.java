package com.lowes.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import com.lowes.notificationservice.service.NotificationService;

import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@EnableEurekaClient
public class NotificationServiceApplication {
	
	@Autowired
	NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	@Bean
    public Consumer<Message<String>> notificationEventSupplier() {
        return message -> notificationService.processOrderStatus(message.getPayload());
    }

}
