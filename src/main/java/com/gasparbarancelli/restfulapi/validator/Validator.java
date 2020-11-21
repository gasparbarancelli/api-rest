package com.gasparbarancelli.restfulapi.validator;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class Validator {

    public static Predicate<String> isEmail = email -> {
        var emailValidationRegex = "^(.+)@(.+)$";
        var pattern = Pattern.compile(emailValidationRegex);
        return pattern.matcher(email).matches();
    };

    public static Predicate<String> isNotEmpty = value -> value != null && value.trim().length() > 0;

}
