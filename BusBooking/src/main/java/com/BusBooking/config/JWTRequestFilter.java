package com.BusBooking.config;

import com.BusBooking.entity.UserRegistration;
import com.BusBooking.repository.UserRegistrationRepository;
import com.BusBooking.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if(tokenHeader != null && tokenHeader.startsWith("Bearer ")){
            String token = tokenHeader.substring(7);
            String username = jwtService.getUsername(token);
            Optional<UserRegistration> byEmail = userRegistrationRepository.findByEmail(username);
            if (byEmail.isPresent()){
                UserRegistration userRegistration = byEmail.get();

                //for server to understand who is the current user
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userRegistration, null, new ArrayList<>());
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    }

}
