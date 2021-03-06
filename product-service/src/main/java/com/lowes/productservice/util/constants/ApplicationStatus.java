package com.lowes.productservice.util.constants;

import java.util.HashMap;
import java.util.Map;

public enum ApplicationStatus {

	VALID_RESPONSE(200, "Success"), 
	DELETED_SUCCESS(200, "Deleted Successfully"),
	UPDATED_SUCESS(204, "Updated Sucessfully"), 
	CREATED(201, "Created"),
	PRODUCT_ALREADY_REGISTERED(403, "Product Already Registered"),
	NO_DATA(402, "No record"), 
	ERROR(500, "Something Went Wrong");

	private static final Map<Integer, ApplicationStatus> map = new HashMap<>(values().length, 1);
	
	private ApplicationStatus(int statusCode, String statusMesssage) {
		this.statusMesssage = statusMesssage;
		this.statusCode = statusCode;
	}

	public int statusCode;
	public String statusMesssage;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMesssage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMesssage = statusMessage;
	}
	
	public static ApplicationStatus of(int statusId) {
		ApplicationStatus result = map.get(statusId);
		if (result == null) {
			throw new IllegalArgumentException("Invalid error code: " + statusId);
		}
		return result;
	}
}
