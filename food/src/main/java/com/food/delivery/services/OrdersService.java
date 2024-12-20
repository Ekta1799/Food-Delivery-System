package com.food.delivery.services;

import java.util.List;

import com.food.delivery.model.Orders;

public interface OrdersService {

	public void addOrders(Orders orders);

	public void updateStatus(Long customer_id, Long restaurant_id, Long food_id, String status);

	public List<Orders> getOrdersByRestaurantOwner(Long restaurant_id);
	
	public List<Orders> getOrderStatusByCustomer(Long customer_id, String status);
	
	public List<Orders> getDeliveryOrders();
}
