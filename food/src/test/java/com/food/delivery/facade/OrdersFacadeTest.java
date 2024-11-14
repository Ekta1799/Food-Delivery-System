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

		user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		ordersResource = new OrdersResource();
		ordersResource.setUsername("testUser");
		ordersResource.setRestaurantName("Test Restaurant");
		ordersResource.setFoodName("Pizza");
		ordersResource.setFood_price(100.0);
		ordersResource.setStatus("Pending");
	}

	@Test
	public void testAddOrders() {

		when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
		when(restaurantProfileRepo.getRestaurantIdByRestaurantName("Test Restaurant")).thenReturn(1L);
		when(menuRepo.getFoodIdFromFoodName("Pizza")).thenReturn(1L);

		ordersFacade.addOrders(ordersResource);

		verify(ordersService, times(1)).addOrders(any(Orders.class));
	}

	@Test
	public void testUpdateOrders() {

		when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
		when(restaurantProfileRepo.getRestaurantIdByRestaurantName("Test Restaurant")).thenReturn(1L);
		when(menuRepo.getFoodIdFromFoodName("Pizza")).thenReturn(1L);

		boolean result = ordersFacade.updateOrders(ordersResource);

		assertTrue(result);
		verify(ordersService, times(1)).updateStatus(anyLong(), anyLong(), anyLong(), eq("Pending"));
	}

	@Test
	public void testGetOrdersByRestaurantOwner() {

		when(restaurantProfileRepo.getRestaurantIdByRestaurantName("Test Restaurant")).thenReturn(1L);

		Orders order = new Orders();
		order.setId(1L);
		order.setCustomer_id(1L);
		order.setRestaurant_id(1L);
		order.setFood_id(1L);
		order.setFood_price(100.0);
		order.setStatus("Pending");

		List<Orders> ordersList = new ArrayList<>();
		ordersList.add(order);

		when(ordersService.getOrdersByRestaurantOwner(1L)).thenReturn(ordersList);
		when(userRepository.userByUserId(1L)).thenReturn("testUser");
		when(menuRepo.getFoodNameFromFoodId(1L)).thenReturn("Pizza");
		when(restaurantProfileRepo.getRestaurantNameByRestaurantId(1L)).thenReturn("Test Restaurant");

		List<OrdersResource> result = ordersFacade.getOrdersByRestaurantOwner("Test Restaurant");

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("testUser", result.get(0).getUsername());
		assertEquals("Pizza", result.get(0).getFoodName());
	}

	@Test
	public void testGetOrderStatusByCustomer() {

		User user = new User();
		user.setId(1L);
		user.setUsername("testUser");

		when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

		Orders order = new Orders();
		order.setId(1L);
		order.setCustomer_id(1L);
		order.setRestaurant_id(1L);
		order.setFood_id(1L);
		order.setFood_price(100.0);
		order.setStatus("Pending");

		List<Orders> ordersList = new ArrayList<>();
		ordersList.add(order);

		when(ordersService.getOrderStatusByCustomer(1L, "Pending")).thenReturn(ordersList);
		when(userRepository.userByUserId(1L)).thenReturn("testUser");
		when(menuRepo.getFoodNameFromFoodId(1L)).thenReturn("Pizza");
		when(restaurantProfileRepo.getRestaurantNameByRestaurantId(1L)).thenReturn("Test Restaurant");

		List<OrdersResource> result = ordersFacade.getOrderStatusByCustomer("testUser", "Pending");

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("testUser", result.get(0).getUsername());
		assertEquals("Pizza", result.get(0).getFoodName());
	}

}
