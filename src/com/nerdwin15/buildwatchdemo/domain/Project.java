package com.nerdwin15.buildwatchdemo.domain;

import com.nerdwin15.buildwatchdemo.domain.Build.Status;

/**
 * Describes a particular Project for a Jenkins Instance.  Jenkins calls these
 * "Jobs", but I think Project sounds better.
 *
 * @author Michael Irwin (mikesir87)
 */
public class Project {

	private Long id;
	private String name;
	private String url;
	private Status status;
	private Build[] builds;

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
   * Gets the {@code builds} property.
   */
  public Build[] getBuilds() {
    return builds;
  }

  /**
   * Sets the {@code builds} property.
   */
  public void setBuilds(Build[] builds) {
    this.builds = builds;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof Project)) return false;
    return getId().equals(((Project) o).getId());
  }

  /**
   * {@inheritDoc}
   */
  @Override
	public String toString() {
		return getName();
	}
}
