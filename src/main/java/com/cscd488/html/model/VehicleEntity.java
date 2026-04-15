package com.cscd488.html.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehicle")
public class VehicleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vin;
    private String make;
    private String model;
    private String year;
    private String freeFormText;
}