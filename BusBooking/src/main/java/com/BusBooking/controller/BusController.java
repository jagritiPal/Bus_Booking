package com.BusBooking.controller;

import com.BusBooking.exception.ResourceNotFoundException;
import com.BusBooking.payload.BusDTO;
import com.BusBooking.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusService busService;

    //http://localhost:8080/api/buses/add
    @PostMapping("/add")
    public ResponseEntity<BusDTO> addBus(@RequestBody BusDTO busDTO) {
        BusDTO addedBus = busService.addBus(busDTO);
        return new ResponseEntity<>(addedBus, HttpStatus.CREATED);
    }


    //http://localhost:8080/api/buses/getBusesAndRoutesOrSubroutes?fromLocation=City%20A&toLocation=City%20B&fromDate=2024-03-07
    @GetMapping("/getBusesAndRoutesOrSubroutes")
    public ResponseEntity<List<Object>> findBusesAndRoutesOrSubroutesByLocationsAndDate(
            @RequestParam("fromLocation") String fromLocation,
            @RequestParam("toLocation") String toLocation,
            @RequestParam("fromDate") String fromDate) {

        List<Object> result = busService.findBusesAndRoutesOrSubroutesByLocationsAndDate(fromLocation, toLocation, fromDate);

        if (result.isEmpty()) {
            // No routes or subroutes found
            List<Object> responseBody = new ArrayList<>();
            responseBody.add("No routes or subroutes found for the provided locations and date");
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        } else {
            // Routes or subroutes found
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }




}



//    @GetMapping("/getBusesAndRoutesOrSubroutes")
//    public ResponseEntity<List<Object>> findBusesAndRoutesOrSubroutesByLocationsAndDate(
//            @RequestParam("fromLocation") String fromLocation,
//            @RequestParam("toLocation") String toLocation,
//            @RequestParam("fromDate") String fromDate) {
//
//        try {
//            List<Object> result = busService.findBusesAndRoutesOrSubroutesByLocationsAndDate(fromLocation, toLocation, fromDate);
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        } catch (ResourceNotFoundException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


//    //http://localhost:8080/api/buses/getBusesAndRoute?fromLocation=City%20A&toLocation=City%20B&fromDate=2024-03-07
//    @GetMapping("/getBusesAndRoute")
//    public ResponseEntity<List<SearchListOfBusesDTO>> findBusesByLocationsAndDate(
//            @RequestParam("fromLocation") String fromLocation,
//            @RequestParam("toLocation") String toLocation,
//            @RequestParam("fromDate") String fromDate) {
//        List<SearchListOfBusesDTO> busesByLocationsAndDate = busService.findBusesByLocationsAndDate(fromLocation, toLocation, fromDate);
//        return new ResponseEntity<>(busesByLocationsAndDate, HttpStatus.OK);
//    }
//
//    //http://localhost:8080/api/buses/getBusesAndSubRoute?fromLocation=City%20J&toLocation=City%20S&fromDate=2024-03-07
//    @GetMapping("/getBusesAndSubRoute")
//    public ResponseEntity<List<BusAndSubrouteDTO>> findBusesAndSubroutesByLocationsAndDate(
//            @RequestParam("fromLocation") String fromLocation,
//            @RequestParam("toLocation") String toLocation,
//            @RequestParam("fromDate") String fromDate) {
//        List<BusAndSubrouteDTO> busesAndSubroutesByLocationsAndDate = busService.findBusesAndSubroutesByLocationsAndDate(fromLocation, toLocation, fromDate);
//        return new ResponseEntity<>(busesAndSubroutesByLocationsAndDate, HttpStatus.OK);
//    }

