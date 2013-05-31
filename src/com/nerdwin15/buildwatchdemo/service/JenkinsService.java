package com.nerdwin15.buildwatchdemo.service;

import java.util.List;

import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;

public interface JenkinsService {

	List<JenkinsInstance> retrieveJenkinsInstances();
	
}
