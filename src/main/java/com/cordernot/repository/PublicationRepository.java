package com.cordernot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cordernot.entities.Customer;
import com.cordernot.entities.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findByCustomer(Optional<Customer> customer);

}
