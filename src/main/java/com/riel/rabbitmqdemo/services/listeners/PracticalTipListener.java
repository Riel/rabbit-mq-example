package com.riel.rabbitmqdemo.services.listeners;

import com.riel.rabbitmqdemo.configuration.MessageConfiguration;
import com.riel.rabbitmqdemo.models.PracticalTipMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class PracticalTipListener {

  private static final Logger log = LoggerFactory.getLogger(PracticalTipListener.class);

  // Here we just subscribe to the given Queue
  @RabbitListener(queues = MessageConfiguration.DEFAULT_PARSING_QUEUE)
  public void consumeDefaultMessage(final PracticalTipMessage message) {
    log.info("Received message, tip is: {}", message.toString());
  }
}
