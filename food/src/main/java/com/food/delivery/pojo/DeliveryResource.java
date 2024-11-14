package com.food.delivery.pojo;

public class DeliveryResource {

	private String username;

	private String delivery_status;

	private Long orderId;

	public String getDelivery_status() {
		return delivery_status;
	}

	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
