package com.riel.rabbitmqdemo;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.riel.rabbitmqdemo.models.blogtask.BlogPost;
import com.riel.rabbitmqdemo.models.Todo;
import com.riel.rabbitmqdemo.models.blogtask.Comment;
import com.riel.rabbitmqdemo.models.blogtask.User;
import com.riel.rabbitmqdemo.repositories.BlogPostRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RabbitMqDemoApplication implements CommandLineRunner {

  private MongoTemplate todoTemplate;
  private BlogPostRepository blogPostRepository;

  @Autowired
  public RabbitMqDemoApplication(MongoTemplate mongoTemplate, BlogPostRepository blogPostRepository) {
    this.todoTemplate = mongoTemplate;
    this.blogPostRepository = blogPostRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(RabbitMqDemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Collection Exists? " + todoTemplate.collectionExists("todo_collection"));

    //useRabbitTemplate();
    useRabbitRepository();
  }

  //region Using Rabbit Tempplate
  private void useRabbitTemplate() {
    saveTodo();
    uceQueryWithCriteriaClasses();
    useQueryFromJson();
    updateSingleItem();
    updateMultipleItems();
    deleteItem();
  }

  private void saveTodo() {
    Todo newTodo = new Todo("Go home", "Riel");
    todoTemplate.insert(newTodo);
  }

  // Using Query and Criteria classes:
  private void uceQueryWithCriteriaClasses() {
    Criteria criteria = where("title").is("Go home");
    Query query = Query.query(criteria);
    Todo todo = todoTemplate.findOne(query, Todo.class);
    List<Todo> todos = todoTemplate.findAll(Todo.class);
  }

  // Creating a Query instance from a plain JSON string:
  private void useQueryFromJson() {
    BasicQuery query = new BasicQuery("{ ownerName: 'Riel' }");
    List<Todo> todos = todoTemplate.find(query, Todo.class);
  }

  // Update single item (document)
  private void updateSingleItem() {
    Query query = new Query(new Criteria("title").is("Go home"));
    Update update = new Update()
        .set("rating", 7.9)
        .set("areas", Arrays.asList("Novel", "Allegory", "Satire" ));

    UpdateResult result = todoTemplate.updateFirst(query, update, Todo.class);
    System.out.println("Modified documents: " + result.getModifiedCount());

    Todo todo = todoTemplate.findOne(query, Todo.class);
  }

  // Update multiple items (documents)
  private void updateMultipleItems() {
    Query query = query(where("ownerName").is("Riel"));
    Update update = new Update().set("ownerName", "Sanyika");
    UpdateResult result = todoTemplate.updateMulti(query, update, Todo.class);
    System.out.println("Modified documents: " + result.getModifiedCount());

    Todo todo = todoTemplate.findOne(query(where("ownerName").is("Sanyika")), Todo.class);
  }

  // Delete single item (document)
  private void deleteItem() {
    Query query = query(where("ownerName").is("Sanyika"));
    DeleteResult result = todoTemplate.remove(query, Todo.class);
    System.out.println("Deleted documents: " + result.getDeletedCount());

    Todo todo = todoTemplate.findOne(query(where("ownerName").is("Sanyika")), Todo.class);
  }
  //endregion


  //region Using Rabbit Repository
  private void useRabbitRepository() {
    saveBlogPost();
    queryBlogPosts();
    updatePostItems();
  }

  private void saveBlogPost() {
    BlogPost post1 = new BlogPost("Duma", "Well done");
    User user1 = new User("Robert", "Bor", "valami@ez");
    User user2 = new User("Adam", "nagy", "valami@mas");
    Comment comment1 = new Comment("Elso", user1);
    Comment comment2 = new Comment("Masodik", user1);
    Comment comment3 = new Comment("Harmadk", user2);
    BlogPost post2 = new BlogPost("Szép", "Go go go");
    post2.addComment(comment1);
    post2.addComment(comment2);
    post2.addComment(comment3);
    blogPostRepository.save(post1);
    blogPostRepository.save(post2);
  }

  private void queryBlogPosts() {
    List<BlogPost> posts = blogPostRepository.findAll();
    BlogPost post = blogPostRepository.findById(posts.get(0).getId()).get();
  }

  private void updatePostItems() {
    List<BlogPost> posts = blogPostRepository.findAll();
    BlogPost bp = posts.get(0);
    bp.setContent("New content");
    blogPostRepository.save(bp);
  }
}