package com.cordernot.dto;

import com.cordernot.entities.Comment;

import lombok.Data;
import lombok.Getter;

@Data
public class CommentResponse {
    private long id;
    private String comment;
    private int likes;
    private long customerId;
  
    public CommentResponse(Comment comment) {
      this.id = comment.getId();
      this.comment = comment.getComment();
      this.likes = comment.getLikes();
      this.customerId = comment.getCustomer().getId();
    }
  
    // Getters and Setters
  }
