package com.example.repository;

import com.example.model.dto.CustomerAccountSearchDto;
import com.example.model.entity.CustomerAccount;
import com.example.model.entity.CustomerAccount_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class CustomerAccountSpecification {
    public static Specification<CustomerAccount> search(CustomerAccountSearchDto dto) {
        return Specification
                .where(findByLastName(dto.getLastName()))
                .and(findByFirstName(dto.getFirstName()))
                .and(findByMiddleName(dto.getMiddleName()))
                .and(findByPhoneNumber(dto.getPhoneNumber()))
                .and(findByEmail(dto.getEmail()));
    }

    public static Specification<CustomerAccount> findByLastName(String lastName) {
        if (!StringUtils.hasLength(lastName))
            return null;

        return (root, query, cb) -> cb.equal(root.get(CustomerAccount_.LAST_NAME), lastName);
    }

    public static Specification<CustomerAccount> findByFirstName(String firstName) {
        if (!StringUtils.hasLength(firstName))
            return null;

        return (root, query, cb) -> cb.equal(root.get(CustomerAccount_.FIRST_NAME), firstName);
    }
    public static Specification<CustomerAccount> findByMiddleName(String middleName) {
        if (!StringUtils.hasLength(middleName))
            return null;

        return (root, query, cb) -> cb.equal(root.get(CustomerAccount_.MIDDLE_NAME), middleName);
    }

    public static Specification<CustomerAccount> findByPhoneNumber(String phoneNumber) {
        if (!StringUtils.hasLength(phoneNumber))
            return null;

        return (root, query, cb) -> cb.equal(root.get(CustomerAccount_.PHONE_NUMBER), phoneNumber);
    }

    public static Specification<CustomerAccount> findByEmail(String email) {
        if (!StringUtils.hasLength(email))
            return null;

        return (root, query, cb) -> cb.equal(root.get(CustomerAccount_.EMAIL), email);
    }
}
