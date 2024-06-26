package com.cordernot.dto;

import java.util.List;

import com.cordernot.entities.Publication;

import lombok.Data;

@Data
public class PublicationResponse {
    private long id;
    private String content;
    private int likes;
    private int dislikes;
    private List<CommentResponse> comments;
    private long customerId;
  
    public PublicationResponse(Publication publication, List<CommentResponse> comments) {
      this.id = publication.getId();
      this.content = publication.getContent();
      this.likes = publication.getLikes();
      this.dislikes = publication.getDislikes();
      this.comments = comments;
      this.customerId = publication.getCustomer().getId();
    }
  
    // Getters and Setters
  }