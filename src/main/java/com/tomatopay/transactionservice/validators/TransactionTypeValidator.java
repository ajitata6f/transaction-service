package com.tomatopay.transactionservice.validators;

import com.tomatopay.transactionservice.annotations.ValidTransactionType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TransactionTypeValidator implements ConstraintValidator<ValidTransactionType, String> {
    private Pattern pattern;

    @Override
    public void initialize(ValidTransactionType constraintAnnotation) {
        try {
            pattern = Pattern.compile(constraintAnnotation.regexp());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher m = pattern.matcher(value);
        return m.matches();
    }

}
