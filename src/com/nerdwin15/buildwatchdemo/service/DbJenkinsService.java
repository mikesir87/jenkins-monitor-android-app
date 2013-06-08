package com.nerdwin15.buildwatchdemo.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.buildwatchdemo.db.JenkinsInstanceDomainDbManager;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;

/**
 * An implementation of the {@link JenkinsInstanceService} that uses a database backend
 * for persistence
 * 
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class DbJenkinsService implements JenkinsInstanceService {

  @Inject
  private JenkinsInstanceDomainDbManager instanceManager;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createJenkinsInstance(JenkinsInstance instance) {
    instanceManager.create(instance);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<JenkinsInstance> retrieveJenkinsInstances() {
    return instanceManager.retrieveJenkinsInstances();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public JenkinsInstance retrieveInstanceByBaseUrl(String baseUrl) {
    return instanceManager.retrieveJenkinsInstanceByBaseUrl(baseUrl);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update(JenkinsInstance instance) {
    instanceManager.update(instance);
  }
  
}
