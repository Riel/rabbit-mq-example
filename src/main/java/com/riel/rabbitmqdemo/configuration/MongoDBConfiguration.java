package com.riel.rabbitmqdemo.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import java.util.UUID;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;


// https://www.codementor.io/@prasadsaya/access-mongodb-database-from-a-spring-boot-application-17nwi5shuc
// By default, a MongoDB client connects to a database named "test" on the localhost and port 27017
@Configuration
public class MongoDBConfiguration {

  // Represents a pool of connections to the database
  public MongoClient mongoClient() {
    //return MongoClients.create("mongodb://localhost:27017");
    // or use this one to avoid
    // org.bson.codecs.configuration.CodecConfigurationException: The uuidRepresentation has not been specified, so the UUID cannot be encoded.
    return MongoClients.create(MongoClientSettings.builder()
        .uuidRepresentation(UuidRepresentation.STANDARD)      // save UUIDs as 0x04 subtype
        .build());
  }

  // Has the methods to perform the CRUD operations.
  @Bean
  public MongoTemplate mongoTemplate() {
    // Here we set the name of the db that will be used by the template
    return new MongoTemplate(mongoClient(), "libraryDB");
  }
}