package com.nerdwin15.android.jenkinsmonitor.domain.db;

import com.google.inject.Inject;
import com.nerdwin15.android.jenkinsmonitor.db.BuildDomainDbManager;
import com.nerdwin15.android.jenkinsmonitor.domain.Build;
import com.nerdwin15.android.jenkinsmonitor.domain.Project;

/**
 * An enhanced version of the Project that performs lazy-fetching on the
 * Builds.
 *
 * @author Michael Irwin (mikesir87)
 */
public class DbEnhancedProject extends Project {

  @Inject
  private BuildDomainDbManager buildManager;

  /**
   * {@inheritDoc}
   * 
   * If the Builds haven't been retrieved from the Database yet, they are first
   * retrieved and then returned.
   */
  @Override
  public Build[] getBuilds() {
    if (super.getBuilds() == null)
      setBuilds( buildManager.retrieveBuilds(this).toArray(new Build[0]) );
    return super.getBuilds();
  }
  
}
