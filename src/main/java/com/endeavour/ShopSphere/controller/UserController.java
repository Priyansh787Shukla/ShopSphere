package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.service.UserService;
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
    public User createUser(@RequestBody User user)
    {
        return userService.createUser(user);
    }
}
