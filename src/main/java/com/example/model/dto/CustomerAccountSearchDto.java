package com.example.model.dto;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountSearchDto {
    private String lastName;
    private String firstName;
    private String middleName;
    private String phoneNumber;
    private String email;

    public boolean isEmpty() {
        return Stream.of(lastName, firstName, middleName, phoneNumber, email).allMatch(StringUtils::isEmpty);
    }
}
