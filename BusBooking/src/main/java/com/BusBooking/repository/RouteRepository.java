package com.BusBooking.repository;

import com.BusBooking.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    List<Route> findByFromLocationAndToLocationAndFromDate(String fromLocation, String toLocation, String fromDate);

}
