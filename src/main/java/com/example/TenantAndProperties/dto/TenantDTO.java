package com.example.TenantAndProperties.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantDTO {

    @NotEmpty(message = "Tenant name is required.")
    private String name;

    @NotEmpty(message = "Tenant phone is required.")
    private String phone;
    private PropertyDTO property;
}
