package com.nerdwin15.android.jenkinsmonitor.db;

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
import com.nerdwin15.android.jenkinsmonitor.domain.Build.Status;
import com.nerdwin15.android.jenkinsmonitor.domain.JenkinsInstance;
import com.nerdwin15.android.jenkinsmonitor.domain.Project;
import com.nerdwin15.android.jenkinsmonitor.domain.db.DbEnhancedProject;

/**
 * A DomainDbManager that is responsible for the persistence of {@link Project}
 * domain objects.
 *
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class ProjectDomainDbManager extends AbstractDomainDbManager {

  @Inject
  private Context context;
  
  private static final String TABLE_NAME = "projects";

  private static final String COL_ID = "_id";
  private static final String COL_JENKINS_INST_ID = "instance_id";
  private static final String COL_NAME = "name";
  private static final String COL_URL = "url";
  private static final String COL_STATUS = "status";

  private static final String[] ALL_KEYS = { 
      COL_ID, COL_JENKINS_INST_ID, COL_NAME, COL_URL, COL_STATUS
  };

  private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" 
      + COL_ID + " INTEGER PRIMARY KEY, "   + COL_JENKINS_INST_ID + " INTEGER, "   
      + COL_NAME + " TEXT, "                + COL_URL + " TEXT, "
      + COL_STATUS + " TEXT"
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
   * Save the provided Project into the database
   * @param project The project to save
   * @param instance The Jenkins Instance that the project belongs to
   */
  public void create(Project project, JenkinsInstance instance) {
    SQLiteDatabase db = getWriteableDatabase();
    ContentValues values = getValues(project);
    values.put(COL_JENKINS_INST_ID, instance.getId());
    Long id = db.insert(TABLE_NAME, null, values);
    project.setId(id);
  }
  
  /**
   * Retrieve all projects associated with the provided JenkinsInstance.
   * @param instance The instance to find projects for.
   * @return The Projects associated with the provided JenkinsInstance.
   */
  public List<Project> retrieveProjects(JenkinsInstance instance) {
    List<Project> projects = new ArrayList<Project>();
    Cursor c = getReadableDatabase().query(TABLE_NAME, ALL_KEYS, 
        COL_JENKINS_INST_ID + "=?", new String[]{instance.getId().toString()}, 
        null, null, COL_NAME);
    if (c != null && c.moveToFirst()) {
      do {
        projects.add(getProject(c));
      } while (c.moveToNext());
    }
    return projects;
  }
  
  private ContentValues getValues(Project project) {
    ContentValues values = new ContentValues();
    values.put(COL_NAME, project.getName());
    values.put(COL_STATUS, (project.getStatus() != null) ? 
        project.getStatus().toString() : "");
    values.put(COL_URL, project.getUrl());
    return values;
  }
  
  private Project getProject(Cursor cursor) {
    Project project = new DbEnhancedProject();
    final RoboInjector injector = RoboGuice.getInjector(context);
    injector.injectMembersWithoutViews(project);
    project.setId(cursor.getLong(cursor.getColumnIndex(COL_ID)));
    project.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
    project.setStatus(Status.valueOf(cursor.getString(
        cursor.getColumnIndex(COL_STATUS))));
    project.setUrl(cursor.getString(cursor.getColumnIndex(COL_URL)));
    return project;
  }
  
}
