package com.amigoscode.notification.controller;

import com.amigoscode.clients.dto.NotificationRequest;
import com.amigoscode.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/notification")
public record NotificationController(
    NotificationService service
) {

  @PostMapping
  public void sendNotification(@RequestBody NotificationRequest request){

    log.info("New notification ...{}", request);
    service.send(request);
  }
}
