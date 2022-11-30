package com.amigoscode.notification.mapper;

import com.amigoscode.clients.dto.NotificationRequest;
import com.amigoscode.notification.domain.model.Notification;
import org.mapstruct.*;

//import java.time.LocalDateTime;


@Mapper(componentModel = "spring")
public interface NotificationMapper {

  @Mappings({
      @Mapping(target="toCustomerEmail", source="toCustomerName"),
      @Mapping(target="sender", constant="AmigosCode"),
      @Mapping(target="sentAt", expression="java(java.time.LocalDateTime.now())")
  })
  Notification toNotification(NotificationRequest request);



}
