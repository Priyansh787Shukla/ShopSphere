package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.UserRequestDTO;
import com.endeavour.ShopSphere.dto.UserResponseDTO;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

@RestController
@RequestMapping("/api/users")
public class UserController
{
    private final UserService userService;
    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO userRequest)
    {
        return userService.createUser(userRequest);
    }

    @GetMapping
    public UserResponseDTO getUserById(@RequestParam Long id)
    {
        return userService.getUserById(id);
    }
}
