package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User createUser(User user)
    {
        return userRepository.save(user);
    }
}
