package com.nerdwin15.buildwatchdemo.service;

import java.util.ArrayList;
import java.util.List;

import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;

public class MockedMultipleInstanceJenkinsService implements JenkinsService {

	private List<JenkinsInstance> jenkins;
	
	public MockedMultipleInstanceJenkinsService() {
		jenkins = new ArrayList<JenkinsInstance>();
		JenkinsInstance instance = new JenkinsInstance();
		instance.setName("Mocked Instance");
		instance.setBaseUrl("http://192.168.1.111/jenkins/");
		jenkins.add(instance);
		
		instance = new JenkinsInstance();
		instance.setName("Other Instance");
		instance.setBaseUrl("http://192.168.1.110/jenkins/");
		jenkins.add(instance);
	}
	
	@Override
	public List<JenkinsInstance> retrieveJenkinsInstances() {
		return jenkins;
	}
	
}
