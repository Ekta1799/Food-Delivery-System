package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuResourceTest {

    private MenuResource menuResource;

    @BeforeEach
    void setUp() {
        menuResource = new MenuResource();
    }

    @Test
    void testGetFoodItem() {
        String expectedFoodItem = "Pizza";
        menuResource.setFood_item(expectedFoodItem);
        assertEquals(expectedFoodItem, menuResource.getFood_item());
    }

    @Test
    void testSetFoodItem() {
        String foodItem = "Burger";
        menuResource.setFood_item(foodItem);
        assertEquals(foodItem, menuResource.getFood_item());
    }

    @Test
    void testGetPrice() {
        double expectedPrice = 12.99;
        menuResource.setPrice(expectedPrice);
        assertEquals(expectedPrice, menuResource.getPrice());
    }

    @Test
    void testSetPrice() {
        double price = 5.49;
        menuResource.setPrice(price);
        assertEquals(price, menuResource.getPrice());
    }

    @Test
    void testGetCuisine() {
        String expectedCuisine = "Italian";
        menuResource.setCuisine(expectedCuisine);
        assertEquals(expectedCuisine, menuResource.getCuisine());
    }

    @Test
    void testSetCuisine() {
        String cuisine = "Chinese";
        menuResource.setCuisine(cuisine);
        assertEquals(cuisine, menuResource.getCuisine());
    }

    @Test
    void testIsVeg() {
        Boolean expectedVeg = true;
        menuResource.setVeg(expectedVeg);
        assertEquals(expectedVeg, menuResource.isVeg());
    }

    @Test
    void testSetVeg() {
        Boolean veg = false;
        menuResource.setVeg(veg);
        assertEquals(veg, menuResource.isVeg());
    }

    @Test
    void testGetRestaurantName() {
        String expectedRestaurantName = "Domino's Pizza";
        menuResource.setRestaurant_name(expectedRestaurantName);
        assertEquals(expectedRestaurantName, menuResource.getRestaurant_name());
    }

    @Test
    void testSetRestaurantName() {
        String restaurantName = "McDonald's";
        menuResource.setRestaurant_name(restaurantName);
        assertEquals(restaurantName, menuResource.getRestaurant_name());
    }
}
