package com.BusBooking.repository;

import com.BusBooking.entity.Route;
import com.BusBooking.entity.SubRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubRouteRepository extends JpaRepository <SubRoute, Long> {

    List<SubRoute> findByFromLocationAndToLocationAndFromDate(String fromLocation, String toLocation, String fromDate);

}