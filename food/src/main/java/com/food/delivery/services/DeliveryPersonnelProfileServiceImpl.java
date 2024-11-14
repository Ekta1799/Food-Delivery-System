package com.food.delivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.delivery.model.Delivery;
import com.food.delivery.model.DeliveryPersonnelProfile;
import com.food.delivery.repository.DeliveryPersonnelProfileRepository;
import com.food.delivery.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryPersonnelProfileServiceImpl implements DeliveryPersonnelProfileService {

	@Autowired
	DeliveryPersonnelProfileRepository repo;
	
	@Autowired
	DeliveryRepository deliveryRepo;

	public void addDeliveryPersonnelProfile(DeliveryPersonnelProfile deliveryPersonnel) {
		repo.save(deliveryPersonnel);
	}
	
	public DeliveryPersonnelProfile getDeliveryPersonnelProfile(String firstname) {
		DeliveryPersonnelProfile deliveryPersonnel = new DeliveryPersonnelProfile();

		deliveryPersonnel = repo.getDeliveryPersonnelProfile(firstname);

		return deliveryPersonnel;
	}
	
	public void addDeliveryDetails(Delivery delivery) {
		deliveryRepo.save(delivery);
	}

}
