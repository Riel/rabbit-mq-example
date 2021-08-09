package com.riel.rabbitmqdemo.models.blogtask;


import java.util.UUID;
import org.springframework.data.annotation.Id;

public class User {

  @Id
  private UUID id;
  private String firstName;
  private String lastName;
  private String eMail;

  public User() {
    id = UUID.randomUUID();
  }

  public User(String firstName, String lastName, String eMail) {
    this();
    this.firstName = firstName;
    this.lastName = lastName;
    this.eMail = eMail;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String geteMail() {
    return eMail;
  }

  public void seteMail(String eMail) {
    this.eMail = eMail;
  }
}
