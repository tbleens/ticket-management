package com.brice.ticket;

import com.brice.ticket.controllers.UserController;
import com.brice.ticket.dtos.RegisterUserDto;
import com.brice.ticket.entities.User;
import com.brice.ticket.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        User user = new User();
//        user.setId(1);
        user.setFullName("Test User");
        user.setEmail("test@example.com");

        when(userService.getAll()).thenReturn(Arrays.asList(user));

        ResponseEntity<List<User>> response = userController.getAll();
        assertEquals(1, response.getBody().size());
        assertEquals("Test User", response.getBody().get(0).getFullName());
    }

    @Test
    void testGetById() {
        User user = new User();
//        user.setId(1);
        user.setFullName("Test User");

        when(userService.getById(1)).thenReturn(Optional.of(user));

        ResponseEntity<Optional<User>> response = userController.getById(1);
        assertEquals("Test User", response.getBody().get().getFullName());
    }

    @Test
    void testCreateUser() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setFullName("New User");
        dto.setEmail( "newuser@example.com");
        dto.setPassword( "password");
        User user = new User();
//        user.setId(1);
        user.setFullName("New User");

        when(userService.saveUser(dto)).thenReturn(user);

        ResponseEntity<User> response = userController.CreateUser(dto);
        assertEquals("New User", response.getBody().getFullName());
    }

    @Test
    void testUpdateUser() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setFullName("Updated User");
        dto.setEmail( "updateduser@example.com");
        dto.setPassword( "newpassword");
        User user = new User();
//        user.setId(1);
        user.setFullName("Updated User");

        when(userService.updateUser(1, dto)).thenReturn(user);

        ResponseEntity<User> response = userController.UpdateUser(1, dto);
        assertEquals("Updated User", response.getBody().getFullName());
    }
}

