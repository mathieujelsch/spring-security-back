package com.cordernot.services;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cordernot.dto.SignupRequest;
import com.cordernot.entities.Customer;
import com.cordernot.repository.CustomerRepository;

@Service
public class AuthServiceImpl implements AuthService {

  private final CustomerRepository customerRepository;

  private final PasswordEncoder passwordEncoder;

  public AuthServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
    this.customerRepository = customerRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Customer createCustomer(SignupRequest signupRequest) {
    //Check if customer already exist
    if (customerRepository.existsByEmail(signupRequest.getEmail())) {
        return null;
    }

    Customer customer = new Customer();
    BeanUtils.copyProperties(signupRequest,customer);

    //Hash the password before saving
    String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
    customer.setPassword(hashPassword);
    Customer createdCustomer = customerRepository.save(customer);
    customer.setId(createdCustomer.getId());
    return customer;
  }
}
