package com.nerdwin15.buildwatchdemo.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.buildwatchdemo.db.BuildDomainDbManager;
import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.Project;

/**
 * An implementation of the {@link BuildService} that uses a database backend
 * to provide persistence.
 *
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class DbBuildService implements BuildService {

  @Inject
  private BuildDomainDbManager buildManager;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createBuild(Build build, Project project) {
    buildManager.create(build, project);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<Build> retrieveBuildsForProject(Project project) {
    return buildManager.retrieveBuilds(project);
  }
  
}
