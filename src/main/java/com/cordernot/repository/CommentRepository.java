package com.cordernot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cordernot.entities.Comment;
import com.cordernot.entities.Customer;
import com.cordernot.entities.Publication;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByPublicationAndCustomer(Publication publication, Customer customer);

    void deleteByPublication(Publication publication);

    void deleteByPublicationId(Long publicationId);

    List<Comment> findByPublicationId(Long publicationId);
}