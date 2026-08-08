package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.dto.UserResponseDTO;
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

    public UserResponseDTO createUser(User user)
    {
        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRole(), savedUser.getCreatedAt());
    }
}
