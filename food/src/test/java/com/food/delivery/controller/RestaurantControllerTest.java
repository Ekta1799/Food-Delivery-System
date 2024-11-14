package com.food.delivery.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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

import com.food.delivery.facade.RestaurantProfileFacade;
import com.food.delivery.pojo.MenuResource;
import com.food.delivery.pojo.RestaurantProfileResource;

@ExtendWith(SpringExtension.class)
public class RestaurantControllerTest {

	private MockMvc mockMvc;

	@Mock
	private RestaurantProfileFacade restaurantProfileFacade;

	@InjectMocks
	private RestaurantOwnerProfileController restaurantOwnerProfileController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(restaurantOwnerProfileController).build();
	}

	@Test
	void testAddRestaurantOwnerProfile_Success() throws Exception {
		RestaurantProfileResource restaurantResource = new RestaurantProfileResource();
		restaurantResource.setFirstname("Aman");

		mockMvc.perform(MockMvcRequestBuilders.post("/restaurantProfile").contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstname\":\"Aman\"}")).andExpect(status().isOk());
	}

	@Test
	void testGetRestaurantProfiles_BadRequest() throws Exception {
		RestaurantProfileResource restaurantResource = new RestaurantProfileResource();
		restaurantResource.setFirstname("Aman");

		when(restaurantProfileFacade.getRestaurantProfile("Aman")).thenReturn(restaurantResource);

		mockMvc.perform(MockMvcRequestBuilders.get("/restaurantProfile").param("firstname", "Aman")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}

	@Test
	void testUpdateRestaurantProfile_BadRequest() throws Exception {
		when(restaurantProfileFacade.updateRestaurantProfile(any(RestaurantProfileResource.class))).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.put("/restaurantProfile").contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstname\":\"Aman\"}")).andExpect(status().is4xxClientError());
	}

	@Test
	void testUpdateRestaurantProfile_Failure() throws Exception {
		when(restaurantProfileFacade.updateRestaurantProfile(any(RestaurantProfileResource.class))).thenReturn(false);

		mockMvc.perform(MockMvcRequestBuilders.put("/restaurantProfile").contentType(MediaType.APPLICATION_JSON)
				.content("{\"firstname\":\"Aman\"}")).andExpect(status().isBadRequest());
	}

	@Test
	void testAddMenuByRestaurantName_NotFound() throws Exception {
		List<MenuResource> menuList = new ArrayList<>();
		MenuResource menuResource = new MenuResource();
		menuResource.setFood_item("Pizza");
		menuResource.setPrice(12.99);
		menuList.add(menuResource);

		when(restaurantProfileFacade.addMenu(menuList)).thenReturn(false);

		mockMvc.perform(MockMvcRequestBuilders.post("/menu").contentType(MediaType.APPLICATION_JSON)
				.content("[{\"foodItem\":\"Pizza\",\"price\":12.99}]")).andExpect(status().is4xxClientError());
	}

	@Test
	void testAddMenuByRestaurantName_Success() throws Exception {
		when(restaurantProfileFacade.addMenu(any(List.class))).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.post("/menu").contentType(MediaType.APPLICATION_JSON)
				.content("[{\"foodItem\":\"Pizza\",\"price\":12.99}]")).andExpect(status().is2xxSuccessful());
	}

	@Test
	void testAddMenuByRestaurantName_EmptyFields() throws Exception {
		when(restaurantProfileFacade.addMenu(any(List.class))).thenThrow(new NullPointerException());

		mockMvc.perform(MockMvcRequestBuilders.post("/menu").contentType(MediaType.APPLICATION_JSON)
				.content("[{\"foodItem\":\"\"}]")).andExpect(status().isBadRequest());
	}
}
