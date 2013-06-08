package com.nerdwin15.android.jenkinsmonitor.domain;

import java.util.Date;

/**
 * Provides details about a specific commit that was involved with a particular
 * Build.
 *
 * @author Michael Irwin (mikesir87)
 */
public class CommitInfo {

  private Long id;
  private String committer;
  private String message;
  private Date timestamp;
  
  /**
   * Gets the {@code id} property.
   */
  public Long getId() {
    return id;
  }
  
  /**
   * Sets the {@code id} property.
   */
  public void setId(Long id) {
    this.id = id;
  }
  
  /**
   * Gets the {@code committer} property.
   */
  public String getCommitter() {
    return committer;
  }
  
  /**
   * Sets the {@code committer} property.
   */
  public void setCommitter(String committer) {
    this.committer = committer;
  }
  
  /**
   * Gets the {@code message} property.
   */
  public String getMessage() {
    return message;
  }
  
  /**
   * Sets the {@code message} property.
   */
  public void setMessage(String message) {
    this.message = message;
  }
  
  /**
   * Gets the {@code timestamp} property.
   */
  public Date getTimestamp() {
    return timestamp;
  }
  
  /**
   * Sets the {@code timestamp} property.
   */
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof CommitInfo)) return false;
    return getId().equals(((CommitInfo) o).getId());
  }
  
}
