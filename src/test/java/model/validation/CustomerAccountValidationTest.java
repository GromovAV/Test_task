package model.validation;


import com.example.model.dto.CustomerAccountDto;
import com.example.model.entity.CustomerAccount_;
import com.example.model.enums.ClientAppType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CustomerAccountValidationTest {
    private Validator validator;
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateMobileRequest_Success() {
        CustomerAccountDto customerAccountDto = new CustomerAccountDto();
        customerAccountDto.setClientAppType(ClientAppType.MOBILE);
        customerAccountDto.setPhoneNumber("71234567890");
        Set<ConstraintViolation<CustomerAccountDto>> validationResult = validator.validate(customerAccountDto);

        assertEquals(validationResult.size(), 0);
    }

    @Test
    public void validateMobileRequest_Fail() {
        CustomerAccountDto customerAccountDto = new CustomerAccountDto();
        customerAccountDto.setClientAppType(ClientAppType.MOBILE);
        customerAccountDto.setBankId("123456");
        customerAccountDto.setLastName("Smith");
        customerAccountDto.setMiddleName("Smithovich");
        customerAccountDto.setFirstName("John");

        Set<ConstraintViolation<CustomerAccountDto>> validationResult = validator.validate(customerAccountDto);

        assertEquals(1, validationResult.size());

        ConstraintViolation<CustomerAccountDto> constraintViolation = validationResult.iterator().next();
        assertEquals(CustomerAccount_.PHONE_NUMBER, constraintViolation.getPropertyPath().toString());
    }

    @Test
    public void validateGosuslugiRequest_Fail() {
        CustomerAccountDto customerAccountDto = new CustomerAccountDto();
        customerAccountDto.setClientAppType(ClientAppType.GOSUSLUGI);
        customerAccountDto.setBankId("123456");
        customerAccountDto.setLastName("Smith");
        customerAccountDto.setMiddleName("Smithovich");
        customerAccountDto.setFirstName("John");
        customerAccountDto.setPlaceOfBirth("Moscow");
        customerAccountDto.setPhoneNumber("71234567890");
        customerAccountDto.setRegistrationAddress("Moscow");

        Set<ConstraintViolation<CustomerAccountDto>> validationResult = validator.validate(customerAccountDto);

        assertEquals(2, validationResult.size());

        assertTrue(validationResult.stream().map(ConstraintViolation::getPropertyPath)
                .anyMatch(e -> e.toString().equals(CustomerAccount_.DATE_OF_BIRTH)));
        assertTrue(validationResult.stream().map(ConstraintViolation::getPropertyPath)
                .anyMatch(e -> e.toString().equals(CustomerAccount_.PASSPORT_NUMBER)));
    }

    @Test
    public void validateGosuslugiRequest_Success() {
        CustomerAccountDto customerAccountDto = new CustomerAccountDto();
        customerAccountDto.setClientAppType(ClientAppType.GOSUSLUGI);
        customerAccountDto.setBankId("123456");
        customerAccountDto.setLastName("Smith");
        customerAccountDto.setMiddleName("Smithovich");
        customerAccountDto.setFirstName("John");
        customerAccountDto.setPlaceOfBirth("Moscow");
        customerAccountDto.setPhoneNumber("71234567890");
        customerAccountDto.setRegistrationAddress("Moscow");
        customerAccountDto.setDateOfBirth(LocalDate.now());
        customerAccountDto.setPassportNumber("1646 789086");

        Set<ConstraintViolation<CustomerAccountDto>> validationResult = validator.validate(customerAccountDto);

        assertEquals(0, validationResult.size());
    }

    @Test
    public void validatePassportFormat_Fail() {
        CustomerAccountDto customerAccountDto = new CustomerAccountDto();
        customerAccountDto.setClientAppType(ClientAppType.MOBILE);
        customerAccountDto.setPlaceOfBirth("Moscow");
        customerAccountDto.setPhoneNumber("71234567890");
        customerAccountDto.setPassportNumber("16465789086");

        Set<ConstraintViolation<CustomerAccountDto>> validationResult = validator.validate(customerAccountDto);

        assertEquals(1, validationResult.size());

        ConstraintViolation<CustomerAccountDto> constraintViolation = validationResult.iterator().next();
        assertEquals(CustomerAccount_.PASSPORT_NUMBER, constraintViolation.getPropertyPath().toString());
    }
}
