package com.BusBooking.service;

import com.BusBooking.entity.UserRegistration;
import com.BusBooking.exception.ResourceNotFoundException;
import com.BusBooking.payload.LoginDTO;
import com.BusBooking.payload.UserRegistrationDto;
import com.BusBooking.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRegistrationService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Autowired
    private JWTService jwtService;


    public UserRegistrationDto addUser(UserRegistrationDto userRegistrationDto) {

        // Convert DTO to entity
        UserRegistration userRegistration = mapToEntity(userRegistrationDto);

        // Save the user entity
        UserRegistration savedUser = userRegistrationRepository.save(userRegistration);

        // Convert the saved entity back to DTO and return
        return mapToDto(savedUser);
    }

    private UserRegistration mapToEntity(UserRegistrationDto userDto) {
        UserRegistration userRegistration = new UserRegistration();
        userRegistration.setId(userDto.getId());
        userRegistration.setName(userDto.getName());
        userRegistration.setEmail(userDto.getEmail());
        userRegistration.setPassword(userDto.getPassword());
        userRegistration.setUserRole(userDto.getUserRole());
        return userRegistration;
    }


    private UserRegistrationDto mapToDto(UserRegistration userRegistration) {
        UserRegistrationDto userDto = new UserRegistrationDto();
        userDto.setId(userRegistration.getId());
        userDto.setName(userRegistration.getName());
        userDto.setEmail(userRegistration.getEmail());
        userDto.setPassword(userRegistration.getPassword());
        userDto.setUserRole(userRegistration.getUserRole());
        return userDto;
    }


    public UserRegistration getUserById(long id) {
        return userRegistrationRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User is not found with this id: "  +id));
    }


    public String verifyLogin(LoginDTO loginDTO) {
        Optional<UserRegistration> byEmail = userRegistrationRepository.findByEmail(loginDTO.getEmail());
        if (byEmail.isPresent()){
            UserRegistration userRegistration = byEmail.get();
            if(BCrypt.checkpw(loginDTO.getPassword(), userRegistration.getPassword())){
               return jwtService.generateToken(userRegistration);
            }
        }
        return null;
    }


}
