package com.example.TenantAndProperties.controller;

import com.example.TenantAndProperties.dto.TenantDTO;
import com.example.TenantAndProperties.mapper.TenantMapper;
import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.model.Tenant;
import com.example.TenantAndProperties.service.PropertyService;
import com.example.TenantAndProperties.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tenant Controller", description = "Manage tenants")
@RequestMapping("/api/tenant")
public class TenantController {

    private final TenantService tenantService;
    private final TenantMapper tenantMapper;
    private final PropertyService propertyService;

    @PostMapping("/add/{propertyId}")
    @Operation(summary = "Add tenant", description = "Adding and returning a tenant")
    public ResponseEntity<?> registerTenant(@PathVariable Long propertyId, @Valid @RequestBody TenantDTO tenantDTO) {
        try {
            Property property = propertyService.getPropertyById(propertyId);
            Tenant tenant = tenantMapper.toTenantEntity(tenantDTO);
            tenant.setProperty(property);

            tenantService.addTenant(tenant);
            return ResponseEntity.status(HttpStatus.CREATED).body(tenantMapper.toTenantDTO(tenant));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping()
    @Operation(summary = "Get all tenants", description = "Returns a list of tenants")
    public ResponseEntity<List<TenantDTO>> getAllTenants() {
        log.info("fetching all tenants");
        List<TenantDTO> tenants = tenantService.getAll()
                .stream()
                .map(tenantMapper::toTenantDTO)
                .toList();
        return ResponseEntity.ok(tenants);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tenant by id", description = "Returns a specific tenant")
    public ResponseEntity<?> getTenantById(@PathVariable Long id) {
        try {
            Tenant tenant = tenantService.getTenantById(id);
            return ResponseEntity.ok(tenantMapper.toTenantDTO(tenant));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tenant with id " + id + " not found.");
        }
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update tenant by id", description = "Update and return a tenant")
    public ResponseEntity<?> updateTenant(@PathVariable Long id, @Valid @RequestBody TenantDTO tenantDTO) {
        try {
            Tenant updatedTenant = tenantService.updateTenant(id, tenantMapper.toTenantEntity(tenantDTO));
            return ResponseEntity.ok(tenantMapper.toTenantDTO(updatedTenant));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tenant with id " + id + " not found.");
        }
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete tenant by id ", description = "Returns a message of action")
    public ResponseEntity<?> deleteTenant(@PathVariable Long id) {
        try {
            tenantService.deleteTenant(id);
            return ResponseEntity.ok("Tenant was deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tenant with id " + id + " not found.");
        }

    }
}