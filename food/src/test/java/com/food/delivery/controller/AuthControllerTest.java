package com.food.delivery.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.food.delivery.exception.GeneralExceptionHandler;
import com.food.delivery.jwt.JwtUtils;
import com.food.delivery.model.ERole;
import com.food.delivery.model.Role;
import com.food.delivery.pojo.SignUpRequest;
import com.food.delivery.repository.CustomerProfileRepository;
import com.food.delivery.repository.RoleRepository;
import com.food.delivery.repository.UserRepository;

@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

	private MockMvc mockMvc;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private CustomerProfileRepository customerProfileRepository;

	@Mock
	private PasswordEncoder encoder;

	@Mock
	private JwtUtils jwtUtils;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private GeneralExceptionHandler handler;

	@InjectMocks
	private AuthController authController;

	@Mock
	private RestTemplate template;

	@Mock
	private RestTemplateBuilder restTemplateBuilder;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		this.mockMvc = MockMvcBuilders.standaloneSetup(authController).setControllerAdvice(handler).build();
		when(restTemplateBuilder.build()).thenReturn(template);

	}

	@Test
	void testRegisterUser_NotFound() throws Exception {
		SignUpRequest signUpRequest = new SignUpRequest();

		when(userRepository.existsByUsername("testuser")).thenReturn(false);
		when(userRepository.existsByEmail("testuser@example.com")).thenReturn(false);

		Role role = new Role(ERole.ROLE_CUSTOMER);
		when(roleRepository.findByName(ERole.ROLE_CUSTOMER)).thenReturn(Optional.of(role));
		when(encoder.encode(any(String.class))).thenReturn("encoded_password");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).content(
				"{\"username\":\"testuser\",\"email\":\"testuser@example.com\",\"password\":\"password123\",\"role\":\"ROLE_CUSTOMER\"}"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testRegisterUser_UsernameExists() throws Exception {
		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setUsername("testuser");
		signUpRequest.setEmail("testuser@example.com");
		signUpRequest.setPassword("password123");

		when(userRepository.existsByUsername("testuser")).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"testuser\",\"email\":\"testuser@example.com\",\"password\":\"password123\"}"))
				.andExpect(status().is4xxClientError());
	}

	@Test
	void testRegisterUser_EmailExists() throws Exception {
		SignUpRequest signUpRequest = new SignUpRequest();
		signUpRequest.setUsername("testuser");
		signUpRequest.setEmail("testuser@example.com");
		signUpRequest.setPassword("password123");

		when(userRepository.existsByUsername("testuser")).thenReturn(false);
		when(userRepository.existsByEmail("testuser@example.com")).thenReturn(true);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\":\"testuser\",\"email\":\"testuser@example.com\",\"password\":\"password123\"}"))
				.andExpect(status().is4xxClientError());
	}

}
