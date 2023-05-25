package com.example.model.dto;

import com.example.model.enums.ClientAppType;
import com.example.model.validation.ConditionalValidation;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConditionalValidation(conditionalProperty = "clientAppType", values = {"MAIL"},
        requiredProperties = {"firstName", "middleName", "lastName", "email"})
@ConditionalValidation(conditionalProperty = "clientAppType", values = {"MOBILE"},
        requiredProperties = {"phoneNumber"})
@ConditionalValidation(conditionalProperty = "clientAppType", values = {"BANK"},
        requiredProperties = {"bankId", "firstName", "middleName", "lastName", "dateOfBirth", "passportNumber"})
@ConditionalValidation(conditionalProperty = "clientAppType", values = {"GOSUSLUGI"},
        excludedProperties = {"email", "residentialAddress", "id"})
public class CustomerAccountDto {
    private Long id;
    private String bankId;
    private String lastName;
    private String firstName;
    private String middleName;
    private LocalDate dateOfBirth;
    @Pattern(regexp = "\\d{4}\\s\\d{6}", message = "Passport number format is XXXX XXXXXX")
    private String passportNumber;
    private String placeOfBirth;
    @Pattern(regexp = "7\\d{10}", message = "Phone number format is 7XXXXXXXXXX")
    private String phoneNumber;
    private String email;
    private String registrationAddress;
    private String residentialAddress;

    private ClientAppType clientAppType;
}
