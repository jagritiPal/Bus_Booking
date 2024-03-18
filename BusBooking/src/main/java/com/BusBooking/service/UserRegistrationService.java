package com.BusBooking.service;

import com.BusBooking.entity.UserRegistration;
import com.BusBooking.exception.ResourceNotFoundException;
import com.BusBooking.payload.UserRegistrationDto;
import com.BusBooking.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    public UserRegistrationDto createUser(UserRegistration userRegistration){
        userRegistrationRepository.save(userRegistration);
        return null;
    }

    public UserRegistration getUserById(long id) {
        return userRegistrationRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User is not found with this id: "  +id));
    }
}
