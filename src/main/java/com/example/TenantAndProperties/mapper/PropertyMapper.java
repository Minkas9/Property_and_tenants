package com.example.TenantAndProperties.mapper;

import com.example.TenantAndProperties.dto.PropertyDTO;
import com.example.TenantAndProperties.dto.TenantDTO;
import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.model.Tenant;
import org.springframework.stereotype.Component;

@Component
public class PropertyMapper {

    public PropertyDTO toPropertyDTO(final Property property) {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setAddress(property.getAddress());
        propertyDTO.setRentPrice(property.getRentPrice());

        if (property.getTenants() != null && !property.getTenants().isEmpty()) {
            propertyDTO.setTenants(property.getTenants()
                    .stream()
                    .map(tenant -> new TenantDTO(tenant.getName(), tenant.getPhone(), null))
                    .toList());
        }

        return propertyDTO;
    }

    public Property toPropertyEntity(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setAddress(propertyDTO.getAddress());
        property.setRentPrice(propertyDTO.getRentPrice());

        if (propertyDTO.getTenants() != null && !propertyDTO.getTenants().isEmpty()) {
            property.setTenants(propertyDTO.getTenants()
                    .stream()
                    .map(tenantDTO -> new Tenant(null, tenantDTO.getName(), tenantDTO.getPhone(), property))
                    .toList());
        }

        return property;
    }
}