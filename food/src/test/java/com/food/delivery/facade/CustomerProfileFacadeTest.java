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
        CustomerProfileResource customerResource = new CustomerProfileResource();
        customerResource.setFirstname("Vicky");
        customerResource.setLastname("Verma");
        customerResource.setUsername("user");
        customerResource.setAddress("123 Street");
        customerResource.setPhone_no("1234567890");

        customerProfileFacade.addCustomerProfile(customerResource);

        verify(service, times(1)).addCustomerProfile(any(CustomerProfile.class));
    }

    @Test
    void testGetCustomerProfile() {
        String firstName = "Vicky";
        when(service.getCustomerProfile(firstName)).thenReturn(customerProfile);
        when(customerProfile.getFirstname()).thenReturn("Vicky");
        when(customerProfile.getLastname()).thenReturn("Verma");
        when(customerProfile.getUsername()).thenReturn("user");
        when(customerProfile.getAddress()).thenReturn("123 Street");
        when(customerProfile.getPhone_no()).thenReturn("1234567890");
        when(customerProfile.getPayment_type()).thenReturn("Credit");

        CustomerProfileResource result = customerProfileFacade.getCustomerProfile(firstName);

        assertNotNull(result);
        assertEquals("Vicky", result.getFirstname());
        assertEquals("Verma", result.getLastname());
    }

    @Test
    void testUpdateCustomerProfileWhenUserExists() {

        CustomerProfileResource resource = new CustomerProfileResource();
        resource.setUsername("user");
        resource.setFirstname("Vicky");
        resource.setLastname("Verma");
        resource.setPhone_no("9876543210");
        resource.setAddress("456 Avenue");
        resource.setPayment_type("Debit");

        User user = new User();
        user.setId(1L);
        user.setUsername("user");

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(customerProfileRepo.existsByUserId(1L)).thenReturn(true);
        when(customerProfileRepo.findByUsername("user")).thenReturn(Optional.of(customerProfile));

        boolean result = customerProfileFacade.updateCustomerProfile(resource);

        assertTrue(result);
        verify(customerProfileRepo, times(1)).updateUserProfile(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString());
    }

    @Test
    void testUpdateCustomerProfileWhenProfileDoesNotExist() {

        CustomerProfileResource resource = new CustomerProfileResource();
        resource.setUsername("user");
        resource.setFirstname("Vicky");
        resource.setLastname("Verma");
        resource.setPhone_no("9876543210");
        resource.setAddress("456 Avenue");
        resource.setPayment_type("Debit");

        User user = new User();
        user.setId(1L);
        user.setUsername("user");

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(customerProfileRepo.existsByUserId(1L)).thenReturn(false);

        boolean result = customerProfileFacade.updateCustomerProfile(resource);

        assertFalse(result);
    }

    @Test
    void testUpdateCustomerProfileWhenExceptionOccurs() {

        CustomerProfileResource resource = new CustomerProfileResource();
        resource.setUsername("user");
        resource.setFirstname("Vicky");
        resource.setLastname("Verma");
        resource.setPhone_no("9876543210");
        resource.setAddress("456 Avenue");
        resource.setPayment_type("Debit");

        User user = new User();
        user.setId(1L);
        user.setUsername("user");

        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(customerProfileRepo.existsByUserId(1L)).thenReturn(true);
        when(customerProfileRepo.findByUsername("user")).thenReturn(Optional.of(customerProfile));
        doThrow(new RuntimeException("Database error")).when(customerProfileRepo).updateUserProfile(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString());

        boolean result = customerProfileFacade.updateCustomerProfile(resource);

        assertFalse(result);
    }
}
