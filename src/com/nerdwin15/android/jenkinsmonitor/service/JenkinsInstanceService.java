package com.nerdwin15.android.jenkinsmonitor.service;

import java.util.List;

import com.nerdwin15.android.jenkinsmonitor.domain.JenkinsInstance;

/**
 * A service object that is used to interact with the underlying persistence of
 * JenkinsInstance objects.
 * 
 * By making this an interface, allows for various implementations. During UI 
 * development, a mocked implementation was useful. Production use should use
 * a database-backed implementation.
 *
 * @author Michael Irwin (mikesir87)
 */
public interface JenkinsInstanceService {

  /**
   * Retrieve all JenkinsInstances stored in persistence.
   * @return All known JenkinsInstances stored in persistence
   */
	List<JenkinsInstance> retrieveJenkinsInstances();
	
	/**
	 * Retrieve the JenkinsInstance that has the provided baseUrl. 
	 * @param baseUrl The baseUrl to be used for searching
	 * @return The matching JenkinsInstance, if found. Otherwise, null.
	 */
	JenkinsInstance retrieveInstanceByBaseUrl(String baseUrl);
	
	/**
	 * Save the provided JenkinsInstance into persistence.  The id of the 
	 * instance will be populated.
	 * @param instance The instance to save
	 */
	void createJenkinsInstance(JenkinsInstance instance);
	
	/**
	 * Update the provided JenkinsInstance that already exists in persistence.
	 * @param instance The instance that will be updated
	 */
	void update(JenkinsInstance instance);
	
}
