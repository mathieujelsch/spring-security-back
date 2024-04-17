package com.cordernot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cordernot.dto.PublicationRequest;
import com.cordernot.entities.Customer;
import com.cordernot.entities.Publication;
import com.cordernot.repository.CustomerRepository;
import com.cordernot.repository.PublicationRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/publications")
public class PublicationController {

  private PublicationRepository publicationRepository;

  private CustomerRepository customerRepository;

  public PublicationController(PublicationRepository publicationRepository, CustomerRepository customerRepository) {
    this.publicationRepository = publicationRepository;
    this.customerRepository = customerRepository;
  }

  @GetMapping
  public ResponseEntity<List<Publication>> getAllPersons() {
    List<Publication> publications = publicationRepository.findAll();
    return ResponseEntity.ok(publications);
  }


  @PostMapping
  public ResponseEntity<Publication> createPublication(@RequestBody PublicationRequest publicationRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String email = authentication.getName(); // getName ressort l'email ici
    Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);

    if (!optionalCustomer.isPresent()) {
      // Handle case where customer is not found
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    Customer loggedInCustomer = optionalCustomer.get();

    Publication publication = new Publication();
    BeanUtils.copyProperties(publicationRequest, publication);
    publication.setCustomer(loggedInCustomer);

    publicationRepository.save(publication);

    return ResponseEntity.ok(publication); //test
  }
}
