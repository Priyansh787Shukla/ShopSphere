package com.endeavour.ShopSphere.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO
{
    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @Email
    @NotNull
    private String email;

    @NotBlank
    @Size(min = 6)
    @NotNull
    private String password;
}
