package com.es.phoneshop.model.validator;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class UserDataValidator {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+[1-9]{1}[0-9]{7,11}$");
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private UserDataValidator(){
    }

    public static boolean isLegalPhone(String potentialPhone) {
        if(potentialPhone == null || potentialPhone.isEmpty()){
            return false;
        }
        return PHONE_PATTERN.matcher(potentialPhone).find();
    }

    public static boolean isLegalDate(String potentialDate) {
        if(potentialDate == null || potentialDate.isEmpty()){
            return false;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        simpleDateFormat.setLenient(false);
        return simpleDateFormat.parse(potentialDate, new ParsePosition(0)) != null;
    }
}
