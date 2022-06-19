package com.lowes.productservice.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
	
	@NotBlank
	private String productId;
	@NotBlank
	private String productName;
	private long productPrice;
	private long productStock;
	public ProductDto(long productPrice, String productName, String id,int productStock) {
		this.productId = id;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStock = productStock;
	}
	public ProductDto() {
		// TODO Auto-generated constructor stub
	}
	public ProductDto(String id, String productId, String productName, long productPrice, int productStock) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productStock = productStock;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(long productPrice) {
		this.productPrice = productPrice;
	}
	public long getProductStock() {
		return productStock;
	}
	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	@Override
	public String toString() {
		return "ProductDto [productId=" + productId + ", productName=" + productName + ", productPrice=" + productPrice
				+ ", productStock=" + productStock + "]";
	}

	
}
