package com.example.model.converter;

import com.example.model.dto.CustomerAccountDto;
import com.example.model.entity.CustomerAccount;

public class CustomerAccountConverter {

    public static CustomerAccountDto convertToDto(CustomerAccount customerAccount) {
        CustomerAccountDto dto = new CustomerAccountDto();

        dto.setId(customerAccount.getId());
        dto.setBankId(customerAccount.getBankId());
        dto.setDateOfBirth(customerAccount.getDateOfBirth());
        dto.setEmail(customerAccount.getEmail());
        dto.setFirstName(customerAccount.getFirstName());
        dto.setMiddleName(customerAccount.getMiddleName());
        dto.setPassportNumber(customerAccount.getPassportNumber());
        dto.setPhoneNumber(customerAccount.getPhoneNumber());
        dto.setPlaceOfBirth(customerAccount.getPlaceOfBirth());
        dto.setRegistrationAddress(customerAccount.getRegistrationAddress());
        dto.setResidentialAddress(customerAccount.getResidentialAddress());
        dto.setLastName(customerAccount.getLastName());

        return dto;
    }

    public static CustomerAccount convertToEntity(CustomerAccountDto customerAccountDto) {
        CustomerAccount customerAccount = new CustomerAccount();

        customerAccount.setBankId(customerAccountDto.getBankId());
        customerAccount.setDateOfBirth(customerAccountDto.getDateOfBirth());
        customerAccount.setEmail(customerAccountDto.getEmail());
        customerAccount.setFirstName(customerAccountDto.getFirstName());
        customerAccount.setMiddleName(customerAccountDto.getMiddleName());
        customerAccount.setPassportNumber(customerAccountDto.getPassportNumber());
        customerAccount.setPhoneNumber(customerAccountDto.getPhoneNumber());
        customerAccount.setPlaceOfBirth(customerAccountDto.getPlaceOfBirth());
        customerAccount.setRegistrationAddress(customerAccountDto.getRegistrationAddress());
        customerAccount.setResidentialAddress(customerAccountDto.getResidentialAddress());
        customerAccount.setLastName(customerAccountDto.getLastName());

        return customerAccount;
    }
}
