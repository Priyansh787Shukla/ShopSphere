package com.endeavour.ShopSphere.dto;

import com.endeavour.ShopSphere.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDTO
{
    private Long id;
    private String name;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
}
