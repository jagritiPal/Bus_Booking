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
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long routeId;
    private String fromLocation;
    private String toLocation;
    private String fromDate;
    private String toDate;
    private String totalDuration;
    private String fromTime;
    private String toTime;


    // One-to-many mapping with SubRoute
    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private List<SubRoute> subRoutes;

    @OneToOne
    @JoinColumn(name = "busId")
    private Bus bus;


}

