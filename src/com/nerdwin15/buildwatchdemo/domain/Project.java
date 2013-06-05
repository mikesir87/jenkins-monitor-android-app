package com.nerdwin15.buildwatchdemo.domain;

import com.nerdwin15.buildwatchdemo.domain.Build.Status;

public class Project {

	private Long id;
	private String name;
	private String url;
	private Status status;
	
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
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	/**
	 * Get the project's current status, based on its last build
	 * @return The status of the project
	 */
	public Status getStatus() {
    return status;
  }
	
	/**
	 * Set the status for the project
	 * @param status The current status
	 */
	public void setStatus(Status status) {
    this.status = status;
  }
	
	@Override
	public String toString() {
		return getName();
	}
}
