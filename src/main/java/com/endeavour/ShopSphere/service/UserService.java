package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.dto.UserRequestDTO;
import com.endeavour.ShopSphere.dto.UserResponseDTO;
import com.endeavour.ShopSphere.entity.Role;
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

    public UserResponseDTO createUser(UserRequestDTO userRequest)
    {
        if(userRepository.findByEmail(userRequest.getEmail()).isPresent())
            throw new RuntimeException("Email already registered");

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(Role.CUSTOMER);

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRole(), savedUser.getCreatedAt());
    }
}
