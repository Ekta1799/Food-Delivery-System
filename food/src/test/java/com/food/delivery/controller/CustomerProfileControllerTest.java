package com.food.delivery.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.food.delivery.facade.CustomerProfileFacade;
import com.food.delivery.facade.RestaurantProfileFacade;
import com.food.delivery.pojo.CustomerProfileResource;
import com.food.delivery.pojo.MenuResource;

@ExtendWith(SpringExtension.class)
public class CustomerProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CustomerProfileFacade customerProfileFacade;

    @Mock
    private RestaurantProfileFacade restaurantProfileFacade;

    @InjectMocks
    private CustomerProfileController customerProfileController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(customerProfileController).build();
    }

    @Test
    void testAddCustomerProfile_Success() throws Exception {
        CustomerProfileResource customerProfileResource = new CustomerProfileResource();
        customerProfileResource.setFirstname("Abhi");
        customerProfileResource.setLastname("Verma");

//        when(customerProfileFacade.addCustomerProfile(any(CustomerProfileResource.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/customerProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Abhi\",\"lastName\":\"Verma\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCustomerProfiles_Success() throws Exception {
        CustomerProfileResource customerProfile = new CustomerProfileResource();
        customerProfile.setFirstname("Abhi");
        customerProfile.setLastname("Verma");
        customerProfile.setAddress("Pune");
        customerProfile.setPayment_type("credit");
        customerProfile.setUsername("abhiv");
        customerProfile.setPhone_no("1234567890");

        when(customerProfileFacade.getCustomerProfile("Abhi")).thenReturn(customerProfile);

        mockMvc.perform(MockMvcRequestBuilders.get("/customerProfile/John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCustomerProfiles_UserNotFound() throws Exception {
        when(customerProfileFacade.getCustomerProfile("Abhi")).thenThrow(new RuntimeException("User profile not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/customerProfile")
                        .param("firstname", "Abhi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testUpdateCustomerProfile_BadRequest() throws Exception {
        CustomerProfileResource customerProfileResource = new CustomerProfileResource();
        customerProfileResource.setFirstname("Abhi");
        customerProfileResource.setLastname("Verma");

        when(customerProfileFacade.updateCustomerProfile(any(CustomerProfileResource.class))).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.put("/customerProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Abhi\",\"lastName\":\"Verma\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testUpdateCustomerProfile_Failure() throws Exception {
        CustomerProfileResource customerProfileResource = new CustomerProfileResource();
        customerProfileResource.setFirstname("Abhi");
        customerProfileResource.setLastname("Verma");

        when(customerProfileFacade.updateCustomerProfile(any(CustomerProfileResource.class))).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/customerProfile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Abhi\",\"lastName\":\"Verma\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetRestaurantList_Success() throws Exception {
        List<MenuResource> restaurants = new ArrayList<>();
        MenuResource menuResource = new MenuResource();
        menuResource.setRestaurant_name("Restaurant1");
        restaurants.add(menuResource);

        when(restaurantProfileFacade.getRestaurantList("Pizza", true, "Italian")).thenReturn(restaurants);

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants")
                        .param("food_item", "Pizza")
                        .param("veg", "true")
                        .param("cuisine", "Italian")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void testGetRestaurantList_Failure() throws Exception {
        when(restaurantProfileFacade.getRestaurantList("Pizza", true, "Italian")).thenThrow(new RuntimeException("Error retrieving restaurants"));

        mockMvc.perform(MockMvcRequestBuilders.get("/restaurants")
                        .param("food_item", "Pizza")
                        .param("veg", "true")
                        .param("cuisine", "Italian")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
