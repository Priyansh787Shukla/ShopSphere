package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.UserRequestDTO;
import com.endeavour.ShopSphere.dto.UserResponseDTO;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @GetMapping
    public UserResponseDTO getUserById(Authentication authentication)
    {
        return userService.getUserById(authentication.getName());
    }

    @GetMapping("/all")
    public List<UserResponseDTO> getAllUsers(Authentication authentication)
    {
        return userService.getAllUsers(authentication.getName());
    }

    @PutMapping
    public ResponseEntity<UserResponseDTO> updateUserById(Authentication authentication, @Valid @RequestBody UserRequestDTO userRequest)
    {
        return ResponseEntity.status(HttpStatus.OK).
                body(userService.updateUserById(authentication.getName(), userRequest));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserById(Authentication authentication)
    {
        userService.deleteUserById(authentication.getName());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("User Deleted Successfully");
    }

    @GetMapping("/csrf")
    public CsrfToken getToken(CsrfToken csrfToken)
    {
        return csrfToken;
    }
}
