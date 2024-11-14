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

import com.food.delivery.model.Menu;
import com.food.delivery.repository.MenuRepository;

class MenuServiceImplTest {

    @Mock
    private MenuRepository menuRepo;

    @InjectMocks
    private MenuServiceImpl menuService;

    private Menu menu;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        menu = new Menu();
        menu.setFood_item("Pizza");
        menu.setPrice(12.99);
    }

    @Test
    void testGetMenuByRestaurantId_withFilters() {

        Long restaurantId = 1L;
        String foodItem = "Pizza";
        Boolean veg = true;
        String cuisine = "Italian";
        
        when(menuRepo.searchMenu(restaurantId, foodItem, veg, cuisine)).thenReturn(menu);

        Menu result = menuService.getMenuByRestaurantId(restaurantId, foodItem, veg, cuisine);

        assertNotNull(result);
        assertEquals("Pizza", result.getFood_item());
        verify(menuRepo, times(1)).searchMenu(restaurantId, foodItem, veg, cuisine);
    }

    @Test
    void testGetMenuByRestaurantId_withoutFilters() {

        Long restaurantId = 1L;
        String foodItem = null;
        Boolean veg = null;
        String cuisine = null;

        when(menuRepo.getMenuByRestaurantId(restaurantId)).thenReturn(menu);

        Menu result = menuService.getMenuByRestaurantId(restaurantId, foodItem, veg, cuisine);

        assertNotNull(result);
        assertEquals("Pizza", result.getFood_item());
        verify(menuRepo, times(1)).getMenuByRestaurantId(restaurantId);
    }

    @Test
    void testAddMenu() {
        menuService.addMenu(menu);

        verify(menuRepo, times(1)).save(menu);
    }
}
