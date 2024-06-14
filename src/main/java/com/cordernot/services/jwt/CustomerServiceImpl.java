package com.cordernot.services.jwt;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cordernot.entities.CustomUserDetails;
import com.cordernot.entities.Customer;
import com.cordernot.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements UserDetailsService {

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerServiceImpl(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(
                customer.getEmail(),
                customer.getPassword(),
                String.valueOf(customer.getId()), // Convertir l'ID en String si nécessaire
                // Ajoutez les rôles/authorités si nécessaire
                // customer.getRoles()
                Collections.emptyList() // Pour cet exemple, nous retournons une liste vide d'authorités
        );
    }
}
