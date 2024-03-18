package com.BusBooking.controller;

import com.BusBooking.payload.SubRouteDTO;
import com.BusBooking.service.SubRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subroutes")
public class SubRouteController {

    @Autowired
    private SubRouteService subRouteService;

    //http://localhost:8080/api/subroutes/add
    @PostMapping("/add")
    public ResponseEntity<SubRouteDTO> addSubRoute(@RequestBody SubRouteDTO subRouteDTO) {
        SubRouteDTO addedSubRoute = subRouteService.addSubRoute(subRouteDTO);
        return new ResponseEntity<>(addedSubRoute, HttpStatus.CREATED);
    }


}
