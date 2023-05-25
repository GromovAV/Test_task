package com.example.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.isEmpty;

@Slf4j
public class ConditionalValidator implements ConstraintValidator<ConditionalValidation, Object> {
    private String conditionalProperty;
    private String[] requiredProperties;
    private String[] excludedProperties;
    private String[] values;

    private static final Map<String, List<String>> CLASS_FIELDS = new HashMap<>();

    @Override
    public void initialize(ConditionalValidation constraint) {
        conditionalProperty = constraint.conditionalProperty();
        requiredProperties = constraint.requiredProperties();
        values = constraint.values();
        excludedProperties = constraint.excludedProperties();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Object conditionalPropertyValue = BeanUtils.getProperty(object, conditionalProperty);
            if (doConditionalValidation(conditionalPropertyValue)) {
                return validateRequiredProperties(object, context);
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            return false;
        }
        return true;
    }

    private boolean validateRequiredProperties(Object object, ConstraintValidatorContext context) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        boolean isValid = true;

        if (excludedProperties.length != 0) {
            List<String> fieldNames = CLASS_FIELDS.get(object.getClass().getName());
            if (fieldNames == null) {
                fieldNames =  Arrays.stream(object.getClass().getDeclaredFields())
                        .map(Field::getName).toList();
                CLASS_FIELDS.put(object.getClass().getName(), fieldNames);
            }

            requiredProperties = fieldNames.stream()
                    .filter(field -> !Arrays.asList(excludedProperties).contains(field))
                    .toArray(String[]::new);
        }

        for (String property : requiredProperties) {
            Object requiredValue = BeanUtils.getProperty(object, property);
            boolean isPresent = requiredValue != null && !isEmpty(requiredValue);
            if (!isPresent) {
                isValid = false;
                context.disableDefaultConstraintViolation();
                context
                        .buildConstraintViolationWithTemplate("field is required " + property)
                        .addPropertyNode(property)
                        .addConstraintViolation();
            }
        }

        return isValid;
    }

    private boolean doConditionalValidation(Object actualValue) {
        return Arrays.asList(values).contains(actualValue);
    }
}
