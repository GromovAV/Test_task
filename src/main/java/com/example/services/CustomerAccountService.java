package com.example.services;

import com.example.model.converter.CustomerAccountConverter;
import com.example.model.dto.CustomerAccountDto;
import com.example.model.dto.CustomerAccountSearchDto;
import com.example.model.entity.CustomerAccount;
import com.example.repository.CustomerAccountRepository;
import com.example.repository.CustomerAccountSpecification;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerAccountService {
    private final CustomerAccountRepository customerAccountRepository;
    private final Validator validator;

    public CustomerAccount createCustomerAccount(CustomerAccountDto customerAccountDto) {
        Set<ConstraintViolation<CustomerAccountDto>> validationViolations = validator.validate(customerAccountDto);
        if (!validationViolations.isEmpty()) {
            String errorMessage = "Failed validation for " + customerAccountDto.getClientAppType() + " request: " +
                    validationViolations.stream()
                            .map(ConstraintViolation::getMessage)
                            .collect(Collectors.joining(", "));
            throw new ValidationException(errorMessage);
        }

        CustomerAccount customerAccount = CustomerAccountConverter.convertToEntity(customerAccountDto);

        return customerAccountRepository.save(customerAccount);
    }

    public Optional<CustomerAccountDto> getCustomerAccountById(Long id) {
        return customerAccountRepository.findById(id).map(CustomerAccountConverter::convertToDto);
    }

    public List<CustomerAccountDto> searchCustomerAccounts(CustomerAccountSearchDto accountSearchDto) {
        return customerAccountRepository
                .findAll(CustomerAccountSpecification.search(accountSearchDto))
                .stream()
                .map(CustomerAccountConverter::convertToDto)
                .toList();
    }

}