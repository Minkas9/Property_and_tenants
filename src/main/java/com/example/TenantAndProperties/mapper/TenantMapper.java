package com.example.TenantAndProperties.mapper;

import com.example.TenantAndProperties.dto.PropertyDTO;
import com.example.TenantAndProperties.dto.TenantDTO;
import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.model.Tenant;
import org.springframework.stereotype.Component;

@Component
public class TenantMapper {

    public TenantDTO toTenantDTO(final Tenant tenant) {
        TenantDTO tenantDTO = new TenantDTO();
        tenantDTO.setName(tenant.getName());
        tenantDTO.setPhone(tenant.getPhone());

        if (tenant.getProperty() != null) {
            PropertyDTO propertyDTO = new PropertyDTO();
            propertyDTO.setAddress(tenant.getProperty().getAddress());
            propertyDTO.setRentPrice(tenant.getProperty().getRentPrice());
            tenantDTO.setProperty(propertyDTO);
        }

        return tenantDTO;
    }

    public Tenant toTenantEntity(TenantDTO tenantDTO) {
        Tenant tenant = new Tenant();
        tenant.setName(tenantDTO.getName());
        tenant.setPhone(tenantDTO.getPhone());

        if (tenantDTO.getProperty() != null) {
            Property property = new Property();
            property.setAddress(tenantDTO.getProperty().getAddress());
            property.setRentPrice(tenantDTO.getProperty().getRentPrice());
            tenant.setProperty(property);
        }

        return tenant;
    }
}