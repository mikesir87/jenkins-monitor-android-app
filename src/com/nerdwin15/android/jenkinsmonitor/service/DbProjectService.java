package com.nerdwin15.android.jenkinsmonitor.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.android.jenkinsmonitor.db.ProjectDomainDbManager;
import com.nerdwin15.android.jenkinsmonitor.domain.JenkinsInstance;
import com.nerdwin15.android.jenkinsmonitor.domain.Project;

/**
 * An implementation of the {@link ProjectService} that uses a database backend
 * for persistence.
 *
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class DbProjectService implements ProjectService {

  @Inject
  private ProjectDomainDbManager projectManager;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createProject(Project project, JenkinsInstance instance) {
    projectManager.create(project, instance);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Project> retrieveProjects(JenkinsInstance instance) {
    return projectManager.retrieveProjects(instance);
  }

}
