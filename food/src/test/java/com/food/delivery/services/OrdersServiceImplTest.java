package com.food.delivery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.food.delivery.model.Orders;
import com.food.delivery.repository.OrdersRepository;

class OrdersServiceImplTest {

    @Mock
    private OrdersRepository ordersRepo;

    @InjectMocks
    private OrdersServiceImpl ordersService;

    private Orders order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Orders();
        order.setCustomer_id(1L);
        order.setRestaurant_id(1L);
        order.setFood_id(1L);
        order.setStatus("Pending");
    }

    @Test
    void testAddOrders() {

        ordersService.addOrders(order);

        verify(ordersRepo, times(1)).save(order);
    }

    @Test
    void testUpdateStatus() {

        Long customerId = 1L;
        Long restaurantId = 1L;
        Long foodId = 1L;
        String status = "Completed";

        ordersService.updateStatus(customerId, restaurantId, foodId, status);

        verify(ordersRepo, times(1)).updateStatus(customerId, restaurantId, foodId, status);
    }

    @Test
    void testGetOrdersByRestaurantOwner() {

        Long restaurantId = 1L;
        List<Orders> ordersList = Arrays.asList(order);

        when(ordersRepo.getOrdersByRestaurantId(restaurantId)).thenReturn(ordersList);

        List<Orders> result = ordersService.getOrdersByRestaurantOwner(restaurantId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ordersRepo, times(1)).getOrdersByRestaurantId(restaurantId);
    }

    @Test
    void testGetOrderStatusByCustomer_withStatus() {
        Long customerId = 1L;
        String status = "Pending";
        List<Orders> ordersList = Arrays.asList(order);

        when(ordersRepo.getOrderByCustomerBasedOnStatus(customerId, status)).thenReturn(ordersList);

        List<Orders> result = ordersService.getOrderStatusByCustomer(customerId, status);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ordersRepo, times(1)).getOrderByCustomerBasedOnStatus(customerId, status);
    }

    @Test
    void testGetOrderStatusByCustomer_withoutStatus() {
        Long customerId = 1L;
        List<Orders> ordersList = Arrays.asList(order);

        when(ordersRepo.getOrderByCustomer(customerId)).thenReturn(ordersList);

        List<Orders> result = ordersService.getOrderStatusByCustomer(customerId, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(ordersRepo, times(1)).getOrderByCustomer(customerId);
    }
}
