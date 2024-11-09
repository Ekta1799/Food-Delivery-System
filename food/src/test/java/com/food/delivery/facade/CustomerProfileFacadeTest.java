package com.food.delivery.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.food.delivery.model.CustomerProfile;
import com.food.delivery.model.User;
import com.food.delivery.pojo.CustomerProfileResource;
import com.food.delivery.repository.CustomerProfileRepository;
import com.food.delivery.repository.UserRepository;
import com.food.delivery.services.CustomerProfileService;

class CustomerProfileFacadeTest {

    @InjectMocks
    private CustomerProfileFacade customerProfileFacade;

    @Mock
    private CustomerProfileService service;

    @Mock
    private CustomerProfileRepository customerProfileRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerProfile customerProfile;

    @Mock
    private CustomerProfileResource customerProfileResource;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCustomerProfile() {
        // Arrange
        CustomerProfileResource customerResource = new CustomerProfileResource();
        customerResource.setFirstname("John");
        customerResource.setLastname("Doe");
        customerResource.setUsername("johndoe");
        customerResource.setAddress("123 Street");
        customerResource.setPhone_no("1234567890");

        // Act
        customerProfileFacade.addCustomerProfile(customerResource);

        // Assert
        verify(service, times(1)).addCustomerProfile(any(CustomerProfile.class));
    }

    @Test
    void testGetCustomerProfile() {
        // Arrange
        String firstName = "John";
        when(service.getCustomerProfile(firstName)).thenReturn(customerProfile);
        when(customerProfile.getFirstname()).thenReturn("John");
        when(customerProfile.getLastname()).thenReturn("Doe");
        when(customerProfile.getUsername()).thenReturn("johndoe");
        when(customerProfile.getAddress()).thenReturn("123 Street");
        when(customerProfile.getPhone_no()).thenReturn("1234567890");
        when(customerProfile.getPayment_type()).thenReturn("Credit");

        // Act
        CustomerProfileResource result = customerProfileFacade.getCustomerProfile(firstName);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
    }

    @Test
    void testUpdateCustomerProfileWhenUserExists() {
        // Arrange
        CustomerProfileResource resource = new CustomerProfileResource();
        resource.setUsername("johndoe");
        resource.setFirstname("John");
        resource.setLastname("Doe");
        resource.setPhone_no("9876543210");
        resource.setAddress("456 Avenue");
        resource.setPayment_type("Debit");

        User user = new User();
        user.setId(1L);
        user.setUsername("johndoe");

        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));
        when(customerProfileRepo.existsByUserId(1L)).thenReturn(true);
        when(customerProfileRepo.findByUsername("johndoe")).thenReturn(Optional.of(customerProfile));

        // Act
        boolean result = customerProfileFacade.updateCustomerProfile(resource);

        // Assert
        assertTrue(result);
        verify(customerProfileRepo, times(1)).updateUserProfile(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testUpdateCustomerProfileWhenProfileDoesNotExist() {
        // Arrange
        CustomerProfileResource resource = new CustomerProfileResource();
        resource.setUsername("johndoe");
        resource.setFirstname("John");
        resource.setLastname("Doe");
        resource.setPhone_no("9876543210");
        resource.setAddress("456 Avenue");
        resource.setPayment_type("Debit");

        User user = new User();
        user.setId(1L);
        user.setUsername("johndoe");

        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));
        when(customerProfileRepo.existsByUserId(1L)).thenReturn(false);

        // Act
        boolean result = customerProfileFacade.updateCustomerProfile(resource);

        // Assert
        assertFalse(result);
    }

    @Test
    void testUpdateCustomerProfileWhenExceptionOccurs() {
        // Arrange
        CustomerProfileResource resource = new CustomerProfileResource();
        resource.setUsername("johndoe");
        resource.setFirstname("John");
        resource.setLastname("Doe");
        resource.setPhone_no("9876543210");
        resource.setAddress("456 Avenue");
        resource.setPayment_type("Debit");

        User user = new User();
        user.setId(1L);
        user.setUsername("johndoe");

        when(userRepository.findByUsername("johndoe")).thenReturn(Optional.of(user));
        when(customerProfileRepo.existsByUserId(1L)).thenReturn(true);
        when(customerProfileRepo.findByUsername("johndoe")).thenReturn(Optional.of(customerProfile));
        doThrow(new RuntimeException("Database error")).when(customerProfileRepo).updateUserProfile(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString());

        // Act
        boolean result = customerProfileFacade.updateCustomerProfile(resource);

        // Assert
        assertFalse(result);
    }
}
