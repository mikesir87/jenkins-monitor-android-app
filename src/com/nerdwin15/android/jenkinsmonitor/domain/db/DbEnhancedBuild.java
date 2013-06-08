package com.nerdwin15.android.jenkinsmonitor.domain.db;

import com.google.inject.Inject;
import com.nerdwin15.android.jenkinsmonitor.db.CommitInfoDomainDbManager;
import com.nerdwin15.android.jenkinsmonitor.domain.Build;
import com.nerdwin15.android.jenkinsmonitor.domain.CommitInfo;

/**
 * An extended version of the Build that basically does lazy-fetching on the
 * commit details.
 *
 * @author Michael Irwin (mikesir87)
 */
public class DbEnhancedBuild extends Build {

  @Inject
  private CommitInfoDomainDbManager commitManager;

  /**
   * {@inheritDoc}
   * 
   * If the commits hasn't been retrieved yet, it fetches them first.
   */
  @Override
  public CommitInfo[] getCommits() {
    if (super.getCommits() == null) {
      setCommits( 
          commitManager.retrieveCommits(this).toArray(new CommitInfo[0]) 
      );
    }
    return super.getCommits();
  }
  
}
