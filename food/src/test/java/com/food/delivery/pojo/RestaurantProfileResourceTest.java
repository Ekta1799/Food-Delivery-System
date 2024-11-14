package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RestaurantProfileResourceTest {

    private RestaurantProfileResource restaurantProfileResource;

    @BeforeEach
    void setUp() {
        restaurantProfileResource = new RestaurantProfileResource();
    }

    @Test
    void testGetAndSetFirstname() {
        String firstname = "Alia";
        restaurantProfileResource.setFirstname(firstname);
        assertEquals(firstname, restaurantProfileResource.getFirstname());
    }

    @Test
    void testGetAndSetLastname() {
        String lastname = "Roy";
        restaurantProfileResource.setLastname(lastname);
        assertEquals(lastname, restaurantProfileResource.getLastname());
    }

    @Test
    void testGetAndSetUsername() {
        String username = "ar_restaurant";
        restaurantProfileResource.setUsername(username);
        assertEquals(username, restaurantProfileResource.getUsername());
    }

    @Test
    void testGetAndSetRestaurantName() {
        String restaurantName = "Pizza Palace";
        restaurantProfileResource.setRestaurant_name(restaurantName);
        assertEquals(restaurantName, restaurantProfileResource.getRestaurant_name());
    }

    @Test
    void testGetAndSetPhoneNo() {
        String phoneNo = "123-456-7890";
        restaurantProfileResource.setPhone_no(phoneNo);
        assertEquals(phoneNo, restaurantProfileResource.getPhone_no());
    }

    @Test
    void testGetAndSetAddress() {
        String address = "123 Pizza Street, Food City";
        restaurantProfileResource.setAddress(address);
        assertEquals(address, restaurantProfileResource.getAddress());
    }

    @Test
    void testGetAndSetHoursOfOperation() {
        int hoursOfOperation = 12;
        restaurantProfileResource.setHours_of_operation(hoursOfOperation);
        assertEquals(hoursOfOperation, restaurantProfileResource.getHours_of_operation());
    }
}
