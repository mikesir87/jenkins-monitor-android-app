package com.nerdwin15.android.jenkinsmonitor.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Singleton;
import com.nerdwin15.android.jenkinsmonitor.domain.Build;
import com.nerdwin15.android.jenkinsmonitor.domain.CommitInfo;

/**
 * A DomainDbManager that is responsible for working with {@link CommitInfo}
 * domain objects.
 *
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class CommitInfoDomainDbManager extends AbstractDomainDbManager {

  private static final String TABLE_NAME = "commits";
  
  private static final String COL_ID = "_id";
  private static final String COL_BUILD_ID = "build_id";
  private static final String COL_COMMITTER = "committer";
  private static final String COL_MESSAGE = "message";
  private static final String COL_TIMESTAMP = "date_created";

  private static final String[] ALL_KEYS = { 
      COL_ID, COL_BUILD_ID, COL_COMMITTER, COL_MESSAGE, COL_TIMESTAMP
  };

  private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " (" 
      + COL_ID + " INTEGER PRIMARY KEY, "   + COL_BUILD_ID + " INTEGER, "   
      + COL_COMMITTER + " TEXT, "           + COL_MESSAGE + " TEXT, "
      + COL_TIMESTAMP + " TEXT"
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
   * Saves the provided CommitInfo into the database.
   * @param commit The commit to save
   * @param build The Build that the commit is associated with
   */
  public void create(CommitInfo commit, Build build) {
    SQLiteDatabase db = getWriteableDatabase();
    ContentValues values = getValues(commit);
    values.put(COL_BUILD_ID, build.getId());
    Long id = db.insert(TABLE_NAME, null, values);
    commit.setId(id);
  }

  /**
   * Retrieve all commits for the provided Build.
   * @param build The Build to find commits for.
   * @return The commits related to the requested build.
   */
  public List<CommitInfo> retrieveCommits(Build build) {
    List<CommitInfo> commits = new ArrayList<CommitInfo>();
    Cursor c = getReadableDatabase().query(TABLE_NAME, ALL_KEYS, 
        COL_BUILD_ID + "=?", new String[]{build.getId().toString()}, 
        null, null, COL_TIMESTAMP + " ASC");
    if (c != null && c.moveToFirst()) {
      do {
        commits.add(getCommit(c));
      } while (c.moveToNext());
    }
    return commits;
  }
  
  private ContentValues getValues(CommitInfo commit) {
    ContentValues values = new ContentValues();
    values.put(COL_COMMITTER, commit.getCommitter());
    values.put(COL_MESSAGE, commit.getMessage());
    values.put(COL_TIMESTAMP, convertDateToTimestamp(commit.getTimestamp()));
    return values;
  }
  
  private CommitInfo getCommit(Cursor cursor) {
    CommitInfo commit = new CommitInfo();
    commit.setCommitter(cursor.getString(cursor.getColumnIndex(COL_COMMITTER)));
    commit.setId(cursor.getLong(cursor.getColumnIndex(COL_ID)));
    commit.setMessage(cursor.getString(cursor.getColumnIndex(COL_MESSAGE)));
    commit.setTimestamp(convertTimestampStringToDate(
        cursor.getString(cursor.getColumnIndex(COL_TIMESTAMP))));
    return commit;
  }

}
