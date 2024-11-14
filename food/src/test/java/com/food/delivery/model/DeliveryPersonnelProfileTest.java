package com.food.delivery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeliveryPersonnelProfileTest {

	private DeliveryPersonnelProfile deliveryPersonnelProfile;

	@BeforeEach
	void setUp() {
		deliveryPersonnelProfile = new DeliveryPersonnelProfile();
	}

	@Test
	void testIdGetterAndSetter() {
		Long id = 1L;
		deliveryPersonnelProfile.setId(id);
		assertEquals(id, deliveryPersonnelProfile.getId());
	}

	@Test
	void testFirstnameGetterAndSetter() {
		String firstname = "Simran";
		deliveryPersonnelProfile.setFirstname(firstname);
		assertEquals(firstname, deliveryPersonnelProfile.getFirstname());
	}

	@Test
	void testLastnameGetterAndSetter() {
		String lastname = "Verma";
		deliveryPersonnelProfile.setLastname(lastname);
		assertEquals(lastname, deliveryPersonnelProfile.getLastname());
	}

	@Test
	void testUsernameGetterAndSetter() {
		String username = "simi";
		deliveryPersonnelProfile.setUsername(username);
		assertEquals(username, deliveryPersonnelProfile.getUsername());
	}

	@Test
	void testPhoneNoGetterAndSetter() {
		String phoneNo = "1234567890";
		deliveryPersonnelProfile.setPhone_no(phoneNo);
		assertEquals(phoneNo, deliveryPersonnelProfile.getPhone_no());
	}

	@Test
	void testVehicleTypeGetterAndSetter() {
		String vehicleType = "Bike";
		deliveryPersonnelProfile.setVehicle_type(vehicleType);
		assertEquals(vehicleType, deliveryPersonnelProfile.getVehicle_type());
	}

	@Test
	void testUserIdGetterAndSetter() {
		Long userId = 2L;
		deliveryPersonnelProfile.setUser_id(userId);
		assertEquals(userId, deliveryPersonnelProfile.getUser_id());
	}

	@Test
	void testAvailabilityGetterAndSetter() {
		deliveryPersonnelProfile.setAvailability(true);
		assertTrue(deliveryPersonnelProfile.isAvailability());
	}
}
