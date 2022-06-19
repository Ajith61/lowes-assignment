package com.lowes.inventoryservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.lowes.inventoryservice.model.dao.InventoryDao;
import com.lowes.inventoryservice.repository.InventoryRepository;
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
		
		inventoryService.createInventory(productId,stockCount);
	}

}
