package com.cordernot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cordernot.entities.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
