package com.BusBooking.service;

import com.BusBooking.entity.Bus;
import com.BusBooking.entity.Driver;
import com.BusBooking.payload.DriverDTO;
import com.BusBooking.repository.BusRepository;
import com.BusBooking.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BusRepository busRepository;

    public DriverDTO addDriver(DriverDTO driverDTO) {
        // Check if the provided busId exists in the Bus table
        if (!busRepository.existsById(driverDTO.getBusId())) {
            throw new IllegalArgumentException("Bus with ID " + driverDTO.getBusId() + " does not exist");
        }

        // Convert DTO to entity
        Driver driver = mapToEntity(driverDTO);

        // Save the driver entity
        Driver savedDriver = driverRepository.save(driver);

        // Convert the saved entity back to DTO and return
        return mapToDto(savedDriver);
    }

    private Driver mapToEntity(DriverDTO driverDTO) {
        Driver driver = new Driver();
        driver.setDriverName(driverDTO.getDriverName());
        driver.setDriverLicense(driverDTO.getDriverLicense());
        driver.setDriverAadharNumber(driverDTO.getDriverAadharNumber());
        driver.setAddress(driverDTO.getAddress());
        driver.setContactNumber(driverDTO.getContactNumber());
        driver.setAlternateContactNumber(driverDTO.getAlternateContactNumber());
        driver.setEmailId(driverDTO.getEmailId());

        // Set the bus
        Bus bus = busRepository.findById(driverDTO.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Bus with ID " + driverDTO.getBusId() + " does not exist"));
        driver.setBus(bus);
        return driver;
    }

    private DriverDTO mapToDto(Driver driver) {
        DriverDTO driverDTO = new DriverDTO();
        driverDTO.setDriverId(driver.getDriverId());
        driverDTO.setDriverName(driver.getDriverName());
        driverDTO.setDriverLicense(driver.getDriverLicense());
        driverDTO.setDriverAadharNumber(driver.getDriverAadharNumber());
        driverDTO.setAddress(driver.getAddress());
        driverDTO.setContactNumber(driver.getContactNumber());
        driverDTO.setAlternateContactNumber(driver.getAlternateContactNumber());
        driverDTO.setEmailId(driver.getEmailId());
        driverDTO.setBusId(driver.getBus().getBusId());
        return driverDTO;
    }
}
