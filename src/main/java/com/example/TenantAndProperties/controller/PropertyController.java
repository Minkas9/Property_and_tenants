package com.example.TenantAndProperties.controller;

import com.example.TenantAndProperties.dto.PropertyDTO;
import com.example.TenantAndProperties.mapper.PropertyMapper;
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
    private final PropertyMapper propertyMapper;

    @PostMapping("/add")
    @Operation(summary = "Add property", description = "Adding and returning a property")
    public ResponseEntity<?> registerProperty(@Valid @RequestBody PropertyDTO propertyDTO) {
        try {
            Property property = propertyMapper.toPropertyEntity(propertyDTO);
            propertyService.addProperty(property);
            return ResponseEntity.status(HttpStatus.CREATED).body(propertyMapper.toPropertyDTO(property));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping()
    @Operation(summary = "Get all properties", description = "Returns a list of properties")
    public ResponseEntity<List<PropertyDTO>> getAllProperties() {
        log.info("Fetching all properties");
        List<PropertyDTO> properties = propertyService.getAll()
                .stream()
                .map(propertyMapper::toPropertyDTO)
                .toList();
        return ResponseEntity.ok(properties);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get property by id", description = "Returns a specific property")
    public ResponseEntity<PropertyDTO> getPropertyById(@PathVariable Long id) {
        Property property = propertyService.getPropertyById(id);
        return ResponseEntity.ok(propertyMapper.toPropertyDTO(property));
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update property by id", description = "Update and return a property")
    public ResponseEntity<PropertyDTO> updateProperty(@PathVariable Long id, @Valid @RequestBody PropertyDTO propertyDTO) {
        Property updatedProperty = propertyService.updateProperty(id, propertyMapper.toPropertyEntity(propertyDTO));
        return ResponseEntity.ok(propertyMapper.toPropertyDTO(updatedProperty));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete property by id", description = "Prevents deletion if tenants are assigned")
    public ResponseEntity<?> deleteProperty(@PathVariable Long id) {
        try {
            Property property = propertyService.getPropertyById(id);

            if (!property.getTenants().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Cannot delete property with assigned tenants.");
            }

            propertyService.deleteProperty(id);
            return ResponseEntity.ok("Property was deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Property with id " + id + " not found.");
        }
    }
}