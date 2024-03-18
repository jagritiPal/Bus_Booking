package com.BusBooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SubRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long subRouteId;
    private String fromLocation;
    private String toLocation;
    private String fromDate;
    private String toDate;
    private String totalDuration;
    private String fromTime;
    private String toTime;

    // Many-to-one mapping with Route
    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    // Many-to-one mapping with Route
    @ManyToOne
    @JoinColumn(name = "bus_id")
    private Bus bus;


}
