package com.example.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "customer_account")
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String bankId;

    @Column
    private String lastName;

    @Column
    private String firstName;

    @Column
    private String middleName;

    @Column
    private LocalDate dateOfBirth;

    @Column
    @Pattern(regexp = "\\d{4} \\d{6}", message = "Passport number should be in the format XXXX XXXXXX")
    private String passportNumber;

    @Column
    private String placeOfBirth;

    @Column
    @Pattern(regexp = "7\\d{10}", message = "Phone number should be in the format 7XXXXXXXXXX")
    private String phoneNumber;

    @Column
    @Email(message = "Email should be valid")
    private String email;

    @Column
    private String registrationAddress;

    @Column
    private String residentialAddress;
}
