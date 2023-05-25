package com.example.controller;

import com.example.model.dto.CustomerAccountDto;
import com.example.model.dto.CustomerAccountSearchDto;
import com.example.model.entity.CustomerAccount;
import com.example.model.enums.ClientAppType;
import com.example.services.CustomerAccountService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer-accounts")
public class CustomerAccountController {
    private final CustomerAccountService customerAccountService;
    @PostMapping
    public ResponseEntity<?> createCustomerAccount(@RequestBody CustomerAccountDto customerAccountDto,
                                                   @RequestHeader("x-Source") ClientAppType source) {
        customerAccountDto.setClientAppType(source);
        CustomerAccount customerAccount = customerAccountService.createCustomerAccount(customerAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerAccountById(@PathVariable Long id) {
        Optional<CustomerAccountDto> customerAccountDto = customerAccountService.getCustomerAccountById(id);
        return ResponseEntity.of(customerAccountDto);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCustomerAccounts(CustomerAccountSearchDto accountSearchDto) {
        if (accountSearchDto.isEmpty()) {
            return ResponseEntity.badRequest().body("You need to specify at least one filter");
        }

        List<CustomerAccountDto> customerAccounts = customerAccountService.searchCustomerAccounts(accountSearchDto);
        return ResponseEntity.ok(customerAccounts);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(Exception e) {
        if (e instanceof ValidationException ve) {
            return ResponseEntity.badRequest().body(ve.getMessage());
        }

        log.error("Exception occurred processing a request", e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
}