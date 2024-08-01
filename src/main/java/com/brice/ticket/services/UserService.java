package com.brice.ticket.services;

import com.brice.ticket.dtos.RegisterUserDto;
import com.brice.ticket.entities.User;
import com.brice.ticket.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
       this.userRepository.findAll().forEach(users::add);
        System.out.println("user ==");
        return users;
    }

    public Optional<User> getById(Integer idUser) {
        return this.userRepository.findById(idUser);
    }

    public Optional<User> getByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User saveUser(RegisterUserDto input) {
        User user = new User();
        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return this.userRepository.save(user);
    }

    public User updateUser(Integer idUser, RegisterUserDto input) {
        User user = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(input.getFullName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));

        return this.userRepository.save(user);
    }

}
