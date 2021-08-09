package com.riel.rabbitmqdemo.models.blogtask;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document("blog_post_doc")
public class BlogPost {

  @Id
  private UUID id;
  private String title;
  private String content;
  private LocalDate publicationDate;
  private List<Comment> comments;


  public BlogPost() {
    comments = new ArrayList<>();
    id = UUID.randomUUID();
    publicationDate = LocalDate.now();
  }

  public BlogPost(String title, String content) {
    this();
    this.title = title;
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public LocalDate getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(LocalDate publicationDate) {
    this.publicationDate = publicationDate;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public UUID getId() {
    return id;
  }

  public void addComment(Comment comment) {
    this.comments.add(comment);
  }
}
