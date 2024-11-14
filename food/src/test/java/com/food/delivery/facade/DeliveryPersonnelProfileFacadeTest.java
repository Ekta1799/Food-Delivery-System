package com.food.delivery.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.food.delivery.model.Delivery;
import com.food.delivery.model.DeliveryPersonnelProfile;
import com.food.delivery.model.Orders;
import com.food.delivery.model.User;
import com.food.delivery.pojo.DeliveryPersonnelProfileResource;
import com.food.delivery.pojo.DeliveryResource;
import com.food.delivery.pojo.OrdersResource;
import com.food.delivery.repository.DeliveryPersonnelProfileRepository;
import com.food.delivery.repository.DeliveryRepository;
import com.food.delivery.repository.MenuRepository;
import com.food.delivery.repository.OrdersRepository;
import com.food.delivery.repository.RestaurantProfileRepository;
import com.food.delivery.repository.UserRepository;
import com.food.delivery.services.DeliveryPersonnelProfileService;
import com.food.delivery.services.OrdersService;

public class DeliveryPersonnelProfileFacadeTest {

    @Mock
    private DeliveryPersonnelProfileService service;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DeliveryPersonnelProfileRepository repo;

    @Mock
    private RestaurantProfileRepository restaurantProfileRepo;

    @Mock
    private MenuRepository menuRepo;

    @Mock
    private OrdersService ordersService;

    @Mock
    private DeliveryRepository deliveryRepo;

    @Mock
    private OrdersRepository ordersRepo;

    @InjectMocks
    private DeliveryPersonnelProfileFacade facade;

    private DeliveryPersonnelProfileResource profileResource;
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        profileResource = new DeliveryPersonnelProfileResource();
        profileResource.setFirstname("Vicky");
        profileResource.setLastname("Verma");
        profileResource.setUsername("testUser");
        profileResource.setVehicle_type("Bike");
        profileResource.setPhone_no("1234567890");
        profileResource.setAvailablity(true);
    }

    @Test
    public void testAddDeliveryPersonnelProfile() {
        facade.addDeliveryPersonnelProfile(profileResource);

        verify(service, times(1)).addDeliveryPersonnelProfile(any(DeliveryPersonnelProfile.class));
    }

    @Test
    public void testGetDeliveryPersonnelProfile() {
        DeliveryPersonnelProfile profile = new DeliveryPersonnelProfile();
        profile.setFirstname("Vicky");
        profile.setLastname("Verma");
        profile.setUsername("testUser");

        when(service.getDeliveryPersonnelProfile("Vicky")).thenReturn(profile);

        DeliveryPersonnelProfileResource result = facade.getDeliveryPersonnelProfile("Vicky");

        assertNotNull(result);
        assertEquals("Vicky", result.getFirstname());
        assertEquals("Verma", result.getLastname());
    }

    @Test
    public void testUpdateDeliveryPersonnelProfile() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(repo.existsByUserId(1L)).thenReturn(true);
        when(repo.findByUsername("testUser")).thenReturn(Optional.of(new DeliveryPersonnelProfile()));

        boolean result = facade.updateDeliveryPersonnelProfile(profileResource);

        assertTrue(result);
    }

    @Test
    public void testGetDeliveryOrders() {
        Orders order = new Orders();
        order.setId(1L);
        order.setFood_price(100.0);
        order.setStatus("Pending");

        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(order);

        when(ordersService.getDeliveryOrders()).thenReturn(ordersList);
        when(userRepository.userByUserId(1L)).thenReturn("testUser");
        when(menuRepo.getFoodNameFromFoodId(1L)).thenReturn("Pizza");
        when(restaurantProfileRepo.getRestaurantNameByRestaurantId(1L)).thenReturn("Test Restaurant");

        List<OrdersResource> result = facade.getDeliveryOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testUser", result.get(0).getUsername());
    }

    @Test
    public void testAddDeliveryDetails() {
        DeliveryResource deliveryResource = new DeliveryResource();
        deliveryResource.setUsername("testUser");
        deliveryResource.setOrderId(1L);
        deliveryResource.setDelivery_status("Pending");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(ordersRepo.existsById(1L)).thenReturn(true);

        facade.addDeliveryDetails(deliveryResource);

        verify(service, times(1)).addDeliveryDetails(any(Delivery.class));
    }

    @Test
    public void testUpdateDeliveryStatus() {
        facade.updateDeliveryStatus("Delivered", 1L);

        verify(deliveryRepo, times(1)).updateDeliveryStatus(eq("Delivered"), eq(1L));
    }
}
