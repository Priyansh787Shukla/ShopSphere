package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.dto.UserRequestDTO;
import com.endeavour.ShopSphere.dto.UserResponseDTO;
import com.endeavour.ShopSphere.entity.Role;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.exception.UserAlreadyExistsException;
import com.endeavour.ShopSphere.exception.UserNotFoundException;
import com.endeavour.ShopSphere.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public UserResponseDTO createUser(UserRequestDTO userRequest)
    {
        if(userRepository.findByEmail(userRequest.getEmail()))
            throw new UserAlreadyExistsException("Email already registered");

        User user = new User();
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.CUSTOMER);

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRole(), savedUser.getCreatedAt());
    }


    public UserResponseDTO getUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User Not Found"));

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }


    public List<UserResponseDTO> getAllUsers()
    {
        return userRepository.findAll().stream().map(user-> new UserResponseDTO(user.getId(),
                user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt())).toList();
    }

    public UserResponseDTO updateUserById(Long id, UserRequestDTO userRequest)
    {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User Not Found"));

        if(!(user.getEmail().equals(userRequest.getEmail())) && userRepository.findByEmail(userRequest.getEmail()))
            throw new UserAlreadyExistsException("Email already registered");

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRole(), savedUser.getCreatedAt());
    }

    public void deleteUserById(Long id)
    {
        User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException("User Not Found"));

        userRepository.delete(user);
    }
}
