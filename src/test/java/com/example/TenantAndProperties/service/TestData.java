package com.example.TenantAndProperties.service;

import com.example.TenantAndProperties.dto.PropertyDTO;
import com.example.TenantAndProperties.dto.TenantDTO;
import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.model.Tenant;

import java.util.List;

public class TestData {

    public static Property createTestProperty() {

        return Property.builder()
                .id(1L)
                .address("Kauno g. 11, Kaunas")
                .rentPrice(999.00)
                .tenants(List.of())
                .build();
    }

    public static Tenant createTestTenant() {
        return Tenant.builder()
                .id(1L)
                .name("Arvydas Sabonis")
                .phone("065478991")
                .build();
    }

    public static PropertyDTO createTestPropertyDTO() {
        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setName("Arvydas Sabonis");
        tenantDTO.setPhone("065478991");

        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress("Kauno g. 11, Kaunas");
        propertyDTO.setRentPrice(999.00);
        propertyDTO.setTenants(List.of(tenantDTO));

        return propertyDTO;
    }

    public static TenantDTO createTestTenantDTO() {
        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setName("Arvydas Sabonis");
        tenantDTO.setPhone("065478991");

        return tenantDTO;
    }

}



