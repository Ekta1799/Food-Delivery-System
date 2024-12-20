package com.food.delivery.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.delivery.jwt.JwtUtils;
import com.food.delivery.model.CustomerProfile;
import com.food.delivery.model.DeliveryPersonnelProfile;
import com.food.delivery.model.ERole;
import com.food.delivery.model.RestaurantOwnerProfile;
import com.food.delivery.model.Role;
import com.food.delivery.model.User;
import com.food.delivery.pojo.JWTResponse;
import com.food.delivery.pojo.LoginRequest;
import com.food.delivery.pojo.MessageResponse;
import com.food.delivery.pojo.SignUpRequest;
import com.food.delivery.repository.CustomerProfileRepository;
import com.food.delivery.repository.DeliveryPersonnelProfileRepository;
import com.food.delivery.repository.RestaurantProfileRepository;
import com.food.delivery.repository.RoleRepository;
import com.food.delivery.repository.UserRepository;
import com.food.delivery.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/food")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	CustomerProfileRepository customerProfileRepo;

	@Autowired
	RestaurantProfileRepository restaurantProfileRepo;

	@Autowired
	DeliveryPersonnelProfileRepository deliveryPersonnelProfileRepository;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		String strRole = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		logger.debug("rolessss++++++++++++++++++++ : " + strRole);

		if (strRole == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);

			user.setRoles(roles);
			userRepository.save(user);

			CustomerProfile customerProfile = new CustomerProfile();
			customerProfile.setUser_id(user.getId());
			customerProfile.setUsername(user.getUsername());
			customerProfileRepo.save(customerProfile);
		} else {
			switch (strRole) {
			case "ROLE_RESTAURANT_OWNER":
				Role restaurantRole = roleRepository.findByName(ERole.ROLE_RESTAURANT_OWNER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(restaurantRole);

				user.setRoles(roles);
				userRepository.save(user);

				RestaurantOwnerProfile restaurantOwnerProfile = new RestaurantOwnerProfile();
				restaurantOwnerProfile.setUser_id(user.getId());
				restaurantOwnerProfile.setUsername(user.getUsername());
				restaurantProfileRepo.save(restaurantOwnerProfile);

				break;
			case "ROLE_DELIVERY_PERSONNEL":
				Role deliveryRole = roleRepository.findByName(ERole.ROLE_DELIVERY_PERSONNEL)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(deliveryRole);

				user.setRoles(roles);
				userRepository.save(user);

				DeliveryPersonnelProfile deliveryPersonnelProfile = new DeliveryPersonnelProfile();
				deliveryPersonnelProfile.setUser_id(user.getId());
				deliveryPersonnelProfile.setUsername(user.getUsername());
				deliveryPersonnelProfileRepository.save(deliveryPersonnelProfile);

				break;
			case "ROLE_ADMIN":
				Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);

				user.setRoles(roles);
				userRepository.save(user);

				break;
			default:
				Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
						.orElseThrow(() -> new RuntimeException("Error: custmer 2 Role is not found."));
				roles.add(userRole);

				user.setRoles(roles);
				userRepository.save(user);

				CustomerProfile customerProfile = new CustomerProfile();
				customerProfile.setUser_id(user.getId());
				customerProfile.setUsername(user.getUsername());
				customerProfileRepo.save(customerProfile);
			}
		}

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

		if (loginRequest == null || loginRequest.getUsername() == null || loginRequest.getPassword() == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Empty credentials!"));
		}

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JWTResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

}
