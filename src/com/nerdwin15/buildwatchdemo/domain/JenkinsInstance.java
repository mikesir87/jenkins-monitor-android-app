package com.nerdwin15.buildwatchdemo.domain;

import java.util.Date;

/**
 * Provides the details for a registered Jenkins Instance.
 *
 * @author Michael Irwin (mikesir87)
 */
public class JenkinsInstance {

	private Long id;
	private String name;
	private Project[] projects;
  private String baseUrl;
	private boolean firewalled;
	private String username;
	private String apiToken;
	private String senderId;
	private Date dateCreated;

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
   * Gets the {@code name} property.
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the {@code name} property.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the {@code projects} property.
   */
  public Project[] getProjects() {
    return projects;
  }

  /**
   * Sets the {@code projects} property.
   */
  public void setProjects(Project[] projects) {
    this.projects = projects;
  }

  /**
   * Gets the {@code baseUrl} property.
   */
  public String getBaseUrl() {
    return baseUrl;
  }

  /**
   * Sets the {@code baseUrl} property.
   */
  public void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  /**
   * Gets the {@code firewalled} property.
   */
  public boolean isFirewalled() {
    return firewalled;
  }

  /**
   * Sets the {@code firewalled} property.
   */
  public void setFirewalled(boolean firewalled) {
    this.firewalled = firewalled;
  }

  /**
   * Gets the {@code username} property.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Sets the {@code username} property.
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the {@code apiToken} property.
   */
  public String getApiToken() {
    return apiToken;
  }

  /**
   * Sets the {@code apiToken} property.
   */
  public void setApiToken(String apiToken) {
    this.apiToken = apiToken;
  }

  /**
   * Gets the {@code senderId} property.
   */
  public String getSenderId() {
    return senderId;
  }

  /**
   * Sets the {@code senderId} property.
   */
  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  /**
   * Gets the {@code dateCreated} property.
   */
  public Date getDateCreated() {
    return dateCreated;
  }

  /**
   * Sets the {@code dateCreated} property.
   */
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof JenkinsInstance)) return false;
    return getId().equals(((JenkinsInstance) o).getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
	public String toString() {
		return getName();
	}
}
