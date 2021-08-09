package com.riel.rabbitmqdemo.services.senders;

import com.riel.rabbitmqdemo.configuration.MessageConfiguration;
import com.riel.rabbitmqdemo.models.PracticalTipMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class PracticalTipSender {

  private static final Logger log = LoggerFactory.getLogger(PracticalTipSender.class);

  // Used to send messages
  private final RabbitTemplate rabbitTemplate;

  public PracticalTipSender(final RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Scheduled(fixedDelay = 3000L)
  public void sendPracticalTip() {
    PracticalTipMessage tip = new PracticalTipMessage("Always use Immutable classes in Java", 1, false);

    // Send a message to the given exchange with the given routing key and content
    rabbitTemplate.convertAndSend(
        MessageConfiguration.EXCHANGE_NAME,
        MessageConfiguration.ROUTING_KEY, tip);
    log.info("Practical Tip sent");
  }
}
