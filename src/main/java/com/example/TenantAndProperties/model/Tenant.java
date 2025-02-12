package com.example.TenantAndProperties.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cant be empty")
    private String name;

    @NotBlank(message = "Name cant be empty")
    private String phone;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "property_id")
    private Property property;
}
