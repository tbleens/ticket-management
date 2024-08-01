package com.brice.ticket.controllers;

import com.brice.ticket.dtos.RegisterUserDto;
import com.brice.ticket.entities.User;
import com.brice.ticket.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Tag(name = "get", description = "Method get pour afficher tous les utilisateurs")
    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        List<User> users  = userService.getAll();
        System.out.println(users.get(0));
        return ResponseEntity.ok(users);
    }

    @Tag(name = "get", description = "Method get pour afficher un utilisateur")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable Integer id) {
        Optional<User> savedUser = userService.getById(id);
        return ResponseEntity.ok(savedUser);
    }

    @Tag(name = "post", description = "Method Post pour creer un utilisateur")
    @PostMapping("")
    public ResponseEntity<User> CreateUser(@RequestBody RegisterUserDto registerUserDto) {
        User savedUser = userService.saveUser(registerUserDto);
        return ResponseEntity.ok(savedUser);
    }

    @Tag(name = "put", description = "Method Put pour modifier un utilisateur")
    @PutMapping("/{id}")
    public ResponseEntity<User> UpdateUser(@PathVariable Integer id, @RequestBody RegisterUserDto registerUserDto) {
        User updateUser = userService.updateUser(id, registerUserDto);
        return ResponseEntity.ok(updateUser);
    }
}

