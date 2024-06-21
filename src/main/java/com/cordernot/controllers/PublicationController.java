package com.cordernot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cordernot.dto.PublicationRequest;
import com.cordernot.entities.Customer;
import com.cordernot.entities.Dislike;
import com.cordernot.entities.Like;
import com.cordernot.entities.Comment;
import com.cordernot.entities.Publication;
import com.cordernot.repository.CommentRepository;
import com.cordernot.repository.CustomerRepository;
import com.cordernot.repository.DislikeRepository;
import com.cordernot.repository.LikeRepository;
import com.cordernot.repository.PublicationRepository;
import com.cordernot.services.PublicationService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/publications")
public class PublicationController {

  private PublicationRepository publicationRepository;

  private CustomerRepository customerRepository;

  @Autowired
  private PublicationService publicationService;

  @Autowired
  private LikeRepository likeRepository;

  @Autowired
  private DislikeRepository dislikeRepository;

  @Autowired
  private CommentRepository commentRepository;

  public PublicationController(PublicationRepository publicationRepository, CustomerRepository customerRepository) {
    this.publicationRepository = publicationRepository;
    this.customerRepository = customerRepository;
  }

  @GetMapping
  public ResponseEntity<List<Publication>> getAllPersons() {
    List<Publication> publications = publicationRepository.findAll();
    return ResponseEntity.ok(publications);
  }

  @GetMapping("messages")
  public ResponseEntity<List<Publication>> getUserPub() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String email = authentication.getName(); // getName ressort l'email ici
    Optional<Customer> customer= customerRepository.findByEmail(email);

    List<Publication> publications = publicationRepository.findByCustomer(customer);
    return ResponseEntity.ok(publications);
  }

  @PostMapping("/{publicationId}/like")
  public ResponseEntity<Publication> toggleLike(@PathVariable Long publicationId, @RequestParam Long customerId) {
      Publication updatedPublication = publicationService.toggleLike(publicationId, customerId);
      return ResponseEntity.ok(updatedPublication);
  }
  
  @PostMapping("/{publicationId}/dislike")
  public ResponseEntity<Publication> toggleDislike(@PathVariable Long publicationId, @RequestParam Long customerId) {
      Publication updatedPublication = publicationService.toggleDislike(publicationId, customerId);
      return ResponseEntity.ok(updatedPublication);
  }

  @PostMapping("/{publicationId}/comment")
  public ResponseEntity<Publication> commentPub(@PathVariable Long publicationId, @RequestParam Long customerId, @RequestBody PublicationRequest publicationRequest) {
    Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

    Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Comment comment = Comment.builder()
      .customer(customer)
      .publication(publication)
      .comment(publicationRequest.getComment()) // setting the comment text
      .build();
    
    commentRepository.save(comment);

    // Ajouter le commentaire à la publication
    publication.getComments().add(comment);

    return ResponseEntity.ok(publication);
  }

  @GetMapping("/{publicationId}/comments")
  public ResponseEntity<List<Comment>> getComment(@PathVariable Long publicationId) {
    Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

            // List<Comment> findByPublicationId(Long publicationId);
    List<Comment> comments = commentRepository.findByPublicationId(publicationId);        

    return ResponseEntity.ok(comments);
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

  @DeleteMapping("/{publicationId}")
  public ResponseEntity<Void> deletePublication(@PathVariable Long publicationId) {
    Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

     // Supprimer les likes associés à la publication
     List<Like> likes = likeRepository.findByPublicationId(publicationId);
     for (Like like : likes) {
         likeRepository.delete(like);
     }
 
     // Supprimer les dislikes associés à la publication
     List<Dislike> dislikes = dislikeRepository.findByPublicationId(publicationId);
     for (Dislike dislike : dislikes) {
         dislikeRepository.delete(dislike);
     }

     List<Comment> comments = commentRepository.findByPublicationId(publicationId);
     for (Comment comment : comments) {
         commentRepository.delete(comment);
     }
                        

    publicationRepository.delete(publication);
    return ResponseEntity.noContent().build();
  }
  

  @PutMapping("/{publicationId}")
  public ResponseEntity<Publication> updatePublication(@PathVariable Long publicationId, @RequestBody PublicationRequest publicationRequest) {
    Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

    publication.setContent(publicationRequest.getContent());

    // Enregistrer la publication mise à jour
    publicationRepository.save(publication);
    return ResponseEntity.ok(publication);
  }
}
