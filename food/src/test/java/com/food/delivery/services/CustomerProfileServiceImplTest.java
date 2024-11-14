package com.food.delivery.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.food.delivery.model.CustomerProfile;
import com.food.delivery.repository.CustomerProfileRepository;

class CustomerProfileServiceImplTest {

    @Mock
    private CustomerProfileRepository customerProfileRepo;

    @InjectMocks
    private CustomerProfileServiceImpl customerProfileService;

    private CustomerProfile customerProfile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerProfile = new CustomerProfile();
        customerProfile.setFirstname("Sharon");
        customerProfile.setLastname("Roy");
        customerProfile.setUsername("Sroy");
        customerProfile.setPhone_no("1234567890");
        customerProfile.setAddress("123 Main St");
        customerProfile.setPayment_type("Credit");
    }

    @Test
    void testAddCustomerProfile() {
        customerProfileService.addCustomerProfile(customerProfile);

        verify(customerProfileRepo, times(1)).save(customerProfile);
    }


    @Test
    void testGetCustomerProfile() {
        when(customerProfileRepo.getCustomerProfile("Sharon")).thenReturn(customerProfile);

        // Act
        CustomerProfile result = customerProfileService.getCustomerProfile("Sharon");

        // Assert
        assertNotNull(result);
        assertEquals("Sharon", result.getFirstname());
        assertEquals("Roy", result.getLastname());
    }
}
