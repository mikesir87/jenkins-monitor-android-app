package com.nerdwin15.buildwatchdemo.service;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.buildwatchdemo.db.CommitInfoDomainDbManager;
import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.CommitInfo;

/**
 * An implementation of the {@link CommitInfoService} that uses a database
 * backend for persistence.
 *
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class DbCommitInfoService implements CommitInfoService {

  @Inject
  private CommitInfoDomainDbManager commitDbManager;

  /**
   * {@inheritDoc}
   */
  @Override
  public void createCommit(CommitInfo commit, Build build) {
    commitDbManager.create(commit, build);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public List<CommitInfo> retrieveCommits(Build build) {
    return commitDbManager.retrieveCommits(build);
  }
  
}
