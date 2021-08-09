package com.riel.rabbitmqdemo.models.blogtask;

import java.util.UUID;
import org.springframework.data.annotation.Id;

public class Comment {

  @Id
  private UUID id;
  private String message;
  private User user;


  public Comment() {
    id = UUID.randomUUID();
  }

  public Comment(String message, User user) {
    this();
    this.message = message;
    this.user = user;
  }
}
