package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.Enums.Role;
import com.example.demo.SecurityConfigurations.DataInitializer;
import com.example.demo.entity.User;
import com.example.demo.repository.DealRepository;
import com.example.demo.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DealRepository dealRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializer dataInitializer;

    private User admin;
    private User john;
    private User jane;
    private User bob;

    @BeforeEach
    void setup() {
        admin = new User();
        admin.setUsername("admin");
        admin.setRole(Role.ADMIN);

        john = new User();
        john.setUsername("john.doe");

        jane = new User();
        jane.setUsername("jane.smith");

        bob = new User();
        bob.setUsername("bob.wilson");
    }

    // -------------------- USERS --------------------

    @Test
    void shouldCreateUsersWhenTheyDoNotExist() throws Exception {
        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(passwordEncoder.encode(any())).thenReturn("encodedPwd");

        // 🔥 Prevent deal creation
        when(dealRepository.count()).thenReturn(1L);

        dataInitializer.run();

        verify(userRepository, times(6)).save(any(User.class));
        verify(dealRepository, never()).saveAll(any());
    }

    @Test
    void shouldNotCreateUsersIfTheyAlreadyExist() throws Exception {
        when(userRepository.existsByUsername(any())).thenReturn(true);

        // 🔥 Prevent deal creation
        when(dealRepository.count()).thenReturn(1L);

        dataInitializer.run();

        verify(userRepository, never()).save(any(User.class));
    }

    // -------------------- DEALS --------------------

    @Test
    void shouldCreateDealsWhenNoDealsExist() throws Exception {
        // 🔥 Prevent user creation
        when(userRepository.existsByUsername(any())).thenReturn(true);

        when(dealRepository.count()).thenReturn(0L);

        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(admin));
        when(userRepository.findByUsername("john.doe")).thenReturn(Optional.of(john));
        when(userRepository.findByUsername("jane.smith")).thenReturn(Optional.of(jane));
        when(userRepository.findByUsername("bob.wilson")).thenReturn(Optional.of(bob));

        dataInitializer.run();

        verify(dealRepository).saveAll(any());
        verify(dealRepository, atLeastOnce()).save(any());
    }

    @Test
    void shouldNotCreateDealsIfDealsAlreadyExist() throws Exception {
        // 🔥 Prevent user creation
        when(userRepository.existsByUsername(any())).thenReturn(true);

        when(dealRepository.count()).thenReturn(5L);

        dataInitializer.run();

        verify(dealRepository, never()).saveAll(any());
    }
}
