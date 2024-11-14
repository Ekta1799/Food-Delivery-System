package com.food.delivery.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.food.delivery.model.Delivery;
import com.food.delivery.model.DeliveryPersonnelProfile;
import com.food.delivery.repository.DeliveryPersonnelProfileRepository;
import com.food.delivery.repository.DeliveryRepository;

class DeliveryPersonnelProfileServiceImplTest {

	@Mock
	private DeliveryPersonnelProfileRepository repo;

	@Mock
	private DeliveryRepository deliveryRepo;

	@InjectMocks
	private DeliveryPersonnelProfileServiceImpl deliveryPersonnelProfileService;

	private DeliveryPersonnelProfile deliveryPersonnel;
	private Delivery delivery;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		deliveryPersonnel = new DeliveryPersonnelProfile();
		deliveryPersonnel.setFirstname("ABC");
		deliveryPersonnel.setLastname("DEF");
		deliveryPersonnel.setUsername("ABCDEF");

		delivery = new Delivery();
		delivery.setOrder_id(1L);
		delivery.setDelivery_personnel_id(1L);
		delivery.setDelivery_status("Pending");
	}

	@Test
	void testAddDeliveryPersonnelProfile() {
		deliveryPersonnelProfileService.addDeliveryPersonnelProfile(deliveryPersonnel);

		verify(repo, times(1)).save(deliveryPersonnel);
	}

	@Test
	void testGetDeliveryPersonnelProfile() {

		String firstname = "ABC";
		when(repo.getDeliveryPersonnelProfile(firstname)).thenReturn(deliveryPersonnel);

		DeliveryPersonnelProfile result = deliveryPersonnelProfileService.getDeliveryPersonnelProfile(firstname);

		assertNotNull(result);
		verify(repo, times(1)).getDeliveryPersonnelProfile(firstname);
	}

	@Test
	void testAddDeliveryDetails() {

		deliveryPersonnelProfileService.addDeliveryDetails(delivery);

		verify(deliveryRepo, times(1)).save(delivery);
	}
}
