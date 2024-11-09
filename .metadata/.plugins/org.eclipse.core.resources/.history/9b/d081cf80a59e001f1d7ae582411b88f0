package com.food.delivery.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdersResourceTest {

    private OrdersResource ordersResource;

    @BeforeEach
    void setUp() {
        // Initialize the OrdersResource object before each test
        ordersResource = new OrdersResource();
    }

    @Test
    void testGetAndSetRestaurantName() {
        // Test setter and getter for restaurantName
        String restaurantName = "Pizza Hut";
        ordersResource.setRestaurantName(restaurantName);
        assertEquals(restaurantName, ordersResource.getRestaurantName());
    }

    @Test
    void testGetAndSetFoodName() {
        // Test setter and getter for foodName
        String foodName = "Pepperoni Pizza";
        ordersResource.setFoodName(foodName);
        assertEquals(foodName, ordersResource.getFoodName());
    }

    @Test
    void testGetAndSetFoodPrice() {
        // Test setter and getter for food_price
        double foodPrice = 15.99;
        ordersResource.setFood_price(foodPrice);
        assertEquals(foodPrice, ordersResource.getFood_price());
    }

    @Test
    void testGetAndSetUsername() {
        // Test setter and getter for username
        String username = "john_doe";
        ordersResource.setUsername(username);
        assertEquals(username, ordersResource.getUsername());
    }

    @Test
    void testGetAndSetStatus() {
        // Test setter and getter for status
        String status = "OUT_FOR_DELIVERY";
        ordersResource.setStatus(status);
        assertEquals(status, ordersResource.getStatus());
    }
}
