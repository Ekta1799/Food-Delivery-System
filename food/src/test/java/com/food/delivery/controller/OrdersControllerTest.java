package com.food.delivery.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

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

import com.food.delivery.facade.OrdersFacade;
import com.food.delivery.facade.RestaurantProfileFacade;
import com.food.delivery.pojo.OrdersResource;

@ExtendWith(SpringExtension.class)
public class OrdersControllerTest {

	private MockMvc mockMvc;

	@Mock
	private OrdersFacade ordersFacade;

	@Mock
	private RestaurantProfileFacade restaurantProfileFacade;

	@InjectMocks
	private OrderController orderController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
	}

	@Test
	void testAddOrder_BadRequest1() throws Exception {
		OrdersResource order = new OrdersResource();
		order.setUsername("customer1");
		order.setRestaurantName("Pizza Place");
		order.setFood_price(15.99);

		mockMvc.perform(MockMvcRequestBuilders.post("/orders").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"customer1\",\"restaurantName\":\"Pizza Place\",\"food_price\":15.99}"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testAddOrder_BadRequest2() throws Exception {
		OrdersResource order = new OrdersResource();
		order.setUsername(""); // Empty username

		mockMvc.perform(MockMvcRequestBuilders.post("/orders").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"\",\"restaurantName\":\"Pizza Place\",\"food_price\":15.99}"))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testGetOrders_Success() throws Exception {
		List<OrdersResource> ordersList = new ArrayList<>();
		OrdersResource order = new OrdersResource();
		order.setRestaurantName("Pizza Place");
		ordersList.add(order);

		when(ordersFacade.getOrdersByRestaurantOwner("Pizza Place")).thenReturn(ordersList);

		mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/orders").param("restaurant_name", "Pizza Place")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void testGetOrders_NotFound() throws Exception {
		when(ordersFacade.getOrdersByRestaurantOwner("Pizza Place")).thenReturn(new ArrayList<>());

		mockMvc.perform(MockMvcRequestBuilders.get("/restaurants/orders").param("restaurant_name", "Pizza Place")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	void testUpdateOrderStatus_Failure() throws Exception {
		when(ordersFacade.updateOrders(any(OrdersResource.class))).thenReturn(false);

		mockMvc.perform(MockMvcRequestBuilders.put("/orders").contentType(MediaType.APPLICATION_JSON)
				.content("{\"status\":\"DELIVERED\", \\\"foodName\\\":\\\"Pizza\\\",\"username\":\"customer1\",\"restaurantName\":\"Pizza Place\"}"))
				.andExpect(status().isBadRequest());
	}

}
