package com.BusBooking.controller;

import com.BusBooking.payload.RouteDTO;
import com.BusBooking.payload.SubRouteDTO;
import com.BusBooking.service.RouteService;
import com.BusBooking.service.SubRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;

    //http://localhost:8080/api/routes/add
    @PostMapping("/add")
    public ResponseEntity<RouteDTO> addRoute(@RequestBody RouteDTO routeDTO) {
        RouteDTO addedRoute = routeService.addRoute(routeDTO);
        return new ResponseEntity<>(addedRoute, HttpStatus.CREATED);
    }


}
