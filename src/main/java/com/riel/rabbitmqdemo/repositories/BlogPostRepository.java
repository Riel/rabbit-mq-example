package com.riel.rabbitmqdemo.repositories;

import com.riel.rabbitmqdemo.models.blogtask.BlogPost;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

// No need gor @Repository
public interface BlogPostRepository extends MongoRepository<BlogPost, UUID> {
}
