package com.BusBooking.service;

import com.BusBooking.entity.Bus;
import com.BusBooking.entity.Route;
import com.BusBooking.payload.RouteDTO;
import com.BusBooking.repository.BusRepository;
import com.BusBooking.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusRepository busRepository;

    public RouteDTO addRoute(RouteDTO routeDTO) {
        // Check if the provided busId exists in the Bus table
        if (!busRepository.existsById(routeDTO.getBusId())) {
            throw new IllegalArgumentException("Bus with ID " + routeDTO.getBusId() + " does not exist");
        }

        // Convert DTO to entity
        Route route = mapToEntity(routeDTO);

        // Save the route entity
        Route savedRoute = routeRepository.save(route);

        // Convert the saved entity back to DTO and return
        return mapToDto(savedRoute);
    }

    private Route mapToEntity(RouteDTO routeDTO) {
        Route route = new Route();
        route.setRouteId(routeDTO.getRouteId());
        route.setFromLocation(routeDTO.getFromLocation());
        route.setToLocation(routeDTO.getToLocation());
        route.setFromDate(routeDTO.getFromDate());
        route.setToDate(routeDTO.getToDate());
        route.setTotalDuration(routeDTO.getTotalDuration());
        route.setFromTime(routeDTO.getFromTime());
        route.setToTime(routeDTO.getToTime());

        // Set the bus
        Bus bus = busRepository.findById(routeDTO.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Bus with ID " + routeDTO.getBusId() + " does not present"));
        route.setBus(bus);
        return route;
    }


    private RouteDTO mapToDto(Route route) {
        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setRouteId(route.getRouteId());
        routeDTO.setFromLocation(route.getFromLocation());
        routeDTO.setToLocation(route.getToLocation());
        routeDTO.setFromDate(route.getFromDate());
        routeDTO.setToDate(route.getToDate());
        routeDTO.setTotalDuration(route.getTotalDuration());
        routeDTO.setFromTime(route.getFromTime());
        routeDTO.setToTime(route.getToTime());
        routeDTO.setBusId(route.getBus().getBusId());
        return routeDTO;
    }
}
