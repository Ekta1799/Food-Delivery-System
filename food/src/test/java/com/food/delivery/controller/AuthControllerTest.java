package com.food.delivery.controller;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    void testRegisterUser_SuccessfulRegistration() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("testuser");
        signUpRequest.setEmail("testuser@example.com");
        signUpRequest.setPassword("password123");
        signUpRequest.setRole("ROLE_CUSTOMER");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("testuser@example.com")).thenReturn(false);

        Role role = new Role(ERole.ROLE_CUSTOMER);
        when(roleRepository.findByName(ERole.ROLE_CUSTOMER)).thenReturn(Optional.of(role));
        when(encoder.encode(any(String.class))).thenReturn("encoded_password");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"email\":\"testuser@example.com\",\"password\":\"password123\",\"role\":\"ROLE_CUSTOMER\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    void testRegisterUser_UsernameExists() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("testuser");
        signUpRequest.setEmail("testuser@example.com");
        signUpRequest.setPassword("password123");

        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"email\":\"testuser@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Username is already taken!"));
    }

    @Test
    void testRegisterUser_EmailExists() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("testuser");
        signUpRequest.setEmail("testuser@example.com");
        signUpRequest.setPassword("password123");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("testuser@example.com")).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testuser\",\"email\":\"testuser@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Email is already in use!"));
    }

//    @Test
//    void testAuthenticateUser_SuccessfulLogin() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("testuser");
//        loginRequest.setPassword("password123");
//
//        Authentication authentication = mock(Authentication.class);
//        when(authenticationManager.authenticate(any())).thenReturn(authentication);
//
////        UserDetailsImpl userDetails = new UserDetailsImpl(1L, "testuser", "testuser@example.com", "encoded_password", Collections.singletonList(new Role(ERole.ROLE_CUSTOMER)));
//        when(authentication.getPrincipal()).thenReturn(userDetails);
//
//        when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn("jwt_token");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"username\":\"testuser\",\"password\":\"password123\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.token").value("jwt_token"))
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.username").value("testuser"))
//                .andExpect(jsonPath("$.email").value("testuser@example.com"))
//                .andExpect(jsonPath("$.roles[0]").value("ROLE_CUSTOMER"));
//    }
}

