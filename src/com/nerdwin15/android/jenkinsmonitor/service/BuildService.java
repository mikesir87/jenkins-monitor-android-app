package com.nerdwin15.android.jenkinsmonitor.service;

import java.util.List;

import com.nerdwin15.android.jenkinsmonitor.domain.Build;
import com.nerdwin15.android.jenkinsmonitor.domain.Project;

/**
 * A service object that is used to interact with the underlying persistence of
 * Build objects.
 * 
 * By making this an interface, allows for various implementations. During UI 
 * development, a mocked implementation was useful. Production use should use
 * a database-backed implementation.
 *
 * @author Michael Irwin (mikesir87)
 */
public interface BuildService {

  /**
   * Stores the provided Build into persistence. The id of the Build should be
   * set when created.
   * @param build The Build to save into persistence.
   * @param project 
   */
  void createBuild(Build build, Project project);
  
  /**
   * Retrieve all Builds associated with the provided Project.
   * @param project The Project to find Builds for.
   * @return All Builds associated with the provided Project.
   */
	List<Build> retrieveBuildsForProject(Project project);
	
}
