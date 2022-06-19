package com.lowes.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {
    @GetMapping("/v1.0.0/inventory/checkStock")
    Boolean checkStock(@RequestParam("productId") String productId);
}
