package com.nerdwin15.buildwatchdemo.domain;

import java.util.Date;

public class Build {

	public enum Status {
		SUCCESS,
		FAILURE,
		WARNING,
		IN_PROGRESS
	}
	
	private Long id;
	private Date date;
	private Status status;
	private String message;
	private CommitInfo[] commits;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public CommitInfo[] getCommits() {
    return commits;
  }
	
	public void setCommits(CommitInfo[] commits) {
    this.commits = commits;
  }
	
}
