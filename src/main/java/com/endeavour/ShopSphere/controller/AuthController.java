package com.endeavour.ShopSphere.controller;

import com.endeavour.ShopSphere.dto.LoginRequestDTO;
import com.endeavour.ShopSphere.dto.LoginResponseDTO;
import com.endeavour.ShopSphere.dto.LogoutRequestDTO;
import com.endeavour.ShopSphere.dto.RefreshTokenRequestDTO;
import com.endeavour.ShopSphere.entity.RefreshToken;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.service.CustomJwtUserDetails;
import com.endeavour.ShopSphere.service.JwtService;
import com.endeavour.ShopSphere.service.RefreshTokenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenService refreshTokenService)
    {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        CustomJwtUserDetails userDetails = (CustomJwtUserDetails) authentication.getPrincipal();

        User user = userDetails.getUser();

        String token = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user).getToken();

        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(token, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDTO> refreshAccessToken(@Valid @RequestBody RefreshTokenRequestDTO request)
    {
        RefreshToken refreshToken = refreshTokenService.findByToken(request.getRefreshToken());
        refreshTokenService.verifyExpiration(refreshToken);
        User user = refreshToken.getUser();
        String accessToken = jwtService.generateToken(user);
        RefreshToken newRefreshToken = refreshTokenService.rotateRefreshToken(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(accessToken, newRefreshToken.getToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody LogoutRequestDTO request)
    {
        refreshTokenService.deleteByToken(request.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Logged Out Successfully");
    }
}
