package com.fanfiction.webproject.ui.model.response;

public enum ErrorMessages {

    MISSING_REQUIRED_FIELD("Missing required field(-s). Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    NO_RECORDS_IN_BASE("There is no records in base"),
    NO_RECORD_FOUND("Record with provided id is not found"),
    RECORD_WAS_DELETED("Record with provided id was deleted"),
    AUTHENTICATION_FAILED("Authentication failed"),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");

    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}