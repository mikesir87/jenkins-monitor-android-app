package com.nerdwin15.android.jenkinsmonitor.service;

import java.util.ArrayList;
import java.util.List;

import com.nerdwin15.android.jenkinsmonitor.domain.JenkinsInstance;

/**
 * An implementation of the {@link JenkinsInstanceService} that uses in-memory
 * lists to store the various instances.  This implementation starts with a 
 * single instance and is used to validate that the side drawer is not used in
 * the UI.
 *
 * @author Michael Irwin (mikesir87)
 */
public class MockedSingleInstanceJenkinsService implements JenkinsInstanceService {

	private List<JenkinsInstance> jenkins;
	private Long nextId = 1L;
	
	/**
	 * Constructs a default instance that has one instance configured
	 */
	public MockedSingleInstanceJenkinsService() {
		jenkins = new ArrayList<JenkinsInstance>();
		JenkinsInstance instance = new JenkinsInstance();
		instance.setId(nextId++);
		instance.setName("Mocked Instance");
		instance.setBaseUrl("http://192.168.1.111/jenkins/");
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
