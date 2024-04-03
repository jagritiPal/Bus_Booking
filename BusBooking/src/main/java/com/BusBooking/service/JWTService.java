package com.BusBooking.service;


import com.BusBooking.entity.UserRegistration;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
public class JWTService {

    @Value("${security.jwt.algorithm-key}")
    private String algorithmKey;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.expiry-time}")
    private int expiryTime;

    private Algorithm algorithm;

    private final static String USER_NAME = "username";

    @PostConstruct
    public void postConstruct() {
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateToken(UserRegistration userRegistration){
       return JWT.create().
                withClaim(USER_NAME, userRegistration.getEmail()).
                withExpiresAt(new Date(System.currentTimeMillis()+expiryTime)).
                withIssuer(issuer).
                sign(algorithm);
    }

    //verify the token and return username if valid
    public String getUsername(String token){
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();

    }
}
