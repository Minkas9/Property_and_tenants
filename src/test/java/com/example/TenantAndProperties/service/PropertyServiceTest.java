package com.example.TenantAndProperties.service;

import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.model.Tenant;
import com.example.TenantAndProperties.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property property;
    private Tenant tenant;

    @BeforeEach
    void setUp() {
        tenant = TestData.createTestTenant();
        property = TestData.createTestProperty();
    }

    @Test
    void testIfAddPropertyAddsProperty() {
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        propertyService.addProperty(property);

        verify(propertyRepository, times(1)).save(property);
        assertNotNull(property);
        assertTrue(property.getTenants().isEmpty(), "Property should have no tenants");
    }

    @Test
    void testIfGetAllPropertyReturnsAllProperty() {
        when(propertyRepository.findAll()).thenReturn(List.of(property));

        List<Property> properties = propertyService.getAll();

        verify(propertyRepository, times(1)).findAll();
        assertNotNull(properties);
        assertEquals(1, properties.size());
        assertEquals(property.getAddress(), properties.get(0).getAddress());
    }

    @Test
    void testIfGetPropertyByIdReturnsPropertyIfFound() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        Property foundProperty = propertyService.getPropertyById(1L);

        verify(propertyRepository, times(1)).findById(1L);
        assertNotNull(foundProperty);
        assertEquals(property.getAddress(), foundProperty.getAddress());
        assertEquals(property.getRentPrice(), foundProperty.getRentPrice());
    }

    @Test
    void testIfPropertyIsUpdatedWhenExists() {

        Property updatedProperty = new Property();
        updatedProperty.setId(property.getId());
        updatedProperty.setAddress("Vilniaus g. 12, Vilnius");
        updatedProperty.setRentPrice(1200.00);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(updatedProperty);

        Property updated = propertyService.updateProperty(1L, updatedProperty);

        assertNotNull(updated);
        assertEquals("Vilniaus g. 12, Vilnius", updated.getAddress());
        assertEquals(1200.00, updated.getRentPrice());

        verify(propertyRepository, times(1)).save(updatedProperty);
    }

    @Test
    void testIfDeletesPropertyWhenNoTenants() {
        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        propertyService.deleteProperty(1L);

        verify(propertyRepository, times(1)).delete(property);
    }

    @Test
    void testIfDeletePropertyFailsWhenTenantsExist() {

        property.getTenants().add(tenant);

        when(propertyRepository.findById(1L)).thenReturn(Optional.of(property));

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            propertyService.deleteProperty(1L);
        });

        assertTrue(exception.getMessage().contains
                ("Property cannot be deleted because it has assigned tenants."));

        verify(propertyRepository, never()).delete(any(Property.class));
    }
}
