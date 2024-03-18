package com.BusBooking.service;

import com.BusBooking.entity.Bus;
import com.BusBooking.entity.Route;
import com.BusBooking.entity.SubRoute;
import com.BusBooking.exception.ResourceNotFoundException;
import com.BusBooking.payload.BusAndRouteDTO;
import com.BusBooking.payload.BusAndSubrouteDTO;
import com.BusBooking.payload.BusDTO;
import com.BusBooking.repository.BusRepository;
import com.BusBooking.repository.RouteRepository;
import com.BusBooking.repository.SubRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private SubRouteRepository subRouteRepository;


    //Add buses
    public BusDTO addBus(BusDTO busDTO) {
        // Convert DTO to entity
        Bus bus = mapToEntity(busDTO);

        // Save the bus entity
        Bus savedBus = busRepository.save(bus);

        // Convert the saved entity back to DTO and return
        return mapToDto(savedBus);
    }




    //Buses Routes or Subroutes
    public List<Object> findBusesAndRoutesOrSubroutesByLocationsAndDate(String fromLocation, String toLocation, String fromDate) {
        // Find routes based on the provided locations and date
        List<Route> routes = routeRepository.findByFromLocationAndToLocationAndFromDate(fromLocation, toLocation, fromDate);

        // Find subroutes based on the provided locations and date if no routes are found
        if (routes.isEmpty()) {
            List<SubRoute> subRoutes = subRouteRepository.findByFromLocationAndToLocationAndFromDate(fromLocation, toLocation, fromDate);
            if (!subRoutes.isEmpty()) {
                // Create a list to store BusAndSubrouteDTO objects
                List<BusAndSubrouteDTO> busAndSubrouteDTOList = new ArrayList<>();
                // Iterate over the subroutes and map bus and subroute details to BusAndSubrouteDTO
                for (SubRoute subRoute : subRoutes) {
                    Bus bus = subRoute.getBus();
                    if (bus != null) {
                        BusAndSubrouteDTO busAndSubrouteDTO = mapToBusAndSubrouteDTO(bus, subRoute);
                        busAndSubrouteDTOList.add(busAndSubrouteDTO);
                    }
                }
                return new ArrayList<>(busAndSubrouteDTOList);
            } else {
                throw new ResourceNotFoundException("No routes or subroutes found for the provided locations and date");
            }
        } else {
            // Create a list to store SearchListOfBusesDTO objects
            List<BusAndRouteDTO> busRouteDetailsDTOList = new ArrayList<>();
            // Iterate over the routes and map bus and route details to SearchListOfBusesDTO
            for (Route route : routes) {
                Bus bus = route.getBus();
                if (bus != null) {
                    BusAndRouteDTO busRouteDetailsDTO = mapToSearchListOfBuses(bus, route);
                    busRouteDetailsDTOList.add(busRouteDetailsDTO);
                }
            }
            return new ArrayList<>(busRouteDetailsDTOList);
        }
    }



    private Bus mapToEntity(BusDTO busDTO) {
        Bus bus = new Bus();
        bus.setBusId(busDTO.getBusId());
        bus.setBusType(busDTO.getBusType());
        bus.setBusNumber(busDTO.getBusNumber());
        bus.setPrice(busDTO.getPrice());
        bus.setTotalSeats(busDTO.getTotalSeats());
        bus.setAvailableSeats(busDTO.getAvailableSeats());
        return bus;
    }

    private BusDTO mapToDto(Bus bus) {
        BusDTO busDTO = new BusDTO();
        busDTO.setBusId(bus.getBusId());
        busDTO.setBusNumber(bus.getBusNumber());
        busDTO.setBusType(bus.getBusType());
        busDTO.setPrice(bus.getPrice());
        busDTO.setTotalSeats(bus.getTotalSeats());
        busDTO.setAvailableSeats(bus.getAvailableSeats());
        return busDTO;
    }


    private BusAndRouteDTO mapToSearchListOfBuses(Bus bus, Route route) {
        BusAndRouteDTO dto = new BusAndRouteDTO();
        dto.setBusId(bus.getBusId());
        dto.setBusNumber(bus.getBusNumber());
        dto.setBusType(bus.getBusType());
        dto.setPrice(bus.getPrice());
        dto.setTotalSeats(bus.getTotalSeats());
        dto.setAvailableSeats(bus.getAvailableSeats());
        dto.setRouteId(route.getRouteId());
        dto.setFromLocation(route.getFromLocation());
        dto.setToLocation(route.getToLocation());
        dto.setFromDate(route.getFromDate());
        dto.setToDate(route.getToDate());
        dto.setTotalDuration(route.getTotalDuration());
        dto.setFromTime(route.getFromTime());
        dto.setToTime(route.getToTime());
        return dto;
    }

    private BusAndSubrouteDTO mapToBusAndSubrouteDTO(Bus bus, SubRoute subRoute) {
        BusAndSubrouteDTO dto = new BusAndSubrouteDTO();
        dto.setBusId(bus.getBusId());
        dto.setBusNumber(bus.getBusNumber());
        dto.setBusType(bus.getBusType());
        dto.setPrice(bus.getPrice());
        dto.setTotalSeats(bus.getTotalSeats());
        dto.setAvailableSeats(bus.getAvailableSeats());
        dto.setSubRouteId(subRoute.getSubRouteId());
        dto.setFromLocation(subRoute.getFromLocation());
        dto.setToLocation(subRoute.getToLocation());
        dto.setFromDate(subRoute.getFromDate());
        dto.setToDate(subRoute.getToDate());
        dto.setTotalDuration(subRoute.getTotalDuration());
        dto.setFromTime(subRoute.getFromTime());
        dto.setToTime(subRoute.getToTime());
        return dto;
    }


    //
