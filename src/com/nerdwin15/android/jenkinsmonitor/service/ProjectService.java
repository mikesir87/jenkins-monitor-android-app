package com.nerdwin15.android.jenkinsmonitor.service;

import java.util.List;

import com.nerdwin15.android.jenkinsmonitor.domain.JenkinsInstance;
import com.nerdwin15.android.jenkinsmonitor.domain.Project;

/**
 * A service object that is used to interact with the underlying persistence of
 * Project objects.
 * 
 * By making this an interface, allows for various implementations. During UI 
 * development, a mocked implementation was useful. Production use should use
 * a database-backed implementation.
 *
 * @author Michael Irwin (mikesir87)
 */
public interface ProjectService {

  /**
   * Save the provided Project into persistence.
   * @param project The project to save
   * @param instance The JenkinsInstance that the Project belongs to
   */
  void createProject(Project project, JenkinsInstance instance);
  
  /**
   * Retrieve all projects stored in persistence for the provided 
   * JenkinsInstance
   * @param instance The JenkinsInstance to find projects for
   * @return All projects associated with the provided JenkinsInstance
   */
	List<Project> retrieveProjects(JenkinsInstance instance);
	
}
