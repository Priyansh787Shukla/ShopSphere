package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.UserRequestDTO;
import com.endeavour.ShopSphere.dto.UserResponseDTO;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
