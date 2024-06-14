package com.cordernot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cordernot.dto.LoginRequest;
import com.cordernot.dto.LoginResponse;
import com.cordernot.entities.CustomUserDetails;
import com.cordernot.services.jwt.CustomerServiceImpl;
import com.cordernot.utils.JwtUtil;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login")
public class LoginController {

  private final AuthenticationManager authenticationManager;

  private final CustomerServiceImpl customerService;

  private final JwtUtil jwtUtil;

  @Autowired
  public LoginController(AuthenticationManager authenticationManager, CustomerServiceImpl customerService, JwtUtil jwtUtil) {
    this.authenticationManager = authenticationManager;
    this.customerService = customerService;
    this.jwtUtil = jwtUtil;
  }


  @PostMapping
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws IOException, java.io.IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password.");
        } catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Customer is not activated");
            return null;
        }
        final UserDetails userDetails = customerService.loadUserByUsername(loginRequest.getEmail());
        if (userDetails instanceof CustomUserDetails) {
        CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), customUserDetails.getCustomerId());
        return new LoginResponse(jwt);
    } else {
        throw new UsernameNotFoundException("User details are not of expected type");
    }
    }
}
