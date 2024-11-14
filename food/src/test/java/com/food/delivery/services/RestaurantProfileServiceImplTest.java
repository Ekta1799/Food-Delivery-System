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

import com.food.delivery.model.RestaurantOwnerProfile;
import com.food.delivery.repository.MenuRepository;
import com.food.delivery.repository.RestaurantProfileRepository;

class RestaurantProfileServiceImplTest {

    @Mock
    private RestaurantProfileRepository restaurantProfileRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private RestaurantProfileServiceImpl restaurantProfileService;

    private RestaurantOwnerProfile restaurantProfile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantProfile = new RestaurantOwnerProfile();
        restaurantProfile.setFirstname("Reyan");
        restaurantProfile.setId(1L);
        restaurantProfile.setRestaurant_name("Good Food");
    }

    @Test
    void testAddRestaurantProfile() {
        restaurantProfileService.addRestaurantProfile(restaurantProfile);

        // Assert
        verify(restaurantProfileRepository, times(1)).save(restaurantProfile);
    }

    @Test
    void testGetRestaurantProfile() {

        String firstName = "Reyan";
        when(restaurantProfileRepository.getRestaurantProfile(firstName)).thenReturn(restaurantProfile);

        RestaurantOwnerProfile result = restaurantProfileService.getRestaurantProfile(firstName);

        // Assert
        assertNotNull(result);
        assertEquals("Reyan", result.getFirstname());
        verify(restaurantProfileRepository, times(1)).getRestaurantProfile(firstName);
    }
}
