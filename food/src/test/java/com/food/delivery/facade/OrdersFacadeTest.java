package com.food.delivery.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.food.delivery.model.Orders;
import com.food.delivery.model.User;
import com.food.delivery.pojo.OrdersResource;
import com.food.delivery.repository.MenuRepository;
import com.food.delivery.repository.RestaurantProfileRepository;
import com.food.delivery.repository.UserRepository;
import com.food.delivery.services.OrdersService;

public class OrdersFacadeTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private RestaurantProfileRepository restaurantProfileRepo;

	@Mock
	private MenuRepository menuRepo;

	@Mock
	private OrdersService ordersService;

	@InjectMocks
	private OrdersFacade ordersFacade;

	private OrdersResource ordersResource;
	private User user;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		// Create mock User data
		user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		// Create mock OrdersResource data
		ordersResource = new OrdersResource();
		ordersResource.setUsername("testUser");
		ordersResource.setRestaurantName("Test Restaurant");
		ordersResource.setFoodName("Pizza");
		ordersResource.setFood_price(100.0);
		ordersResource.setStatus("Pending");
	}

	@Test
	public void testAddOrders() {
		// Mock the repository and service methods
		when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
		when(restaurantProfileRepo.getRestaurantIdByRestaurantName("Test Restaurant")).thenReturn(1L);
		when(menuRepo.getFoodIdFromFoodName("Pizza")).thenReturn(1L);

		// Call the method
		ordersFacade.addOrders(ordersResource);

		// Verify that the service method was called with correct parameters
		verify(ordersService, times(1)).addOrders(any(Orders.class));
	}

	@Test
	public void testUpdateOrders() {
		// Mock the repository and service methods
		when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
		when(restaurantProfileRepo.getRestaurantIdByRestaurantName("Test Restaurant")).thenReturn(1L);
		when(menuRepo.getFoodIdFromFoodName("Pizza")).thenReturn(1L);

		// Call the method
		boolean result = ordersFacade.updateOrders(ordersResource);

		// Verify the outcome
		assertTrue(result);
		verify(ordersService, times(1)).updateStatus(anyLong(), anyLong(), anyLong(), eq("Pending"));
	}

	@Test
	public void testGetOrdersByRestaurantOwner() {
		// Mock the repository and service methods
		when(restaurantProfileRepo.getRestaurantIdByRestaurantName("Test Restaurant")).thenReturn(1L);

		Orders order = new Orders();
		order.setId(1L); // Ensure the order's ID is set to 1L (or use the customer_id)
		order.setCustomer_id(1L); // Set the customer ID to match the mock
		order.setRestaurant_id(1L);
		order.setFood_id(1L);
		order.setFood_price(100.0);
		order.setStatus("Pending");

		List<Orders> ordersList = new ArrayList<>();
		ordersList.add(order);

		// Mock the service and repository calls
		when(ordersService.getOrdersByRestaurantOwner(1L)).thenReturn(ordersList);
		when(userRepository.userByUserId(1L)).thenReturn("testUser");
		when(menuRepo.getFoodNameFromFoodId(1L)).thenReturn("Pizza");
		when(restaurantProfileRepo.getRestaurantNameByRestaurantId(1L)).thenReturn("Test Restaurant");

		// Call the method
		List<OrdersResource> result = ordersFacade.getOrdersByRestaurantOwner("Test Restaurant");

		// Verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("testUser", result.get(0).getUsername());
		assertEquals("Pizza", result.get(0).getFoodName());
	}

	@Test
	public void testGetOrderStatusByCustomer() {
		// Create a mock user
		User user = new User();
		user.setId(1L); // Set user ID to match the customer_id in Orders
		user.setUsername("testUser");

		// Mock the repository and service methods
		when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

		Orders order = new Orders();
		order.setId(1L); // Set order ID to 1L (which is the same as customer_id in this case)
		order.setCustomer_id(1L); // Ensure that customer_id is correctly set to match the mock
		order.setRestaurant_id(1L);
		order.setFood_id(1L);
		order.setFood_price(100.0);
		order.setStatus("Pending");

		List<Orders> ordersList = new ArrayList<>();
		ordersList.add(order);

		// Mock the service and repository calls
		when(ordersService.getOrderStatusByCustomer(1L, "Pending")).thenReturn(ordersList);
		when(userRepository.userByUserId(1L)).thenReturn("testUser");
		when(menuRepo.getFoodNameFromFoodId(1L)).thenReturn("Pizza");
		when(restaurantProfileRepo.getRestaurantNameByRestaurantId(1L)).thenReturn("Test Restaurant");

		// Call the method
		List<OrdersResource> result = ordersFacade.getOrderStatusByCustomer("testUser", "Pending");

		// Verify the result
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("testUser", result.get(0).getUsername());
		assertEquals("Pizza", result.get(0).getFoodName());
	}

}
