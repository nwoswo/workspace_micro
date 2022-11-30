package com.amigoscode.customer.mapper;

import org.mapstruct.Mapper;

import com.amigoscode.customer.domain.model.Customer;
import com.amigoscode.customer.dto.CustomerRegistrationRequest;

@Mapper(componentModel = "spring")
public interface  CustomerMapper {
    Customer toCustomer(CustomerRegistrationRequest request);
}
