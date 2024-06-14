package com.cordernot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cordernot.entities.Customer;
import com.cordernot.entities.Dislike;
import com.cordernot.entities.Publication;

public interface DislikeRepository extends JpaRepository<Dislike, Long> {
    Optional<Dislike> findByPublicationAndCustomer(Publication publication, Customer customer);

    void deleteByPublication(Publication publication);

    void deleteByPublicationId(Long publicationId);

    List<Dislike> findByPublicationId(Long publicationId);
}
