package com.nerdwin15.android.jenkinsmonitor.domain.db;

import com.google.inject.Inject;
import com.nerdwin15.android.jenkinsmonitor.db.ProjectDomainDbManager;
import com.nerdwin15.android.jenkinsmonitor.domain.JenkinsInstance;
import com.nerdwin15.android.jenkinsmonitor.domain.Project;

/**
 * An enhanced version of the JenkinsInstance that performs lazy-fetching on the
 * Projects.
 *
 * @author Michael Irwin (mikesir87)
 */
public class DbEnhancedJenkinsInstance extends JenkinsInstance {

  @Inject
  private ProjectDomainDbManager projectManager;
  
  /**
   * {@inheritDoc}
   * 
   * If the projects have yet to be retrieved, retrieves them first and returns
   * them.
   */
  @Override
  public Project[] getProjects() {
    if (super.getProjects() == null) {
      setProjects(
          projectManager.retrieveProjects(this).toArray(new Project[0])
      );
    }
    return super.getProjects();
  }
  
}
