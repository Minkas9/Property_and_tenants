package com.example.TenantAndProperties.service;

import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.model.Tenant;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

public class TenantTestData {
    public static Tenant testTenant() {
        return Tenant.builder()
                .name("Petras Petraitis")
                .phone("068741558")
                .property(List.of(
                        new Property(null, "Vilniaus g. 5", 1500.0, null),
                        new Property(null, "Kauno g. 10", 2000.0, null)
                ))
                .build();
    }
}
