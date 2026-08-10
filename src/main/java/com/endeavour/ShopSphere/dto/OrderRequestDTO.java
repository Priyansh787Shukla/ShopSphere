package com.endeavour.ShopSphere.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDTO
{
    @NotNull
    private Long userId;
}
