package com.nerdwin15.buildwatchdemo.domain;

public class JenkinsInstance {

	private Long id;
	private String name;
	private String baseUrl;
	private boolean firewalled;
	private Project[] projects;
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * @return the firewalled
	 */
	public boolean isFirewalled() {
		return firewalled;
	}

	/**
	 * @param firewalled the firewalled to set
	 */
	public void setFirewalled(boolean firewalled) {
		this.firewalled = firewalled;
	}
	
	/**
   * @return the projects
   */
  public Project[] getProjects() {
    return projects;
  }

  /**
   * @param projects the projects to set
   */
  public void setProjects(Project[] projects) {
    this.projects = projects;
  }

  @Override
	public String toString() {
		return getName();
	}
}
