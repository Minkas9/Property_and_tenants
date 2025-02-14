package com.example.TenantAndProperties.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyDTO {

    @NotEmpty(message = "Property address is required.")
    private String address;

    @Positive(message = "Price must be greater than zero")
    private Double rentPrice;

    @NotNull(message = "Tenant ID is required.")
    private List<TenantDTO> tenants;
}
