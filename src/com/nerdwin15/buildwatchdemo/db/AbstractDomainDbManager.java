package com.nerdwin15.buildwatchdemo.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;

/**
 * An abstract implementation of a DbDomainManager that provides utility methods
 * and methods to get access to the database.
 *  
 * @author Michael Irwin (mikesir87)
 */
@SuppressLint("SimpleDateFormat")
public abstract class AbstractDomainDbManager implements DomainDbManager {

  @Inject
  private DatabaseManager databaseManager;

  /**
   * Gets the readable database
   * @return The readable database
   */
  protected SQLiteDatabase getReadableDatabase() {
    return databaseManager.getReadableDatabase();
  }

  /**
   * Gets the writeable database
   * @return The writeable database
   */
  protected SQLiteDatabase getWriteableDatabase() {
    return databaseManager.getWritableDatabase();
  }

  /**
   * Converts a Date object into a timestamp string
   * @param timestamp The Date to convert
   * @return The String representation of the Date in a timestamp format.
   */
  protected String convertDateToTimestamp(Date timestamp) {
    if (timestamp == null)
      return "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(timestamp);
  }

  /**
   * Converts a Date object into a date string
   * @param date The Date object to convert
   * @return The String representation of the Date
   */
  protected String convertDateToDate(Date date) {
    if (date == null)
      return "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    return sdf.format(date);
  }

  /**
   * Converts a String date representation (yyyy-MM-dd) into a Date object.
   * @param date The date string to convert
   * @return The converted date object
   */
  protected Date convertStringToDate(String date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    try {
      return sdf.parse(date);
    } catch (ParseException e) {
      return null;
    }
  }

  /**
   * Converts a String timestamp representation (yyyy-MM-dd HH:mm:ss) to a Date
   * object.
   * @param timestamp The timestamp string
   * @return The converted timestamp
   */
  protected Date convertTimestampStringToDate(String timestamp) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      return sdf.parse(timestamp);
    } catch (ParseException e) {
      return null;
    }
  }
  
}
