package com.example.TenantAndProperties.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Address cant be empty")
    @Column(unique = true)
    private String address;
    @Positive(message = "Price must be more then 0")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    @JsonBackReference
    private Tenant tenant;
}
