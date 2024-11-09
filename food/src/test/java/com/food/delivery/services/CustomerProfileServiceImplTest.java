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
        customerProfile.setFirstname("John");
        customerProfile.setLastname("Doe");
        customerProfile.setUsername("john_doe");
        customerProfile.setPhone_no("1234567890");
        customerProfile.setAddress("123 Main St");
        customerProfile.setPayment_type("Credit");
    }

    @Test
    void testAddCustomerProfile() {
        customerProfileService.addCustomerProfile(customerProfile);

        // Assert
        // Verify that the save method is called once with the correct argument
        verify(customerProfileRepo, times(1)).save(customerProfile);
    }


    @Test
    void testGetCustomerProfile() {
        // Arrange
        when(customerProfileRepo.getCustomerProfile("John")).thenReturn(customerProfile);

        // Act
        CustomerProfile result = customerProfileService.getCustomerProfile("John");

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
    }
}
