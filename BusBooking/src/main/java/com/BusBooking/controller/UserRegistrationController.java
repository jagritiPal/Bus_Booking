package com.BusBooking.controller;


import com.BusBooking.entity.UserRegistration;
import com.BusBooking.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/user")
public class UserRegistrationController {

    @Autowired
    private UserRegistrationService userRegistrationService;

    //http://localhost:8080/api/v1/user
    @PostMapping
    public String createUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("profilePicture") MultipartFile profilePicture
            ){

        try {
            UserRegistration userRegistration = new UserRegistration();
            userRegistration.setName(name);
            userRegistration.setEmail(email);
            userRegistration.setPassword(password);
            userRegistration.setProfilePicture(profilePicture.getBytes());
            userRegistrationService.createUser(userRegistration);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "Done....";
    }

    //http://localhost:8080/api/v1/user/1
    @GetMapping("/{id}")
    public ResponseEntity<UserRegistration> getUserById(@PathVariable long id){
        UserRegistration user = userRegistrationService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
