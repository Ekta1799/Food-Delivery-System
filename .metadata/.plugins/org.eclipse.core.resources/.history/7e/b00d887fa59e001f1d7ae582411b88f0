package com.food.delivery.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuTest {

    private Menu menu;

    @BeforeEach
    void setUp() {
        menu = new Menu();
    }

    @Test
    void testSetAndGetId() {
        menu.setId(1L);
        assertEquals(1L, menu.getId());
    }

    @Test
    void testSetAndGetRestaurantId() {
        menu.setRestaurant_id(101L);
        assertEquals(101L, menu.getRestaurant_id());
    }

    @Test
    void testSetAndGetFoodItem() {
        menu.setFood_item("Pizza");
        assertEquals("Pizza", menu.getFood_item());
    }

    @Test
    void testSetAndGetPrice() {
        menu.setPrice(250.5);
        assertEquals(250.5, menu.getPrice());
    }

    @Test
    void testSetAndGetCuisine() {
        menu.setCuisine("Italian");
        assertEquals("Italian", menu.getCuisine());
    }

    @Test
    void testSetAndGetVeg() {
        menu.setVeg(true);
        assertTrue(menu.isVeg());
        
        menu.setVeg(false);
        assertFalse(menu.isVeg());
    }
}
