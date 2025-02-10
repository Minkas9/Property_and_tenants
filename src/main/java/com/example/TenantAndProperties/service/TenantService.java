package com.example.TenantAndProperties.service;

import com.example.TenantAndProperties.model.Tenant;
import com.example.TenantAndProperties.repository.TenantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TenantService {

    private final TenantRepository tenantRepository;

    public void addTenant(Long propertyId, Tenant tenant) {
        if (tenant.getProperty() == null) {
            throw new IllegalArgumentException("Tenant must be linked to a property.");
        }
        tenantRepository.save(tenant);
    }

    public List<Tenant> getAll() {
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tenant with ID " + id + " not found"));
    }

    public Tenant updateTenant(Long id, Tenant tenant) {
        return tenantRepository.findById(id)
                .map(existingTenant -> {
                    existingTenant.setName(tenant.getName());
                    existingTenant.setPhone(tenant.getPhone());
                    existingTenant.setProperty(tenant.getProperty());
                    return tenantRepository.save(existingTenant);
                })
                .orElseThrow(() -> new EntityNotFoundException("Tenant with ID " + id + " not found"));
    }

    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }
}
