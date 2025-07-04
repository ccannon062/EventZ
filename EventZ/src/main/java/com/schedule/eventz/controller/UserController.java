package com.schedule.eventz.controller;

import com.schedule.eventz.models.Role;
import com.schedule.eventz.models.User;
import com.schedule.eventz.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.schedule.eventz.service.UserService;
import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final EmailService emailService;
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        this.emailService = new EmailService(null);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/role/{role}")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getUsersByRole(@PathVariable Role role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getActiveUsers() {
        return userService.getActiveUsers();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody User user) {
        userService.registerUser(user);
        emailService.sendWelcomeEmail(user);
    }
}
