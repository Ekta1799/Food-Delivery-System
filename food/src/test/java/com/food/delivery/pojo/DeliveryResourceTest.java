package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeliveryResourceTest {

	private DeliveryResource deliveryResource;

	@BeforeEach
	void setUp() {
		deliveryResource = new DeliveryResource();
	}

	@Test
	void testUsernameGetterAndSetter() {
		String username = "abc12";
		deliveryResource.setUsername(username);
		assertEquals(username, deliveryResource.getUsername());
	}

	@Test
	void testDeliveryStatusGetterAndSetter() {
		String deliveryStatus = "Delivered";
		deliveryResource.setDelivery_status(deliveryStatus);
		assertEquals(deliveryStatus, deliveryResource.getDelivery_status());
	}

	@Test
	void testOrderIdGetterAndSetter() {
		Long orderId = 12345L;
		deliveryResource.setOrderId(orderId);
		assertEquals(orderId, deliveryResource.getOrderId());
	}
}
