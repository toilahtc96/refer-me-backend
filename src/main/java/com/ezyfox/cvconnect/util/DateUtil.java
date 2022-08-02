package com.ezyfox.cvconnect.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String DATE_DDMMYYYY_PATTERN = "dd/MM/yyyy";
    public static Date parseFromStringFormat(String date, String parttern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(parttern);
        return simpleDateFormat.parse(date);
    }
}