package com.food.delivery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeliveryTest {

	private Delivery delivery;

	@BeforeEach
	void setUp() {
		delivery = new Delivery();
	}

	@Test
	void testIdGetterAndSetter() {

		Long id = 1L;

		delivery.setId(id);

		assertEquals(id, delivery.getId());
	}

	@Test
	void testDeliveryPersonnelIdGetterAndSetter() {
		Long deliveryPersonnelId = 10L;

		delivery.setDelivery_personnel_id(deliveryPersonnelId);

		assertEquals(deliveryPersonnelId, delivery.getDelivery_personnel_id());
	}

	@Test
	void testOrderIdGetterAndSetter() {
		Long orderId = 20L;

		delivery.setOrder_id(orderId);

		assertEquals(orderId, delivery.getOrder_id());
	}

	@Test
	void testDeliveryStatusGetterAndSetter() {
		String status = "Delivered";

		delivery.setDelivery_status(status);

		assertEquals(status, delivery.getDelivery_status());
	}

	@Test
	void testDeliveryObjectCreation() {
		assertNotNull(delivery);
	}
}
