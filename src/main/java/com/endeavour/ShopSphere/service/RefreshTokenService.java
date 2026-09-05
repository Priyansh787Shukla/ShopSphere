package com.endeavour.ShopSphere.service;

import com.endeavour.ShopSphere.entity.RefreshToken;
import com.endeavour.ShopSphere.entity.User;
import com.endeavour.ShopSphere.exception.InvalidRefreshTokenException;
import com.endeavour.ShopSphere.exception.RefreshTokenExpiredException;
import com.endeavour.ShopSphere.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService
{
    @Value("${shopsphere.refresh-token.expiration}")
    private long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository)
    {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(User user)
    {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusSeconds(refreshTokenExpiration))
                .user(user)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken)
    {
        if(refreshToken.getExpiryDate().isBefore(Instant.now()))
        {
            refreshTokenRepository.delete(refreshToken);
            throw new RefreshTokenExpiredException("Refresh Token Expired");
        }
        return refreshToken;
    }

    public RefreshToken findByToken(String token)
    {
        return refreshTokenRepository.findByToken(token).orElseThrow(()->new InvalidRefreshTokenException("Refresh Token Not Found"));
    }

    public RefreshToken rotateRefreshToken(RefreshToken oldRefreshToken)
    {
        refreshTokenRepository.delete(oldRefreshToken);
        return createRefreshToken(oldRefreshToken.getUser());
    }

    @Transactional
    public void deleteByToken(String token)
    {
        refreshTokenRepository.deleteByToken(token);
    }
}