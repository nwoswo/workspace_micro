package com.amigoscode.clients.dto;

public record NotificationRequest(
    Integer toCustomerId,
    String toCustomerName,
    String message
) {
}