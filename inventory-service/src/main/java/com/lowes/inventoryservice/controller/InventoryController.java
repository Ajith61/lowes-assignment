package com.lowes.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lowes.inventoryservice.service.InventoryService;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/v1.0.0/inventory")
public class InventoryController {
	@Autowired
	private InventoryService inventoryService;

	@GetMapping("/checkStock")
	public Boolean findByProductId(@RequestParam("productId") String productId) {
		return inventoryService.getProductStockCount(productId);
	}

	@PostMapping("/create")
	public void createInventory(@RequestParam String productId, @RequestParam int stockCount) {
		inventoryService.createInventory(productId, stockCount);
	}

}
