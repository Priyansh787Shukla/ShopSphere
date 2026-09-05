package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.LoginRequestDTO;
import com.endeavour.ShopSphere.dto.LoginResponseDTO;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.service.CustomJwtUserDetails;
import com.endeavour.ShopSphere.service.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        CustomJwtUserDetails userDetails = (CustomJwtUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(token);
    }
}
