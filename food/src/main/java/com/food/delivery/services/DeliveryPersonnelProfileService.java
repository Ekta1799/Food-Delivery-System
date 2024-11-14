package com.food.delivery.services;

import com.food.delivery.model.Delivery;
import com.food.delivery.model.DeliveryPersonnelProfile;

public interface DeliveryPersonnelProfileService {

	public void addDeliveryPersonnelProfile(DeliveryPersonnelProfile deliveryPersonnel);

	public DeliveryPersonnelProfile getDeliveryPersonnelProfile(String firstname);

	public void addDeliveryDetails(Delivery delivery);
}
