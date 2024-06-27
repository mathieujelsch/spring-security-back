package com.cordernot.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cordernot.entities.Customer;
import com.cordernot.entities.Like;
import com.cordernot.entities.LikeComment;
import com.cordernot.dto.PublicationRequest;
import com.cordernot.entities.Comment;
import com.cordernot.repository.CommentRepository;
import com.cordernot.repository.CustomerRepository;
import com.cordernot.repository.LikeCommentRepository;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @PostMapping("/{commentId}/like")
    public ResponseEntity<Comment> toggleLikeComment(@PathVariable Long commentId, @RequestParam Long customerId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Optional<LikeComment> existingLike = likeCommentRepository.findByCommentAndCustomer(comment, customer);

        if (existingLike.isPresent()) {
            // customer already liked, so we decrement the likes and remove the like entry
            comment.setLikes(comment.getLikes() - 1);
            likeCommentRepository.delete(existingLike.get());
        } else {
            // customer has not liked, so we increment the likes and add a like entry
            comment.setLikes(comment.getLikes() + 1);
            LikeComment like = LikeComment.builder().customer(customer).comment(comment).build();
            likeCommentRepository.save(like);
        }

        commentRepository.save(comment);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));

        List<LikeComment> likes = likeCommentRepository.findByCommentId(commentId);
        for (LikeComment like : likes) {
            likeCommentRepository.delete(like);
        }

        commentRepository.delete(comment);
        return ResponseEntity.noContent().build();
    }    
    
    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody PublicationRequest publicationRequest) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Publication not found"));


        comment.setComment(publicationRequest.getComment());

        commentRepository.save(comment);
        return ResponseEntity.ok(comment);
    }
}
