package com.nerdwin15.buildwatchdemo.db;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.inject.RoboInjector;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.Project;
import com.nerdwin15.buildwatchdemo.domain.db.DbEnhancedBuild;

/**
 * A DomainDbManager that is reponsible for working with {@link Build} objects.
 *
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class BuildDomainDbManager extends AbstractDomainDbManager {

  @Inject
  private Context context;
  
  private static final String TABLE_NAME = "builds";

  private static final String COL_ID = "_id";
  private static final String COL_BUILD_NUM = "build_number";
  private static final String COL_DATE = "date";
  private static final String COL_PROJECT_ID = "project_id";
  private static final String COL_STATUS = "status";
  private static final String COL_URL = "url";

  private static final String[] ALL_KEYS = { 
      COL_ID, COL_PROJECT_ID, COL_BUILD_NUM, COL_DATE, COL_STATUS, COL_URL
  };

  private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" 
      + COL_ID + " INTEGER PRIMARY KEY, "   + COL_PROJECT_ID + " INTEGER, "
      + COL_BUILD_NUM + " INTEGER, "        + COL_DATE + " TEXT, "
      + COL_STATUS + " TEXT, "              + COL_URL + " TEXT"
      + ")";
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void doCreate(SQLiteDatabase writeableDatabase) {
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
   * Stores the provided Build into the database
   * @param build The Build to save
   * @param project The Project the build belongs to
   */
  public void create(Build build, Project project) {
    SQLiteDatabase db = getWriteableDatabase();
    ContentValues values = getValues(build);
    values.put(COL_PROJECT_ID, project.getId());
    Long id = db.insert(TABLE_NAME, null, values);
    build.setId(id);
  }

  /**
   * Retrieves all Builds for the provided Project
   * @param project The Project to find Builds for.
   * @return The Builds for the project
   */
  public List<Build> retrieveBuilds(Project project) {
    List<Build> builds = new ArrayList<Build>();
    Cursor c = getReadableDatabase().query(TABLE_NAME, ALL_KEYS, 
        COL_PROJECT_ID + "=?", new String[]{project.getId().toString()}, 
        null, null, COL_BUILD_NUM + " DESC");
    if (c != null && c.moveToFirst()) {
      do {
        builds.add(getBuild(c));
      } while (c.moveToNext());
    }
    return builds;
  }
  
  private ContentValues getValues(Build build) {
    ContentValues values = new ContentValues();
    values.put(COL_BUILD_NUM, build.getBuildNumber());
    values.put(COL_DATE, convertDateToTimestamp(build.getDate()));
    values.put(COL_STATUS, build.getStatus().toString());
    values.put(COL_URL, build.getUrl());
    return values;
  }
  
  private Build getBuild(Cursor cursor) {
    Build build = new DbEnhancedBuild();
    final RoboInjector injector = RoboGuice.getInjector(context);
    injector.injectMembersWithoutViews(build);
    build.setBuildNumber(cursor.getLong(cursor.getColumnIndex(COL_BUILD_NUM)));
    build.setDate(convertTimestampStringToDate(
        cursor.getString(cursor.getColumnIndex(COL_DATE))));
    build.setId(cursor.getLong(cursor.getColumnIndex(COL_ID)));
    build.setStatus(Build.Status.valueOf(
        cursor.getString(cursor.getColumnIndex(COL_STATUS))));
    build.setUrl(cursor.getString(cursor.getColumnIndex(COL_URL)));
    return build;
  }
}
