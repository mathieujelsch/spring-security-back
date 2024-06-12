package com.cordernot.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cordernot.entities.Customer;
// import com.cordernot.entities.Like;
import com.cordernot.entities.Publication;
import com.cordernot.repository.CustomerRepository;
// import com.cordernot.repository.LikeRepository;
import com.cordernot.repository.PublicationRepository;

@Service
public class PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // @Autowired
    // private LikeRepository likeRepository; 

    // public Publication toggleLike(Long publicationId, Long customerId) {
    //     Publication publication = publicationRepository.findById(publicationId)
    //             .orElseThrow(() -> new RuntimeException("Publication not found"));

    //     Customer customer = customerRepository.findById(customerId)
    //             .orElseThrow(() -> new RuntimeException("User not found"));

    //     Optional<Like> existingLike = likeRepository.findByPublicationAndUser(publication, customer);

    //     if (existingLike.isPresent()) {
    //         // customer already liked, so we decrement the likes and remove the like entry
    //         publication.setLikes(publication.getLikes() - 1);
    //         likeRepository.delete(existingLike.get());
    //     } else {
    //         // customer has not liked, so we increment the likes and add a like entry
    //         publication.setLikes(publication.getLikes() + 1);
    //         Like like = new Like();
    //         likeRepository.save(like);
    //     }

    //     return publicationRepository.save(publication);
    // }
}
