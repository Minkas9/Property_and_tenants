package com.example.TenantAndProperties.service;

import com.example.TenantAndProperties.model.Tenant;
import com.example.TenantAndProperties.repository.TenantRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TenantServiceTest {

    @Mock
    private TenantRepository tenantRepository;

    @InjectMocks
    private TenantService tenantService;

    private Tenant tenant;

    @BeforeEach
    void setUp() {
        tenant = TestData.createTestTenant();
    }

    @Test
    void testIfAddTenantAddsTenant() {

        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);

        tenantService.addTenant(tenant);

        verify(tenantRepository, times(1)).save(tenant);
    }

    @Test
    void testIfGetTenantByIdReturnsTenantIfFound() {

        when(tenantRepository.findById(1L)).thenReturn(java.util.Optional.of(tenant));

        Tenant foundTenant = tenantService.getTenantById(1L);

        assertNotNull(foundTenant);
        assertEquals("Arvydas Sabonis", foundTenant.getName());
    }

    @Test
    void testIfTenantIsUpdatedWhenExists() {

        Tenant updatedTenant = TestData.createTestTenant();
        updatedTenant.setName("Jonas Valančiūnas");
        updatedTenant.setPhone("065478994");

        when(tenantRepository.findById(1L)).thenReturn(java.util.Optional.of(tenant));
        when(tenantRepository.save(any(Tenant.class))).thenReturn(updatedTenant);

        Tenant updated = tenantService.updateTenant(1L, updatedTenant);

        assertNotNull(updated);
        assertEquals("Jonas Valančiūnas", updated.getName());
        assertEquals("065478994", updated.getPhone());

        verify(tenantRepository, times(1)).save(updatedTenant);
    }

    @Test
    void testIfDeletesTenantWhenExists() {

        when(tenantRepository.existsById(1L)).thenReturn(true);

        tenantService.deleteTenant(1L);

        verify(tenantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testIfDeleteTenantFailsWhenTenantDoesNotExist() {

        when(tenantRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            tenantService.deleteTenant(1L);
        });

        assertTrue(exception.getMessage().contains("Tenant with ID 1 not found"));

        verify(tenantRepository, never()).deleteById(anyLong());
    }
}