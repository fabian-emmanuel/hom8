package org.indulge.hom8.constants;

public interface Message {
    String SUCCESSFUL = "Successful.";
    String OTP_SENT = "OTP Sent.";
    String USER_ACCOUNT_ACTIVATED = "User Account Activated.";


    String FIRST_NAME_IS_REQUIRED = "First name is required.";
    String LAST_NAME_IS_REQUIRED = "Last name is required.";
    String PHONE_NUMBER_IS_REQUIRED = "Phone Number is required.";
    String PIN_IS_REQUIRED = "Pin is required.";
    String USER_TYPE_IS_REQUIRED = "User Type is required.";
    String CODE_IS_REQUIRED = "Code is required.";
    String ACTION_IS_REQUIRED = "Action is required.";


    String INVALID_PHONE_NUMBER = "Invalid Phone Number.";
    String INVALID_PIN = "Invalid pin. Pin must be non-repeating 4-digit numbers.";
    String UNAUTHORIZED = "You need to be authenticated to access this resource.";
    String INVALID_EXPIRED_TOKEN = "Token appears to be Invalid/Expired.";

    String USER_WITH_PHONE_NUMBER_ALREADY_EXIST = "User With phoneNumber `%s` Already Exist.";
}
