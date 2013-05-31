package com.nerdwin15.buildwatchdemo.service;

import java.util.List;

import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;

public interface ProjectService {

	List<Project> retrieveProjects(JenkinsInstance instance);
	
}
