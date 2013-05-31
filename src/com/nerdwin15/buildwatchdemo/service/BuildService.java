package com.nerdwin15.buildwatchdemo.service;

import java.util.List;

import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.Project;

public interface BuildService {

	List<Build> retrieveBuildsForProject(Project project);
	
}
