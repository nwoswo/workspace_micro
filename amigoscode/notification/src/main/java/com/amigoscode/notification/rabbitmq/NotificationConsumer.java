package com.amigoscode.notification.rabbitmq;

import com.amigoscode.clients.dto.NotificationRequest;
import com.amigoscode.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

  private final NotificationService notificationService;

  @RabbitListener(queues = "${rabbitmq.queues.notification}")
  public void consumer(NotificationRequest request){
    log.info("Consumed {} from queue", request);
    notificationService.send(request);

  }
}
