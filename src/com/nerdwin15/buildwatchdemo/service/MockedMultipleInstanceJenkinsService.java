package com.nerdwin15.buildwatchdemo.service;

import java.util.ArrayList;
import java.util.List;

import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;

/**
 * An implementation of the JenkinsInstanceService that does everything with an
 * in-memory representation. This version creates multiple Jenkins Instances and
 * is used to test and validate that the slide-out drawer is used and is 
 * configured correctly.
 *
 * @author Michael Irwin (mikesir87)
 */
public class MockedMultipleInstanceJenkinsService implements JenkinsInstanceService {

	private List<JenkinsInstance> jenkins;
	private Long nextId = 1L;
	
	/**
	 * Creates the service and creates two instances to use.
	 */
	public MockedMultipleInstanceJenkinsService() {
		jenkins = new ArrayList<JenkinsInstance>();
		JenkinsInstance instance = new JenkinsInstance();
		instance.setId(nextId++);
		instance.setName("Mocked Instance");
		instance.setBaseUrl("http://192.168.1.111/jenkins/");
		jenkins.add(instance);
		
		instance = new JenkinsInstance();
    instance.setId(nextId++);
		instance.setName("Other Instance");
		instance.setBaseUrl("http://192.168.1.110/jenkins/");
		jenkins.add(instance);
	}
  
	/**
	 * {@inheritDoc}
	 */
  @Override
  public void createJenkinsInstance(JenkinsInstance instance) {
    instance.setId(nextId++);
    jenkins.add(instance);
  }
	
  /**
   * {@inheritDoc}
   */
	@Override
	public List<JenkinsInstance> retrieveJenkinsInstances() {
		return jenkins;
	}
	
  /**
   * {@inheritDoc}
   */
	@Override
	public JenkinsInstance retrieveInstanceByBaseUrl(String baseUrl) {
	  for (JenkinsInstance instance : jenkins) {
	    if (instance.getBaseUrl().equals(baseUrl))
	      return instance;
	  }
	  return null;
	}
	
  /**
   * {@inheritDoc}
   */
	@Override
	public void update(JenkinsInstance instance) {
	  jenkins.remove(instance);
	  jenkins.add(instance);
	}
	
}
