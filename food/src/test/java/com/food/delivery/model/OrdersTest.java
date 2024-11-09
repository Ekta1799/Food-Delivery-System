package com.food.delivery.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdersTest {

    private Orders order;

    @BeforeEach
    void setUp() {
        order = new Orders();
    }

    @Test
    void testSetAndGetId() {
        order.setId(1L);
        assertEquals(1L, order.getId());
    }

    @Test
    void testSetAndGetRestaurantId() {
        order.setRestaurant_id(101L);
        assertEquals(101L, order.getRestaurant_id());
    }

    @Test
    void testSetAndGetFoodId() {
        order.setFood_id(202L);
        assertEquals(202L, order.getFood_id());
    }

    @Test
    void testSetAndGetFoodPrice() {
        order.setFood_price(250.75);
        assertEquals(250.75, order.getFood_price());
    }

    @Test
    void testSetAndGetCustomerId() {
        order.setCustomer_id(303L);
        assertEquals(303L, order.getCustomer_id());
    }

    @Test
    void testSetAndGetStatus() {
        order.setStatus("Delivered");
        assertEquals("Delivered", order.getStatus());
    }
}
