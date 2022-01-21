package com.tomatopay.transactionservice.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {

    private int status;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("errors")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> fieldErrors = new HashMap<>();

    public void addError(String field, String message) {
        fieldErrors.put(field, message);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Map<String, Object> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

}
