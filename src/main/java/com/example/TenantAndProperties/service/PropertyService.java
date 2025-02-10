package com.example.TenantAndProperties.service;

import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.model.Tenant;
import com.example.TenantAndProperties.repository.PropertyRepository;
import com.example.TenantAndProperties.repository.TenantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final TenantRepository tenantRepository;

    public void addProperty(Property property) {
        if (property.getTenants() != null && !property.getTenants().isEmpty()) {
            for (Tenant tenant : property.getTenants()) {
                Tenant existingTenant = tenantRepository.findById(tenant.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Tenant with ID " + tenant.getId() + " not found"));
                tenant.setProperty(property);
            }
        }
        propertyRepository.save(property);
    }

    public List<Property> getAll() {
        return propertyRepository.findAll();
    }

    public Property getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException
                        ("Property with ID " + id + " not found"));
    }

    public Property updateProperty(Long id, Property property) {
        return propertyRepository.findById(id)
                .map(existingProperty -> {
                    existingProperty.setAddress(property.getAddress());
                    existingProperty.setRentPrice(property.getRentPrice());
                    existingProperty.setTenants(property.getTenants()); // Update tenant list
                    return propertyRepository.save(existingProperty);
                })
                .orElseThrow(() -> new EntityNotFoundException("Property with ID " + id + " not found"));
    }

    public void deleteProperty(Long id) {
        Property property = propertyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Property with ID " + id + " not found"));

        if (!property.getTenants().isEmpty()) {
            throw new IllegalStateException("Property cannot be deleted because it has assigned tenants.");
        }
        propertyRepository.delete(property);
    }
}
