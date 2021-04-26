package com.tr.indodaxdemo.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

  public static String dateToString(Date date, String format) {
    return new SimpleDateFormat(format).format(date);
  }

  public static Date stringToDate(String date, String format) {
    try {
      return new SimpleDateFormat(format).parse(date);
    } catch (ParseException ex) {
      return null;
    }
  }

  public static Date timestampToDate(Timestamp timestamp) {
    return new Date(timestamp.getTime());
  }

  public static Timestamp dateToTimestamp(Date date) {
    return new Timestamp(date.getTime());
  }
}
