package com.riel.rabbitmqdemo.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfiguration {
  public static final String EXCHANGE_NAME = "tips_tx";
  public static final String DEFAULT_PARSING_QUEUE = "default_parser_q";
  public static final String ROUTING_KEY = "tips";

  // Create the exchange by name
  @Bean
  public TopicExchange tipsExchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  // Create a queue by name
  @Bean
  public Queue defaultParsingQueue() {
    return new Queue(DEFAULT_PARSING_QUEUE);
  }

  // Bind the queue to the exchange with the routing-key
  @Bean
  public Binding queueToExchangeBinding() {
    return BindingBuilder.bind(defaultParsingQueue()).to(tipsExchange()).with(ROUTING_KEY);
  }

  // Create the converter
  @Bean
  public Jackson2JsonMessageConverter messageConverter() {
    ObjectMapper mapper = new ObjectMapper();
    return new Jackson2JsonMessageConverter(mapper);
  }

  // Use the converter in the template
  // It will ty to convert the object passed by the sender
  // With this PracticalTipMessage does not need to be Serializable
  // (Jackson serializes and it does not need it
  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());
    return rabbitTemplate;
  }
}
