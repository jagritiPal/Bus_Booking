package com.BusBooking.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {

    private long driverId;
    private String driverName;
    private String driverLicense;
    private String driverAadharNumber;
    private String address;
    private String contactNumber;
    private String alternateContactNumber;
    private String emailId;

    private long busId;

}
