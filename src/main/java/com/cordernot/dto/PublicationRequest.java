package com.cordernot.dto;

import lombok.Data;

@Data
public class PublicationRequest {

  private String content;

  private int likes;

  private int dislikes;

  private String comment;
}
