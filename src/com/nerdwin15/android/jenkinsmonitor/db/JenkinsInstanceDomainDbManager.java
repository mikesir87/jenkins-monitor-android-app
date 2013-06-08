package com.nerdwin15.android.jenkinsmonitor.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.android.jenkinsmonitor.domain.JenkinsInstance;
import com.nerdwin15.android.jenkinsmonitor.domain.db.DbEnhancedJenkinsInstance;

/**
 * A DomainDbManager that is responsible for the persistence of 
 * {@link JenkinsInstance} domain objects.
 *
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class JenkinsInstanceDomainDbManager extends AbstractDomainDbManager {

  @Inject
  private Context context;
  
  private static final String TABLE_NAME = "jenkins_instances";

  private static final String COL_ID = "_id";
  private static final String COL_NAME = "name";
  private static final String COL_URL = "url";
  private static final String COL_FIREWALLED = "is_firewalled";
  private static final String COL_DATE_ADDED = "date_added";
  private static final String COL_USERNAME = "username";
  private static final String COL_API_TOKEN = "api_token";
  private static final String COL_SENDER_ID = "sender_id";

  private static final String[] ALL_KEYS = { 
      COL_ID, COL_NAME, COL_URL, COL_FIREWALLED, COL_DATE_ADDED, COL_USERNAME, 
      COL_API_TOKEN, COL_SENDER_ID 
  };

  private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" 
      + COL_ID + " INTEGER PRIMARY KEY, "   + COL_NAME + " TEXT, "
      + COL_URL + " TEXT, "                 + COL_FIREWALLED + " INTEGER, " 
      + COL_DATE_ADDED + " TEXT, "          + COL_USERNAME + " TEXT, "
      + COL_API_TOKEN + " TEXT, "           + COL_SENDER_ID + " TEXT"
      + ")";

  /**
   * {@inheritDoc}
   */
  @Override
  public void doCreate(SQLiteDatabase writeableDatabase) {
    Log.i("manager", "Writing to the database");
    Log.i("manager", "Running sql " + TABLE_CREATE);
    writeableDatabase.execSQL(TABLE_CREATE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doUpdate(SQLiteDatabase writeableDatabase, int oldVersion,
      int newVersion) {
    // Do nothing for now
  }
  
  /**
   * Save the provided JenkinsInstance into the database
   * @param instance The instance to save
   */
  public void create(JenkinsInstance instance) {
    SQLiteDatabase db = getWriteableDatabase();
    Long id = db.insert(TABLE_NAME, null, getValues(instance));
    instance.setId(id);
  }

  /**
   * Retrieve all JenkinsInstances stored in the database.
   * @return All JenkinsInstances in the database.
   */
  public List<JenkinsInstance> retrieveJenkinsInstances() {
    List<JenkinsInstance> instances = new ArrayList<JenkinsInstance>();

    Cursor c = getReadableDatabase().query(TABLE_NAME, ALL_KEYS, null, null,
        null, null, COL_NAME + " ASC");
    if (c != null && c.moveToFirst()) {
      do {
        instances.add(createInstance(c));
      } while (c.moveToNext());
    }
    return instances;
  }

  /**
   * Retrieve the JenkinsInstance referenced by the provided id.
   * @param id The id of the JenkinsInstance to retrieve.
   * @return The requested JenkinsInstance, if found. Otherwise, null.
   */
  public JenkinsInstance retrieveJenkinsInstance(Long id) {
    Cursor c = getReadableDatabase().query(TABLE_NAME, ALL_KEYS, COL_ID + "=?", 
        new String[]{ id.toString() }, null, null, null);
    c.moveToFirst();
    return createInstance(c);
  }

  /**
   * Retrieve a JenkinsInstance based on its base url
   * @param baseUrl The base url of the Jenkins Instance
   * @return The instance, if found. Otherwise, null.
   */
  public JenkinsInstance retrieveJenkinsInstanceByBaseUrl(String baseUrl) {
    Cursor c = getReadableDatabase().query(TABLE_NAME, ALL_KEYS, COL_URL + "=?", 
        new String[]{ baseUrl }, null, null, null);
    c.moveToFirst();
    return createInstance(c);
  }

  /**
   * Update the database's stored representation of the provided instance
   * @param instance The instance to update
   */
  public void update(JenkinsInstance instance) {
    getWriteableDatabase().update(TABLE_NAME, getValues(instance), 
        COL_ID + "=?", new String[]{ instance.getId().toString() });
  }
  
  private JenkinsInstance createInstance(Cursor cursor) {
    JenkinsInstance instance = new DbEnhancedJenkinsInstance();
    final RoboInjector injector = RoboGuice.getInjector(context);
    injector.injectMembersWithoutViews(instance);
    instance.setApiToken(cursor.getString(
        cursor.getColumnIndex(COL_API_TOKEN)));
    instance.setBaseUrl(cursor.getString(cursor.getColumnIndex(COL_URL)));
    instance.setFirewalled(cursor.getInt(
        cursor.getColumnIndex(COL_FIREWALLED)) == 1);
    instance.setId(cursor.getLong(cursor.getColumnIndex(COL_ID)));
    instance.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
    instance.setSenderId(cursor.getString(
        cursor.getColumnIndex(COL_SENDER_ID)));
    instance.setUsername(cursor.getString(cursor.getColumnIndex(COL_USERNAME)));
    return instance;
  }
  
  private ContentValues getValues(JenkinsInstance instance) {
    instance.setDateCreated(new Date());
    ContentValues values = new ContentValues();
    values.put(COL_API_TOKEN, instance.getApiToken());
    values.put(COL_DATE_ADDED, 
        convertDateToTimestamp(instance.getDateCreated()));
    values.put(COL_FIREWALLED, (instance.isFirewalled()) ? 1 : 0);
    values.put(COL_NAME, instance.getName());
    values.put(COL_SENDER_ID, instance.getSenderId());
    values.put(COL_URL, instance.getBaseUrl());
    values.put(COL_USERNAME, instance.getUsername());
    return values;
  }
}
