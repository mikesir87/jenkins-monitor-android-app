package com.nerdwin15.buildwatchdemo.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.buildwatchdemo.db.ProjectDomainDbManager;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;

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
