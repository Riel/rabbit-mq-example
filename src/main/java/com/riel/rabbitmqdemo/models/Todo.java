package com.riel.rabbitmqdemo.models;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Define collection name here
// No need for @Entity
@Document("todo_collection")
public class Todo {

  @Id
  private String id;
  private String title;
  private String ownerName;
  private LocalDate created;

  private Double rating;
  private List<String> areas;


  public Todo(String title, String ownerName) {
    this.title = title;
    this.ownerName = ownerName;
  }

  public Todo() {
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public void setAreas(List<String> areas) {
    this.areas = areas;
  }

  @Override
  public String toString() {
    return "Todo{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", ownerName='" + ownerName + '\'' +
        '}';
  }
}
