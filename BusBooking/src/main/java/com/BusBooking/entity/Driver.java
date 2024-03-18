package com.BusBooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;
    private String driverName;
    private String driverLicense;
    private String driverAadharNumber;
    private String address;
    private String contactNumber;
    private String alternateContactNumber;
    private String emailId;

    // One-to-one mapping with Bus
    @OneToOne
    @JoinColumn(name = "bus_id", referencedColumnName = "busId")
    private Bus bus;
}
