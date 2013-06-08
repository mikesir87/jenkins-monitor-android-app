package com.nerdwin15.buildwatchdemo.domain;

import java.util.Date;

/**
 * A build represents a single build that was performed on the Jenkins server.
 *
 * @author Michael Irwin (mikesir87)
 */
public class Build {

  /**
   * Provides the status of the build
   *
   * @author Michael Irwin (mikesir87)
   */
	public enum Status {
	  
	  /**
	   * The build was successful
	   */
		SUCCESS,
		
		/**
		 * The build was a failure
		 */
		FAILURE,
		
		/**
		 * The build succeeded, but had warnings associated with it
		 */
		WARNING,
		
		/**
		 * The build is still in progress and does not have a final status yet
		 */
		IN_PROGRESS
	}
	
	private Long id;
	private Long buildNumber;
	private String url;
	private Date date;
	private Status status;
	private String message;
	private CommitInfo[] commits;
  
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
   * Gets the {@code buildNumber} property.
   */
  public Long getBuildNumber() {
    return buildNumber;
  }
  
  /**
   * Sets the {@code buildNumber} property.
   */
  public void setBuildNumber(Long buildNumber) {
    this.buildNumber = buildNumber;
  }
  
  /**
   * Gets the {@code url} property.
   */
  public String getUrl() {
    return url;
  }
  
  /**
   * Sets the {@code url} property.
   */
  public void setUrl(String url) {
    this.url = url;
  }
  
  /**
   * Gets the {@code date} property.
   */
  public Date getDate() {
    return date;
  }
  
  /**
   * Sets the {@code date} property.
   */
  public void setDate(Date date) {
    this.date = date;
  }
  
  /**
   * Gets the {@code status} property.
   */
  public Status getStatus() {
    return status;
  }
  
  /**
   * Sets the {@code status} property.
   */
  public void setStatus(Status status) {
    this.status = status;
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
   * Gets the {@code commits} property.
   */
  public CommitInfo[] getCommits() {
    return commits;
  }
  
  /**
   * Sets the {@code commits} property.
   */
  public void setCommits(CommitInfo[] commits) {
    this.commits = commits;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Build)) return false;
    return getId().equals(((Build) o).getId());
  }
  
}
