package com.food.delivery.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuResourceTest {

    private MenuResource menuResource;

    @BeforeEach
    void setUp() {
        menuResource = new MenuResource();
    }

    @Test
    void testGetFoodItem() {
        // Set a food item and check if the getter returns the correct value
        String expectedFoodItem = "Pizza";
        menuResource.setFood_item(expectedFoodItem);
        assertEquals(expectedFoodItem, menuResource.getFood_item());
    }

    @Test
    void testSetFoodItem() {
        // Test the setter method for food item
        String foodItem = "Burger";
        menuResource.setFood_item(foodItem);
        assertEquals(foodItem, menuResource.getFood_item());
    }

    @Test
    void testGetPrice() {
        // Set a price and check if the getter returns the correct value
        double expectedPrice = 12.99;
        menuResource.setPrice(expectedPrice);
        assertEquals(expectedPrice, menuResource.getPrice());
    }

    @Test
    void testSetPrice() {
        // Test the setter method for price
        double price = 5.49;
        menuResource.setPrice(price);
        assertEquals(price, menuResource.getPrice());
    }

    @Test
    void testGetCuisine() {
        // Set a cuisine and check if the getter returns the correct value
        String expectedCuisine = "Italian";
        menuResource.setCuisine(expectedCuisine);
        assertEquals(expectedCuisine, menuResource.getCuisine());
    }

    @Test
    void testSetCuisine() {
        // Test the setter method for cuisine
        String cuisine = "Chinese";
        menuResource.setCuisine(cuisine);
        assertEquals(cuisine, menuResource.getCuisine());
    }

    @Test
    void testIsVeg() {
        // Set a veg status and check if the getter returns the correct value
        Boolean expectedVeg = true;
        menuResource.setVeg(expectedVeg);
        assertEquals(expectedVeg, menuResource.isVeg());
    }

    @Test
    void testSetVeg() {
        // Test the setter method for veg status
        Boolean veg = false;
        menuResource.setVeg(veg);
        assertEquals(veg, menuResource.isVeg());
    }

    @Test
    void testGetRestaurantName() {
        // Set a restaurant name and check if the getter returns the correct value
        String expectedRestaurantName = "Domino's Pizza";
        menuResource.setRestaurant_name(expectedRestaurantName);
        assertEquals(expectedRestaurantName, menuResource.getRestaurant_name());
    }

    @Test
    void testSetRestaurantName() {
        // Test the setter method for restaurant name
        String restaurantName = "McDonald's";
        menuResource.setRestaurant_name(restaurantName);
        assertEquals(restaurantName, menuResource.getRestaurant_name());
    }
}
