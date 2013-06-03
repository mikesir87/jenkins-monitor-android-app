package com.nerdwin15.buildwatchdemo.service;

import java.util.ArrayList;
import java.util.List;

import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;

public class MockedProjectService implements ProjectService {

  private Long lastUsedId = 12L;
  
	@Override
	public List<Project> retrieveProjects(JenkinsInstance instance) {
		List<Project> projects = new ArrayList<Project>();
		projects.add(createProject("Hello Git - " + instance.getName().substring(0, 3)));
		projects.add(createProject("BuildWatch"));
		projects.add(createProject("GCM-Notifier"));
		return projects;
	}
	
	private Project createProject(String name) {
		Project project = new Project();
		project.setId(lastUsedId++);
		project.setName(name);
		return project;
	}
	
}
