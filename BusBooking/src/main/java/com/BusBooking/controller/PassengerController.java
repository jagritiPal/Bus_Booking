package com.BusBooking.controller;

import com.BusBooking.entity.Passenger;
import com.BusBooking.exception.ResourceNotFoundException;
import com.BusBooking.payload.PassengerDTO;
import com.BusBooking.repository.PassengerRepository;
import com.BusBooking.service.PassengerService;
import com.BusBooking.util.ExcelGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {



    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private ExcelGeneratorService excelGeneratorService;

    @Autowired
    private PassengerService passengerService;

    //http://localhost:8080/api/passengers/add
    @PostMapping("add")
    public ResponseEntity<String> savePassenger(@RequestBody Passenger passenger,
                                                @RequestParam long busId,
                                                @RequestParam long routeId,
                                                @RequestParam long subRouteId) {
        try {
            passengerService.savePassengerDetails(passenger, busId, routeId, subRouteId);
            return new ResponseEntity<>("Passenger details saved successfully", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    //http://localhost:8080/api/passengers/excel
    @GetMapping("/excel")
    public ResponseEntity<byte[]> generateExcel() {
        try {
            // Retrieve passenger data from your repository
            List<Passenger> passengers = retrievePassengerData();

            // Generate Excel file
            byte[] excelBytes = excelGeneratorService.generateExcel(passengers);

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            headers.setContentDispositionFormData("attachment", "passenger_data.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Method to retrieve passenger data from repository (you need to implement this)
    private List<Passenger> retrievePassengerData() {
        // Implement data retrieval logic from your repository and return the list of passengers
        return passengerRepository.findAll();
    }

}
