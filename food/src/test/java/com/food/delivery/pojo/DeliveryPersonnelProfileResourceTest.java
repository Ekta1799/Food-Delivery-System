package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeliveryPersonnelProfileResourceTest {

	private DeliveryPersonnelProfileResource deliveryPersonnelProfileResource;

	@BeforeEach
	void setUp() {
		deliveryPersonnelProfileResource = new DeliveryPersonnelProfileResource();
	}

	@Test
	void testFirstnameGetterAndSetter() {
		String firstname = "vicky";
		deliveryPersonnelProfileResource.setFirstname(firstname);
		assertEquals(firstname, deliveryPersonnelProfileResource.getFirstname());
	}

	@Test
	void testLastnameGetterAndSetter() {
		String lastname = "naik";
		deliveryPersonnelProfileResource.setLastname(lastname);
		assertEquals(lastname, deliveryPersonnelProfileResource.getLastname());
	}

	@Test
	void testUsernameGetterAndSetter() {
		String username = "vnaik";
		deliveryPersonnelProfileResource.setUsername(username);
		assertEquals(username, deliveryPersonnelProfileResource.getUsername());
	}

	@Test
	void testPhoneNoGetterAndSetter() {
		String phoneNo = "1234567890";
		deliveryPersonnelProfileResource.setPhone_no(phoneNo);
		assertEquals(phoneNo, deliveryPersonnelProfileResource.getPhone_no());
	}

	@Test
	void testVehicleTypeGetterAndSetter() {
		String vehicleType = "Bike";
		deliveryPersonnelProfileResource.setVehicle_type(vehicleType);
		assertEquals(vehicleType, deliveryPersonnelProfileResource.getVehicle_type());
	}

	@Test
	void testAvailabilityGetterAndSetter() {
		deliveryPersonnelProfileResource.setAvailablity(true);
		assertTrue(deliveryPersonnelProfileResource.isAvailability());
	}
}
