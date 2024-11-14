package com.food.delivery.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.food.delivery.facade.DeliveryPersonnelProfileFacade;
import com.food.delivery.model.User;
import com.food.delivery.pojo.DeliveryPersonnelProfileResource;
import com.food.delivery.pojo.DeliveryResource;
import com.food.delivery.pojo.OrdersResource;
import com.food.delivery.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class DeliveryPersonnelProfileControllerTest {

	private MockMvc mockMvc;

	@Mock
	private DeliveryPersonnelProfileFacade deliveryPersonnelProfileFacade;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private DeliveryPersonnelProfileController deliveryPersonnelProfileController;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(deliveryPersonnelProfileController).build();
	}

	@Test
	void testAddDeliveryPersonnelProfile_Success() throws Exception {
		DeliveryPersonnelProfileResource resource = new DeliveryPersonnelProfileResource();

		mockMvc.perform(MockMvcRequestBuilders.post("/deliveryPersonnelProfile").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"testuser\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Delivery Personnel profile added successfully"));
	}

	@Test
	void testGetDeliveryPersonnelProfile_Success() throws Exception {
		DeliveryPersonnelProfileResource resource = new DeliveryPersonnelProfileResource();
		resource.setUsername("testuser");

		when(deliveryPersonnelProfileFacade.getDeliveryPersonnelProfile("user")).thenReturn(resource);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/deliveryPersonnelProfile/user").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.username").value("testuser"));
	}

	@Test
	void testGetDeliveryPersonnelProfile_NotFound() throws Exception {
		when(deliveryPersonnelProfileFacade.getDeliveryPersonnelProfile("user"))
				.thenThrow(new RuntimeException("User profile not found"));

		mockMvc.perform(
				MockMvcRequestBuilders.get("/deliveryPersonnelProfile/user").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message").value("Error: User profile not found for user user!"));
	}

	@Test
	void testUpdateDeliveryPersonnelProfile_Success() throws Exception {
		when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(new User()));

		mockMvc.perform(MockMvcRequestBuilders.put("/deliveryPersonnelProfile").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"testuser\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Delivery personnel's Profile Updated successfully!"));
	}

	@Test
	void testGetOrdersForDelivery_Success() throws Exception {
		List<OrdersResource> orders = new ArrayList<>();
		OrdersResource order = new OrdersResource();
		orders.add(order);

		when(deliveryPersonnelProfileFacade.getDeliveryOrders()).thenReturn(orders);

		mockMvc.perform(MockMvcRequestBuilders.get("/deliveryOrders").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testAddDeliveryDetails_Success() throws Exception {
		DeliveryResource deliveryResource = new DeliveryResource();
		deliveryResource.setUsername("testuser");

		mockMvc.perform(MockMvcRequestBuilders.post("/deliveryDetails").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"testuser\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Delivery Details added successfully"));
	}

	@Test
	void testUpdateDeliveryStatus_Success() throws Exception {
		User user = new User();
		user.setId(1L);

		when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

		mockMvc.perform(MockMvcRequestBuilders.put("/deliveryStatus/testuser").contentType(MediaType.APPLICATION_JSON)
				.content("\"picked up\"")).andExpect(status().isOk())
				.andExpect(jsonPath("$.message").value("Delivery Status Updated successfully!"));
	}
}
