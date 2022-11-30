package com.amigoscode.notification;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import com.amigoscode.notification.config.NotificationConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(
    scanBasePackages = {
        "com.amigoscode.notification",
        "com.amigoscode.amqp"
    }
)
public class NotificationApplication {
  public static void main(String[] args) {
    SpringApplication.run(NotificationApplication.class, args);
  }

//  @Bean
//  CommandLineRunner commandLineRunner(
//      RabbitMQMessageProducer producer,
//      NotificationConfig notificationConfig
//      ){
//    return args -> {
//      producer.publish(
//          new Person("Eder", 39),
//          notificationConfig.getInternalExchange(),
//          notificationConfig.getInternalNotificationRoutingKey()
//      );
//    };
//  }
//
//  record Person(String name, int age){}
}
