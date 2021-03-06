package com.lowes.inventoryservice.service;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowes.inventoryservice.model.dao.InventoryDao;
import com.lowes.inventoryservice.repository.InventoryRepository;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class InventoryService {

	@Autowired
	InventoryRepository inventoryRepository;
	private Logger logger = LoggerFactory.getLogger(InventoryService.class);
	public void saveProductInventory(String payload) throws JsonMappingException, JsonProcessingException {
		 ObjectMapper mapper = new ObjectMapper();
	        
	        InventoryDao inventoryDao = mapper.readValue(payload, InventoryDao.class);
	        if (inventoryDao!=null) {
	        	//save in a inventory table
	        	inventoryRepository.save(inventoryDao);
	        	logger.info("Inventory Data Saved");
	        }
		 
	}
	public Boolean getProductStockCount(String productId) {
		// check if the stock is exist or not
		Optional<InventoryDao> inventorys = inventoryRepository.findByProductId(productId);
		InventoryDao inventory = inventorys.get();
		return inventory.getStockCount() > 0;
	}
	public void createInventory(String productId, int stockCount) {
		InventoryDao inventoryDao = new InventoryDao();
		inventoryDao.setProductId(productId);
		inventoryDao.setStockCount(stockCount);
		inventoryRepository.save(inventoryDao);	
	}

}
