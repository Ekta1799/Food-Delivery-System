package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrdersResourceTest {

    private OrdersResource ordersResource;

    @BeforeEach
    void setUp() {
        ordersResource = new OrdersResource();
    }

    @Test
    void testGetAndSetRestaurantName() {
        String restaurantName = "Pizza Hut";
        ordersResource.setRestaurantName(restaurantName);
        assertEquals(restaurantName, ordersResource.getRestaurantName());
    }

    @Test
    void testGetAndSetFoodName() {
        String foodName = "Pepperoni Pizza";
        ordersResource.setFoodName(foodName);
        assertEquals(foodName, ordersResource.getFoodName());
    }

    @Test
    void testGetAndSetFoodPrice() {
        double foodPrice = 15.99;
        ordersResource.setFood_price(foodPrice);
        assertEquals(foodPrice, ordersResource.getFood_price());
    }

    @Test
    void testGetAndSetUsername() {
        String username = "abc12";
        ordersResource.setUsername(username);
        assertEquals(username, ordersResource.getUsername());
    }

    @Test
    void testGetAndSetStatus() {
        String status = "OUT_FOR_DELIVERY";
        ordersResource.setStatus(status);
        assertEquals(status, ordersResource.getStatus());
    }
}
