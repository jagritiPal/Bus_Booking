package com.BusBooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long busId;

    @Column(unique = true)
    private String busNumber;
    private String busType;
    private double price;
    private int totalSeats;
    private int availableSeats;

    // One-to-one mapping with Driver
    @OneToOne(mappedBy = "bus")
    private Driver driver;


    // One-to-one mapping with route
    @OneToOne(mappedBy = "bus")
    private Route route;

    // One-to-many mapping with SubRoute
    @OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
    private List<SubRoute> subRoutes;


}
