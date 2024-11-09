package com.food.delivery.services;

import static org.mockito.Mockito.*;

import com.food.delivery.model.RestaurantOwnerProfile;
import com.food.delivery.repository.RestaurantProfileRepository;
import com.food.delivery.repository.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;

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
        restaurantProfile.setFirstname("John");
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

        String firstName = "John";
        when(restaurantProfileRepository.getRestaurantProfile(firstName)).thenReturn(restaurantProfile);

        RestaurantOwnerProfile result = restaurantProfileService.getRestaurantProfile(firstName);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        verify(restaurantProfileRepository, times(1)).getRestaurantProfile(firstName);
    }
}
