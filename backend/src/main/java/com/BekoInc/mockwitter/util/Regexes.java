package com.BekoInc.mockwitter.util;


public class Regexes {

    public static final String NAME_or_SURNAME = "^[A-Za-z]{1,20}$";

    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static final String USERNAME_REGEX = "^[A-Za-z0-9]{1,20}$";

    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9])(?=\\S+$).{8,20}$";

    public static final String PHONE_NUMBER_REGEX = "^(\\+\\d{1,3})?[0-9]{10}$";
}
