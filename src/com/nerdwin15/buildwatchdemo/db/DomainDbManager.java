package com.nerdwin15.buildwatchdemo.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * A DomainDbManager defines a Database Manager that is used for a specific
 * domain object type.  It is expected that each domain object (that needs to be
 * persisted) will have its own DomainDbManager that knows how to create and
 * update its respective table.
 * 
 * Each DomainDbManager is injected into the DatabaseManager, who then calls
 * each manager's doCreate and doUpdate when needed.  Each manager can then
 * perform any necessary operations to setup tables needed for its respective
 * domain object.
 *
 * @author Michael Irwin (mikesir87)
 */
public interface DomainDbManager {

  /**
   * Perform any table creates/setup that needs to be done for setup
   * @param writeableDatabase A writeable database
   */
  void doCreate(SQLiteDatabase writeableDatabase);
  
  /**
   * Perform any updates that are needed for this manager
   * @param writeableDatabase A writeable database
   * @param oldVersion The version upgrading from
   * @param newVersion The version upgrading to
   */
  void doUpdate(SQLiteDatabase writeableDatabase, int oldVersion, 
      int newVersion);
  
}
