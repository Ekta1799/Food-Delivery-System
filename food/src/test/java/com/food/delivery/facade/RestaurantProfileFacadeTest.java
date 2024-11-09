package com.food.delivery.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.food.delivery.model.Menu;
import com.food.delivery.model.RestaurantOwnerProfile;
import com.food.delivery.model.User;
import com.food.delivery.pojo.MenuResource;
import com.food.delivery.pojo.RestaurantProfileResource;
import com.food.delivery.repository.RestaurantProfileRepository;
import com.food.delivery.repository.UserRepository;
import com.food.delivery.services.MenuService;
import com.food.delivery.services.RestaurantProfileService;

@ExtendWith(MockitoExtension.class)
public class RestaurantProfileFacadeTest {

    @InjectMocks
    private RestaurantProfileFacade facade;

    @Mock
    private RestaurantProfileService service;

    @Mock
    private RestaurantProfileRepository restaurantProfileRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MenuService menuService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRestaurantProfile() {
        // Create test data
        RestaurantProfileResource restaurantResource = new RestaurantProfileResource();
        restaurantResource.setFirstname("John");
        restaurantResource.setLastname("Doe");
        restaurantResource.setUsername("john_doe");
        restaurantResource.setAddress("123 Main St");
        restaurantResource.setPhone_no("1234567890");
        restaurantResource.setRestaurant_name("Foodies");
        restaurantResource.setHours_of_operation(8);

        // Mocking service call
        doNothing().when(service).addRestaurantProfile(any(RestaurantOwnerProfile.class));

        // Call the method under test
        facade.addRestaurantProfile(restaurantResource);

        // Verify that the service method was called
        verify(service, times(1)).addRestaurantProfile(any(RestaurantOwnerProfile.class));
    }

    @Test
    public void testGetRestaurantProfile() {
        String firstName = "John";

        // Create test data
        RestaurantOwnerProfile restaurantProfile = new RestaurantOwnerProfile();
        restaurantProfile.setFirstname(firstName);
        restaurantProfile.setLastname("Doe");
        restaurantProfile.setUsername("john_doe");
        restaurantProfile.setRestaurant_name("Foodies");
        restaurantProfile.setPhone_no("1234567890");
        restaurantProfile.setAddress("123 Main St");
        restaurantProfile.setHours_of_operation(8);

        // Mocking service call
        when(service.getRestaurantProfile(firstName)).thenReturn(restaurantProfile);

        // Call the method under test
        RestaurantProfileResource result = facade.getRestaurantProfile(firstName);

        // Verify results
        assertNotNull(result);
        assertEquals("John", result.getFirstname());
        verify(service, times(1)).getRestaurantProfile(firstName);
    }

    @Test
    public void testUpdateRestaurantProfile() {
        // Create test data
        RestaurantProfileResource restaurantProfileResource = new RestaurantProfileResource();
        restaurantProfileResource.setUsername("john_doe");
        restaurantProfileResource.setFirstname("John");
        restaurantProfileResource.setLastname("Doe");
        restaurantProfileResource.setPhone_no("1234567890");
        restaurantProfileResource.setAddress("123 Main St");
        restaurantProfileResource.setRestaurant_name("Foodies");
        restaurantProfileResource.setHours_of_operation(8);

        User user = new User();
        user.setId(1L);
        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));
        when(restaurantProfileRepo.existsByUserId(1L)).thenReturn(true);

        RestaurantOwnerProfile restaurantOwnerProfile = new RestaurantOwnerProfile();
        restaurantOwnerProfile.setUser_id(1L);
        when(restaurantProfileRepo.findByUsername("john_doe")).thenReturn(Optional.of(restaurantOwnerProfile));

        // Call the method under test
        boolean result = facade.updateRestaurantProfile(restaurantProfileResource);

        // Verify results
        assertTrue(result);
        verify(restaurantProfileRepo, times(1)).updateRestaurantProfile(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    public void testGetRestaurantList() {
        String foodItem = "Pizza";
        Boolean isVeg = true;
        String cuisine = "Italian";

        // Mock restaurant names list
        List<String> restaurantNames = new ArrayList<>();
        restaurantNames.add("Foodies");
        when(restaurantProfileRepo.findAllRestaurants()).thenReturn(restaurantNames);

        // Create test menu
        Menu menu = new Menu();
        menu.setFood_item("Pizza");
        menu.setPrice(200);
        menu.setCuisine("Italian");
        menu.setVeg(true);
        when(menuService.getMenuByRestaurantId(anyLong(), eq(foodItem), eq(isVeg), eq(cuisine))).thenReturn(menu);

        // Call the method under test
        List<MenuResource> result = facade.getRestaurantList(foodItem, isVeg, cuisine);

        // Verify results
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Foodies", result.get(0).getRestaurant_name());
        assertEquals("Pizza", result.get(0).getFood_item());
    }

    @Test
    public void testAddMenu() {
        // Create test data
        List<MenuResource> menuResourceList = new ArrayList<>();
        MenuResource menuResource = new MenuResource();
        menuResource.setFood_item("Pizza");
        menuResource.setCuisine("Italian");
        menuResource.setPrice(200);
        menuResource.setVeg(true);
        menuResource.setRestaurant_name("Foodies");

        menuResourceList.add(menuResource);

        // Mock restaurant ID lookup
        when(restaurantProfileRepo.getRestaurantIdByRestaurantName("Foodies")).thenReturn(1L);

        // Call the method under test
        boolean result = facade.addMenu(menuResourceList);

        // Verify results
        assertTrue(result);
        verify(menuService, times(1)).addMenu(any(Menu.class));
    }

}
