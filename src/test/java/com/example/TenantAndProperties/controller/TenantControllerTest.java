package com.example.TenantAndProperties.controller;

import com.example.TenantAndProperties.dto.TenantDTO;
import com.example.TenantAndProperties.mapper.TenantMapper;
import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.model.Tenant;
import com.example.TenantAndProperties.service.PropertyService;
import com.example.TenantAndProperties.service.TenantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TenantControllerTest {

    @Mock
    private TenantService tenantService;

    @Mock
    private TenantMapper tenantMapper;

    @Mock
    private PropertyService propertyService;

    @InjectMocks
    private TenantController tenantController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Tenant tenant;
    private TenantDTO tenantDTO;
    private Property property;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tenantController).build();
        objectMapper = new ObjectMapper();

        property = new Property();
        property.setId(1L);
        property.setAddress("Kauno g. 10");
        property.setRentPrice(1200.0);

        tenant = new Tenant();
        tenant.setId(1L);
        tenant.setName("Arvydas Sabonis");
        tenant.setPhone("+37060011111");
        tenant.setProperty(property);

        tenantDTO = new TenantDTO("Arvydas Sabonis", "+37060011111", null);
    }

    @Test
    void testAddTenant() throws Exception {
        when(propertyService.getPropertyById(property.getId())).thenReturn(property);
        when(tenantMapper.toTenantEntity(any(TenantDTO.class))).thenReturn(tenant);
        when(tenantMapper.toTenantDTO(any(Tenant.class))).thenReturn(tenantDTO);
        doNothing().when(tenantService).addTenant(any(Tenant.class));

        mockMvc.perform(post("/api/tenant/add/{propertyId}", property.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tenantDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Arvydas Sabonis"))
                .andExpect(jsonPath("$.phone").value("+37060011111"));
    }

    @Test
    void testGetAllTenants() throws Exception {
        when(tenantService.getAll()).thenReturn(List.of(tenant));
        when(tenantMapper.toTenantDTO(tenant)).thenReturn(tenantDTO);

        mockMvc.perform(get("/api/tenant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Arvydas Sabonis"))
                .andExpect(jsonPath("$[0].phone").value("+37060011111"));
    }

    @Test
    void testGetTenantById() throws Exception {
        when(tenantService.getTenantById(tenant.getId())).thenReturn(tenant);
        when(tenantMapper.toTenantDTO(tenant)).thenReturn(tenantDTO);

        mockMvc.perform(get("/api/tenant/{id}", tenant.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Arvydas Sabonis"))
                .andExpect(jsonPath("$.phone").value("+37060011111"));
    }

    @Test
    void testDeleteTenant() throws Exception {
        doNothing().when(tenantService).deleteTenant(tenant.getId());

        mockMvc.perform(delete("/api/tenant/delete/{id}", tenant.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Tenant was deleted"));
    }
}
