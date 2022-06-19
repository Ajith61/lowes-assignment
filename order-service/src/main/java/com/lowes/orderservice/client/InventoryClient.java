package com.lowes.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//feign client to check stock from order0service
@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/v1.0.0/inventory/checkStock")
    Boolean checkStock(@RequestParam("productId") String productId);
}
