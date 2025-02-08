package com.example.TenantAndProperties.controller;

import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.service.PropertyService;
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
@Tag(name = "Property Controller", description = "Manage properties")
@RequestMapping("/api/property")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping("/add/{tenantId}")
    @Operation(summary = "Add property", description = "Adding and returning a property")
    public ResponseEntity<?> registerProperty(@PathVariable Long tenantId, @Valid @RequestBody Property property) {
        try {
            propertyService.addProperty(tenantId, property);
            return ResponseEntity.status(HttpStatus.CREATED).body(property);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping()
    @Operation(summary = "Get all properties", description = "Returns a list of properties")
    public ResponseEntity<List<Property>> getAllProperties() {
        log.info("Endpoint was hit");
        return ResponseEntity.ok(propertyService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get property by id", description = "Returns a specific property")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyById(id));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update property by id", description = "Update and return a property")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @Valid @RequestBody Property property) {
        return ResponseEntity.ok(propertyService.updateProperty(id, property));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete property by id", description = "Returns a message of action")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        try {
            propertyService.deleteProperty(id);
            return ResponseEntity.ok("Property was deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property with id " + id + " not found.");
        }
    }
}