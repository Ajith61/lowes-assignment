package com.lowes.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
// To enable eureka client
@EnableEurekaClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
	
	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		// route to client
        return builder.routes()
                .route(StringConstants.PRODUCT_SERVICE_KEY,
						r -> r.path("/v1.0.0/product/**")
                        .filters(f -> f.stripPrefix(0)).uri("http://localhost:8001"))
                .route(StringConstants.ORDER_SERVICE_KEY,
						r -> r.path("/v1.0.0/order/**")
                        .filters(f -> f.stripPrefix(2)).uri("http://localhost:8002"))
                .build();
    }
}
