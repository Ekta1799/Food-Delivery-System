package com.food.delivery.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerProfileTest {

    private CustomerProfile customerProfile;

    @BeforeEach
    void setUp() {
        customerProfile = new CustomerProfile();
    }

    @Test
    void testGetAndSetId() {
        customerProfile.setId(1L);
        assertEquals(1L, customerProfile.getId());
    }

    @Test
    void testGetAndSetFirstname() {
        customerProfile.setFirstname("Ankit");
        assertEquals("Ankit", customerProfile.getFirstname());
    }

    @Test
    void testGetAndSetLastname() {
        customerProfile.setLastname("Joshi");
        assertEquals("Joshi", customerProfile.getLastname());
    }

    @Test
    void testGetAndSetUsername() {
        customerProfile.setUsername("akd");
        assertEquals("akd", customerProfile.getUsername());
    }

    @Test
    void testGetAndSetPhoneNo() {
        customerProfile.setPhone_no("1234567890");
        assertEquals("1234567890", customerProfile.getPhone_no());
    }

    @Test
    void testGetAndSetAddress() {
        customerProfile.setAddress("123 Main Street");
        assertEquals("123 Main Street", customerProfile.getAddress());
    }

    @Test
    void testGetAndSetPaymentType() {
        customerProfile.setPayment_type("Credit Card");
        assertEquals("Credit Card", customerProfile.getPayment_type());
    }

    @Test
    void testGetAndSetUserId() {
        customerProfile.setUser_id(100L);
        assertEquals(100L, customerProfile.getUser_id());
    }
}
