package com.food.delivery.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.delivery.model.Orders;
import com.food.delivery.repository.OrdersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

	@Autowired
	OrdersRepository ordersRepo;

	public void addOrders(Orders orders) {

		ordersRepo.save(orders);
	}

	public void updateStatus(Long customer_id, Long restaurant_id, Long food_id, String status) {
		ordersRepo.updateStatus(customer_id, restaurant_id, food_id, status);
	}

	public List<Orders> getOrdersByRestaurantOwner(Long restaurant_id) {

		List<Orders> orders = ordersRepo.getOrdersByRestaurantId(restaurant_id);

		return orders;
	}

	public List<Orders> getOrderStatusByCustomer(Long customer_id, String status) {

		List<Orders> orders = new ArrayList<Orders>();
		if (status != null) {
			// Fetch orders based on status for customers
			orders = ordersRepo.getOrderByCustomerBasedOnStatus(customer_id, status);
		} else {
			// Fetch all orders by customer
			orders = ordersRepo.getOrderByCustomer(customer_id);
		}

		return orders;
	}
	
	public List<Orders> getDeliveryOrders() {
		
		List<Orders> orders = ordersRepo.getDeliveryOrder();

		return orders;
	}
}
