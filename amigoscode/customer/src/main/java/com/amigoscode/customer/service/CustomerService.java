package com.amigoscode.customer.service;

import com.amigoscode.amqp.RabbitMQMessageProducer;
import com.amigoscode.clients.FraudClient;
import com.amigoscode.clients.NotificationClient;
import com.amigoscode.clients.dto.FraudCheckResponse;
import com.amigoscode.clients.dto.NotificationRequest;
import com.amigoscode.customer.domain.model.Customer;
import com.amigoscode.customer.domain.repository.CustomerRepository;
import com.amigoscode.customer.dto.CustomerRegistrationRequest;
import com.amigoscode.customer.mapper.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(
    CustomerMapper customerMapper,
    CustomerRepository customerRepository,
    RestTemplate restTemplate,
    FraudClient fraudclient,
    NotificationClient notificationClient,
    RabbitMQMessageProducer mqMessageProducer
) {

  public void registerCustomer(CustomerRegistrationRequest request) {
    Customer customer = customerMapper.toCustomer(request);

    customerRepository.saveAndFlush(customer);
//
//    FraudCheckResponse response = restTemplate.getForObject(
//        "http://FRAUD/api/v1/fraud-check/{customerId}",
//        FraudCheckResponse.class,
//        customer.getId()
//    );

    FraudCheckResponse fraudster = fraudclient.isFraudster(customer.getId());

    if (fraudster.isFraudster()) {
      throw new IllegalStateException("fraudster");
    }

    NotificationRequest request1 = new NotificationRequest(
        customer.getId(),
        customer.getEmail(),
        String.format("Hi %s, welcome to Amigoscode...",
            customer.getFirstName())
    );


    mqMessageProducer.publish(
        request1,
        "internal.exchange",
        "internal.notification.routing-key"
    );


  }
}
