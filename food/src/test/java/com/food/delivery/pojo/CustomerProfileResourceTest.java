package com.food.delivery.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerProfileResourceTest {

    private CustomerProfileResource customerProfile;

    @BeforeEach
    void setUp() {
        customerProfile = new CustomerProfileResource();
    }

    @Test
    void testFirstname() {
        String firstname = "vicky";
        customerProfile.setFirstname(firstname);
        assertEquals(firstname, customerProfile.getFirstname());
    }

    @Test
    void testLastname() {
        String lastname = "naik";
        customerProfile.setLastname(lastname);
        assertEquals(lastname, customerProfile.getLastname());
    }

    @Test
    void testUsername() {
        String username = "vnaik12";
        customerProfile.setUsername(username);
        assertEquals(username, customerProfile.getUsername());
    }

    @Test
    void testPhoneNo() {
        String phoneNo = "1234567890";
        customerProfile.setPhone_no(phoneNo);
        assertEquals(phoneNo, customerProfile.getPhone_no());
    }

    @Test
    void testAddress() {
        String address = "1234 Elm Street";
        customerProfile.setAddress(address);
        assertEquals(address, customerProfile.getAddress());
    }

    @Test
    void testPaymentType() {
        String paymentType = "Credit Card";
        customerProfile.setPayment_type(paymentType);
        assertEquals(paymentType, customerProfile.getPayment_type());
    }

    @Test
    void testEmptyCustomerProfile() {
        // Verify that the profile starts with null or empty values
        assertNull(customerProfile.getFirstname());
        assertNull(customerProfile.getLastname());
        assertNull(customerProfile.getUsername());
        assertNull(customerProfile.getPhone_no());
        assertNull(customerProfile.getAddress());
        assertNull(customerProfile.getPayment_type());
    }

    @Test
    void testSetAndGetProfileDetails() {
        // Setting all fields and verifying using getters
        customerProfile.setFirstname("Alice");
        customerProfile.setLastname("Smith");
        customerProfile.setUsername("alice_smith");
        customerProfile.setPhone_no("9876543210");
        customerProfile.setAddress("5678 Oak Avenue");
        customerProfile.setPayment_type("Debit Card");

        assertEquals("Alice", customerProfile.getFirstname());
        assertEquals("Smith", customerProfile.getLastname());
        assertEquals("alice_smith", customerProfile.getUsername());
        assertEquals("9876543210", customerProfile.getPhone_no());
        assertEquals("5678 Oak Avenue", customerProfile.getAddress());
        assertEquals("Debit Card", customerProfile.getPayment_type());
    }
}
