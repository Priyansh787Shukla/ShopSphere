package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.dto.UserRequestDTO;
import com.endeavour.ShopSphere.dto.UserResponseDTO;
import com.endeavour.ShopSphere.entity.Role;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.exception.UserAlreadyExistsException;
import com.endeavour.ShopSphere.exception.UserNotFoundException;
import com.endeavour.ShopSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService
{
    @Value("${shopsphere.admin.email}")
    private String adminEmail;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    public UserResponseDTO createUser(UserRequestDTO userRequest)
    {
        if(userRepository.existsByEmail(userRequest.getEmail()))
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


    public UserResponseDTO getUserById(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));

        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt());
    }


    public List<UserResponseDTO> getAllUsers(String email)
    {
        User uuser = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));

        return userRepository.findAll().stream().map(user-> new UserResponseDTO(user.getId(),
                user.getName(), user.getEmail(), user.getRole(), user.getCreatedAt())).toList();
    }

    public UserResponseDTO updateUserById(String email, UserRequestDTO userRequest)
    {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));

        if(!(user.getEmail().equals(userRequest.getEmail())) && userRepository.existsByEmail(userRequest.getEmail()))
            throw new UserAlreadyExistsException("Email already registered");

        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRole(), savedUser.getCreatedAt());
    }

    public void deleteUserById(String email)
    {
        User user = userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User Not Found"));

        userRepository.delete(user);
    }

    public User registerOrUpdateUser(String provider, OAuth2User oauth2User)
    {
        String email = oauth2User.getAttribute("email");

        User user = userRepository.findByEmail(email).orElseGet(User::new);

        String name;

        if (provider.equals("google"))
        {
            name = oauth2User.getAttribute("name");
        }
        else
        {
            name = oauth2User.getAttribute("login");
        }

        user.setName(name);
        user.setEmail(email);
        if (email.equals(adminEmail))
        {
            user.setRole(Role.ADMIN);
        }
        else
        {
            user.setRole(Role.CUSTOMER);
        }
        user.setPassword(null);

        return userRepository.save(user);
    }

    public User getUserByEmail(String email)
    {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
