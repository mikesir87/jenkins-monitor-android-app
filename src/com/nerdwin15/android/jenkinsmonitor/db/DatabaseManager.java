package com.nerdwin15.android.jenkinsmonitor.db;

import java.util.Set;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.android.jenkinsmonitor.GuiceConfigurationModule;

/**
 * The top-level DatabaseManager that opens the database and provides access to
 * the Database.
 * 
 * The various DomainDbManagers are injected into this manager and are given
 * the ability to create their various tables during the onCreate.  The 
 * injection of the DomainDbManagers is provided by Guice and configured in the
 * {@link GuiceConfigurationModule}.
 *
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class DatabaseManager extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "buildwatch";
  private static final int DATABASE_VERSION = 2;
  
  @Inject
  private Set<DomainDbManager> dbManagers;
  
  /**
   * Construct the DatabaseManager, which sets up the SQLiteDatabase.
   * @param context The Android context.
   */
  @Inject
  public DatabaseManager(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    for (DomainDbManager dbManager : dbManagers) {
      Log.i("databaseSetup", 
          "Letting " + dbManager.getClass().getName() + " create");
      dbManager.doCreate(db);
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    for (DomainDbManager dbManager : dbManagers) {
      Log.i("databaseSetup", 
          "Letting " + dbManager.getClass().getName() + " update");
      dbManager.doUpdate(db, oldVersion, newVersion);
    }
  }
  
}
