package com.food.delivery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerProfileTest {

    private CustomerProfile customerProfile;

    @BeforeEach
    void setUp() {
        // Create a new CustomerProfile before each test
        customerProfile = new CustomerProfile();
    }

    @Test
    void testGetAndSetId() {
        // Set the ID and assert it's correctly set
        customerProfile.setId(1L);
        assertEquals(1L, customerProfile.getId());
    }

    @Test
    void testGetAndSetFirstname() {
        // Set the firstname and assert it's correctly set
        customerProfile.setFirstname("John");
        assertEquals("John", customerProfile.getFirstname());
    }

    @Test
    void testGetAndSetLastname() {
        // Set the lastname and assert it's correctly set
        customerProfile.setLastname("Doe");
        assertEquals("Doe", customerProfile.getLastname());
    }

    @Test
    void testGetAndSetUsername() {
        // Set the username and assert it's correctly set
        customerProfile.setUsername("john_doe");
        assertEquals("john_doe", customerProfile.getUsername());
    }

    @Test
    void testGetAndSetPhoneNo() {
        // Set the phone number and assert it's correctly set
        customerProfile.setPhone_no("1234567890");
        assertEquals("1234567890", customerProfile.getPhone_no());
    }

    @Test
    void testGetAndSetAddress() {
        // Set the address and assert it's correctly set
        customerProfile.setAddress("123 Main Street");
        assertEquals("123 Main Street", customerProfile.getAddress());
    }

    @Test
    void testGetAndSetPaymentType() {
        // Set the payment type and assert it's correctly set
        customerProfile.setPayment_type("Credit Card");
        assertEquals("Credit Card", customerProfile.getPayment_type());
    }

    @Test
    void testGetAndSetUserId() {
        // Set the user ID and assert it's correctly set
        customerProfile.setUser_id(100L);
        assertEquals(100L, customerProfile.getUser_id());
    }
}