//    //Buses and Routes
//    public List<SearchListOfBusesDTO> findBusesByLocationsAndDate(String fromLocation, String toLocation, String fromDate) {
//        // Find routes based on the provided locations and date
//        List<Route> routes = routeRepository.findByFromLocationAndToLocationAndFromDate(fromLocation, toLocation, fromDate);
//
//        // Create a list to store BusRouteDetailsDTO objects
//        List<SearchListOfBusesDTO> busRouteDetailsDTOList = new ArrayList<>();
//
//        // Iterate over the routes and map bus and route details to SearchListOfBusesDTO
//        for (Route route : routes) {
//            Bus bus = route.getBus();
//            if (bus != null) {
//                SearchListOfBusesDTO busRouteDetailsDTO = mapToSearchListOfBuses(bus, route);
//                busRouteDetailsDTOList.add(busRouteDetailsDTO);
//            }
//        }
//
//        return busRouteDetailsDTOList;
//    }
//
//
//
//
//    //Buses and Subroutes
//    public List<BusAndSubrouteDTO> findBusesAndSubroutesByLocationsAndDate(String fromLocation, String toLocation, String fromDate) {
//        // Find subroutes based on the provided locations and date
//        List<SubRoute> subRoutes = subRouteRepository.findByFromLocationAndToLocationAndFromDate(fromLocation, toLocation, fromDate);
//
//        // Create a list to store BusAndSubrouteDTO objects
//        List<BusAndSubrouteDTO> busAndSubrouteDTOList = new ArrayList<>();
//
//        // Iterate over the subroutes and map bus and subroute details to BusAndSubrouteDTO
//        for (SubRoute subRoute : subRoutes) {
//            Bus bus = subRoute.getBus();
//            if (bus != null) {
//                BusAndSubrouteDTO busAndSubrouteDTO = mapToBusAndSubrouteDTO(bus, subRoute);
//                busAndSubrouteDTOList.add(busAndSubrouteDTO);
//            }
//        }
//
//        return busAndSubrouteDTOList;
//    }



}
