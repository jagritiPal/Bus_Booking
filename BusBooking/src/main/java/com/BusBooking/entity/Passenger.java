package com.BusBooking.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long passengerId;
    private String firstName;
    private String lastName;
    private String email;
    private long mobile;

    @Column(name = "bus_id", unique = true)
    private long busId;

    @Column(name = "route_id", unique = true)
    private long routeId;

    @Column(name = "subRoute_id", unique = true )
    private long subRouteId;

}
