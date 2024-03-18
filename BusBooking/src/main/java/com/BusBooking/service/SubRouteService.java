package com.BusBooking.service;

import com.BusBooking.entity.Bus;
import com.BusBooking.entity.Route;
import com.BusBooking.entity.SubRoute;
import com.BusBooking.payload.SubRouteDTO;
import com.BusBooking.repository.RouteRepository;
import com.BusBooking.repository.SubRouteRepository;
import com.BusBooking.repository.BusRepository; // Import BusRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubRouteService {

    @Autowired
    private SubRouteRepository subRouteRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private BusRepository busRepository; // Autowire BusRepository

    public SubRouteDTO addSubRoute(SubRouteDTO subRouteDTO) {
        // Check if the provided routeId exists in the Route table
        if (!routeRepository.existsById(subRouteDTO.getRouteId())) {
            throw new IllegalArgumentException("Route with ID " + subRouteDTO.getRouteId() + " does not exist");
        }

        // Check if the provided busId exists in the Bus table
        if (!busRepository.existsById(subRouteDTO.getBusId())) {
            throw new IllegalArgumentException("Bus with ID " + subRouteDTO.getBusId() + " does not exist");
        }

        // Convert DTO to entity
        SubRoute subRoute = mapToEntity(subRouteDTO);

        // Save the subroute entity
        SubRoute savedSubRoute = subRouteRepository.save(subRoute);

        // Convert the saved entity back to DTO and return
        return mapToDto(savedSubRoute);
    }

    private SubRoute mapToEntity(SubRouteDTO subRouteDTO) {
        SubRoute subRoute = new SubRoute();
        subRoute.setSubRouteId(subRouteDTO.getSubRouteId());
        subRoute.setFromLocation(subRouteDTO.getFromLocation());
        subRoute.setToLocation(subRouteDTO.getToLocation());
        subRoute.setFromDate(subRouteDTO.getFromDate());
        subRoute.setToDate(subRouteDTO.getToDate());
        subRoute.setTotalDuration(subRouteDTO.getTotalDuration());
        subRoute.setFromTime(subRouteDTO.getFromTime());
        subRoute.setToTime(subRouteDTO.getToTime());

        // Set Route
        Route route = routeRepository.findById(subRouteDTO.getRouteId())
                .orElseThrow(() -> new IllegalArgumentException("Route with ID " + subRouteDTO.getRouteId() + " does not exist"));
        subRoute.setRoute(route);

        // Set Bus
        Bus bus = busRepository.findById(subRouteDTO.getBusId())
                .orElseThrow(() -> new IllegalArgumentException("Bus with ID " + subRouteDTO.getBusId() + " does not exist"));
        subRoute.setBus(bus);

        return subRoute;
    }

    private SubRouteDTO mapToDto(SubRoute subRoute) {
        SubRouteDTO subRouteDTO = new SubRouteDTO();
        subRouteDTO.setSubRouteId(subRoute.getSubRouteId());
        subRouteDTO.setFromLocation(subRoute.getFromLocation());
        subRouteDTO.setToLocation(subRoute.getToLocation());
        subRouteDTO.setFromDate(subRoute.getFromDate());
        subRouteDTO.setToDate(subRoute.getToDate());
        subRouteDTO.setTotalDuration(subRoute.getTotalDuration());
        subRouteDTO.setFromTime(subRoute.getFromTime());
        subRouteDTO.setToTime(subRoute.getToTime());
        subRouteDTO.setRouteId(subRoute.getRoute().getRouteId()); //Set routeId
        subRouteDTO.setBusId(subRoute.getBus().getBusId()); // Set busId
        return subRouteDTO;
    }
}
