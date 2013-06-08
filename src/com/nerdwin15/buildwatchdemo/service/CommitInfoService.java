package com.nerdwin15.buildwatchdemo.service;

import java.util.List;

import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.CommitInfo;

/**
 * A service object that is used to interact with the underlying persistence of
 * CommitInfo objects.
 * 
 * By making this an interface, allows for various implementations. During UI 
 * development, a mocked implementation was useful. Production use should use
 * a database-backed implementation.
 *
 * @author Michael Irwin (mikesir87)
 */
public interface CommitInfoService {

  /**
   * Save the provided CommitInfo into persistence. The id of the CommitInfo 
   * should be set when created.
   * @param commit The commit to be saved.
   * @param build The Build that the commit is associated with.
   */
  void createCommit(CommitInfo commit, Build build);
  
  /**
   * Retrieve all Commits associated with a particular Build.
   * @param build The Build to find Commits for.
   * @return The Commits associated with a particular build.
   */
  List<CommitInfo> retrieveCommits(Build build);
  
}
