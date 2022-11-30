package com.amigoscode.notification.domain.repository;

import com.amigoscode.notification.domain.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}
