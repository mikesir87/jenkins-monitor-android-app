package com.nerdwin15.buildwatchdemo.domain.db;

import com.google.inject.Inject;
import com.nerdwin15.buildwatchdemo.db.ProjectDomainDbManager;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;

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
