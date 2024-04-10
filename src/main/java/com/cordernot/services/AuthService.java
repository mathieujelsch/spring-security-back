package com.cordernot.services;

import com.cordernot.dto.SignupRequest;
import com.cordernot.entities.Customer;

public interface AuthService {

  Customer createCustomer(SignupRequest signupRequest);
}
