package com.example.TenantAndProperties.controller;

import com.example.TenantAndProperties.dto.PropertyDTO;
import com.example.TenantAndProperties.mapper.PropertyMapper;
import com.example.TenantAndProperties.model.Property;
import com.example.TenantAndProperties.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PropertyControllerTest {

    @Mock
    private PropertyService propertyService;

    @Mock
    private PropertyMapper propertyMapper;

    @InjectMocks
    private PropertyController propertyController;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private Property property;
    private PropertyDTO propertyDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(propertyController).build();
        objectMapper = new ObjectMapper();

        property = new Property();
        property.setId(1L);
        property.setAddress("Gedimino pr. 3, Vilnius");
        property.setRentPrice(1000.0);
        property.setTenants(Collections.emptyList());

        propertyDTO = new PropertyDTO("Gedimino pr. 3, Vilnius", 1000.0, Collections.emptyList());
    }

    @Test
    void testAddProperty() throws Exception {
        doNothing().when(propertyService).addProperty(any());

        mockMvc.perform(post("/api/property/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(propertyDTO)))
                .andExpect(status().isCreated()); // HTTP 201 Created
    }

    @Test
    void testGetAllProperties() throws Exception {
        when(propertyService.getAll()).thenReturn(List.of(property));
        when(propertyMapper.toPropertyDTO(any(Property.class))).thenReturn(propertyDTO);

        mockMvc.perform(get("/api/property"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("Gedimino pr. 3, Vilnius"))
                .andExpect(jsonPath("$[0].rentPrice").value(1000.0));
    }


    @Test
    void testGetPropertyById() throws Exception {
        when(propertyService.getPropertyById(1L)).thenReturn(property);
        when(propertyMapper.toPropertyDTO(any(Property.class))).thenReturn(propertyDTO);

        mockMvc.perform(get("/api/property/1"))
                .andExpect(status().isOk()) // HTTP 200 OK
                .andExpect(jsonPath("$.address").value("Gedimino pr. 3, Vilnius"))
                .andExpect(jsonPath("$.rentPrice").value(1000.0));
    }

    @Test
    void testDeleteProperty() throws Exception {
        when(propertyService.getPropertyById(1L)).thenReturn(property);
        doNothing().when(propertyService).deleteProperty(1L);

        mockMvc.perform(delete("/api/property/delete/1"))
                .andExpect(status().isOk()) // HTTP 200 OK
                .andExpect(content().string("Property was deleted"));
    }
}
