package com.example.TenantAndProperties.controller;

import com.example.TenantAndProperties.model.Tenant;
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

    public ResponseEntity<?> registerTenant(@Valid @RequestBody Tenant tenant) {
        if (tenant.getProperty() == null || tenant.getProperty().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tenant must have at least one property.");
        }
        tenantService.addTenant(tenant);
        return ResponseEntity.status(HttpStatus.CREATED).body(tenant);
    }

    @GetMapping()
    @Operation(summary = "Get all tenants", description = "Returns a list of tenants")
    public ResponseEntity<List<Tenant>> getAllTenants() {
        log.info("Endpoint was hit");
        return ResponseEntity.ok(tenantService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tenant by id", description = "Returns a specific tenant")
    public ResponseEntity<Tenant> getTenantById(@PathVariable Long id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update tenant by id", description = "Update and return a tenant")
    public ResponseEntity<Tenant> updateTenant(@PathVariable Long id, @Valid @RequestBody Tenant tenant) {
        return ResponseEntity.ok(tenantService.updateTenant(id, tenant));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete tenant by id ", description = "Returns a message of action")
    public ResponseEntity<?> deleteTenant(@PathVariable Long id) {
        if (tenantService.getTenantById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tenant with id " + id + " not found.");
        }
        tenantService.deleteTenant(id);
        return ResponseEntity.ok("Tenant was deleted");
    }

}