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
        RestaurantProfileResource restaurantResource = new RestaurantProfileResource();
        restaurantResource.setFirstname("Stalin");
        restaurantResource.setLastname("Roy");
        restaurantResource.setUsername("stroy");
        restaurantResource.setAddress("123 Main St");
        restaurantResource.setPhone_no("1234567890");
        restaurantResource.setRestaurant_name("Foodies");
        restaurantResource.setHours_of_operation(8);

        doNothing().when(service).addRestaurantProfile(any(RestaurantOwnerProfile.class));

        facade.addRestaurantProfile(restaurantResource);
        verify(service, times(1)).addRestaurantProfile(any(RestaurantOwnerProfile.class));
    }

    @Test
    public void testGetRestaurantProfile() {
        String firstName = "Stalin";

        RestaurantOwnerProfile restaurantProfile = new RestaurantOwnerProfile();
        restaurantProfile.setFirstname(firstName);
        restaurantProfile.setLastname("Roy");
        restaurantProfile.setUsername("stroy");
        restaurantProfile.setRestaurant_name("Foodies");
        restaurantProfile.setPhone_no("1234567890");
        restaurantProfile.setAddress("123 Main St");
        restaurantProfile.setHours_of_operation(8);

        when(service.getRestaurantProfile(firstName)).thenReturn(restaurantProfile);

        RestaurantProfileResource result = facade.getRestaurantProfile(firstName);

        assertNotNull(result);
        assertEquals("Stalin", result.getFirstname());
        verify(service, times(1)).getRestaurantProfile(firstName);
    }

    @Test
    public void testUpdateRestaurantProfile() {
        RestaurantProfileResource restaurantProfileResource = new RestaurantProfileResource();
        restaurantProfileResource.setUsername("stroy");
        restaurantProfileResource.setFirstname("Stalin");
        restaurantProfileResource.setLastname("Roy");
        restaurantProfileResource.setPhone_no("1234567890");
        restaurantProfileResource.setAddress("123 Main St");
        restaurantProfileResource.setRestaurant_name("Foodies");
        restaurantProfileResource.setHours_of_operation(8);

        User user = new User();
        user.setId(1L);
        when(userRepository.findByUsername("stroy")).thenReturn(Optional.of(user));
        when(restaurantProfileRepo.existsByUserId(1L)).thenReturn(true);

        RestaurantOwnerProfile restaurantOwnerProfile = new RestaurantOwnerProfile();
        restaurantOwnerProfile.setUser_id(1L);
        when(restaurantProfileRepo.findByUsername("stroy")).thenReturn(Optional.of(restaurantOwnerProfile));

        boolean result = facade.updateRestaurantProfile(restaurantProfileResource);

        assertTrue(result);
        verify(restaurantProfileRepo, times(1)).updateRestaurantProfile(anyLong(), anyString(), anyString(), anyString(), anyString(), anyString(), anyInt());
    }

    @Test
    public void testGetRestaurantList() {
        String foodItem = "Pizza";
        Boolean isVeg = true;
        String cuisine = "Italian";

        List<String> restaurantNames = new ArrayList<>();
        restaurantNames.add("Foodies");
        when(restaurantProfileRepo.findAllRestaurants()).thenReturn(restaurantNames);

        Menu menu = new Menu();
        menu.setFood_item("Pizza");
        menu.setPrice(200);
        menu.setCuisine("Italian");
        menu.setVeg(true);
        when(menuService.getMenuByRestaurantId(anyLong(), eq(foodItem), eq(isVeg), eq(cuisine))).thenReturn(menu);

        List<MenuResource> result = facade.getRestaurantList(foodItem, isVeg, cuisine);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Foodies", result.get(0).getRestaurant_name());
        assertEquals("Pizza", result.get(0).getFood_item());
    }

    @Test
    public void testAddMenu() {
        List<MenuResource> menuResourceList = new ArrayList<>();
        MenuResource menuResource = new MenuResource();
        menuResource.setFood_item("Pizza");
        menuResource.setCuisine("Italian");
        menuResource.setPrice(200.23);
        menuResource.setVeg(true);
        menuResource.setRestaurant_name("Foodies");

        menuResourceList.add(menuResource);

        when(restaurantProfileRepo.getRestaurantIdByRestaurantName("Foodies")).thenReturn(1L);

        boolean result = facade.addMenu(menuResourceList);

        assertTrue(result);
        verify(menuService, times(1)).addMenu(any(Menu.class));
    }

}
