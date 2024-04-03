package com.BusBooking.controller;


import com.BusBooking.entity.UserRegistration;
import com.BusBooking.payload.JWTResponse;
import com.BusBooking.payload.LoginDTO;
import com.BusBooking.payload.UserRegistrationDto;
import com.BusBooking.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    //http://localhost:8080/api/v1/user/addUser
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            userRegistrationService.addUser(userRegistrationDto);
            return new ResponseEntity<>("User added successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to create user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //http://localhost:8080/api/v1/user/1
    @GetMapping("/{id}")
    public ResponseEntity<UserRegistration> getUserById(@PathVariable long id){
        UserRegistration user = userRegistrationService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String jwtToken = userRegistrationService.verifyLogin(loginDTO);
        if (jwtToken != null) {
            JWTResponse jwtResponse = new JWTResponse();
            jwtResponse.setToken(jwtToken);
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
    }


}
