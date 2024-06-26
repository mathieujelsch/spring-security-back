package com.cordernot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cordernot.entities.Customer;
import com.cordernot.entities.Like;
import com.cordernot.entities.Publication;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPublicationAndCustomer(Publication publication, Customer customer);

    void deleteByPublication(Publication publication);

    void deleteByPublicationId(Long publicationId);

    List<Like> findByPublicationId(Long publicationId);
}
