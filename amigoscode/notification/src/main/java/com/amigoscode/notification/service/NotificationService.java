package com.amigoscode.notification.service;

import com.amigoscode.clients.dto.NotificationRequest;
import com.amigoscode.notification.domain.model.Notification;
import com.amigoscode.notification.domain.repository.NotificationRepository;
import com.amigoscode.notification.mapper.NotificationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public record NotificationService(
    NotificationRepository repository,
    NotificationMapper mapper
    ) {


  public void send(NotificationRequest request){
    log.info("NotificationRequest before mapper {}", request);
    Notification notification = mapper.toNotification(request);
    log.info("notification after mapper {}", notification);
    repository.save(notification);
  }
}
