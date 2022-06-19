package com.lowes.inventoryservice.model.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(value = "inventory_master")
public class InventoryDao {
	@Id
	private String id;
	@Indexed(unique = true)
	private String productId;
	private long stockCount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public long getStockCount() {
		return stockCount;
	}

	public void setStockCount(long stockCount) {
		this.stockCount = stockCount;
	}

}
