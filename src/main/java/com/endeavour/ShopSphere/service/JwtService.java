package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService
{
    private final JwtEncoder jwtEncoder;
    private final long jwtExpiration;

    public JwtService(
            JwtEncoder jwtEncoder,
            @Value("${shopsphere.jwt.expiration}") long jwtExpiration)
    {
        this.jwtEncoder = jwtEncoder;
        this.jwtExpiration = jwtExpiration;
    }

    public String generateToken(User user)
    {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(user.getEmail())
                .issuedAt(now)
                .expiresAt(now.plusMillis(jwtExpiration))
                .claim("role", user.getRole().name())
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(claims)
        ).getTokenValue();
    }
}