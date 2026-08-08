package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.UserRequestDTO;
import com.endeavour.ShopSphere.dto.UserResponseDTO;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

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

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id)
    {
        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/csrf")
    public CsrfToken getToken(CsrfToken csrfToken)
    {
        return csrfToken;
    }
}
