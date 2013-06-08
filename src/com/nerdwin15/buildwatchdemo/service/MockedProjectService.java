package com.nerdwin15.buildwatchdemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.nerdwin15.buildwatchdemo.domain.Build.Status;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;

/**
 * An implementation of the {@link ProjectService} that uses an in-memory
 * representation of Projects.  During construction, two projects are created.
 *
 * @author Michael Irwin (mikesir87)
 */
public class MockedProjectService implements ProjectService {

  private Long lastUsedId = 12L;
  private Random random = new Random();
  private List<Project> projects;
  
  /**
   * Construct a default instance with two projects
   */
  public MockedProjectService() {
    List<Project> projects = new ArrayList<Project>();
    projects.add(createProject("BuildWatch"));
    projects.add(createProject("GCM-Notifier"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void createProject(Project project, JenkinsInstance instance) {
    projects.add(project);
  }
  
  /**
   * {@inheritDoc}
   */
	@Override
	public List<Project> retrieveProjects(JenkinsInstance instance) {
		return projects;
	}
	
	private Project createProject(String name) {
		Project project = new Project();
		project.setId(lastUsedId++);
		project.setName(name);
		project.setStatus(getStatus());
		return project;
	}
	
	private Status getStatus() {
	  switch (random.nextInt(3)) {
	  case 0:
	    return Status.FAILURE;
	  case 1:
	    return Status.WARNING;
	  case 2:
	    return Status.SUCCESS;
	  }
	  return Status.SUCCESS;
	}
	
}
