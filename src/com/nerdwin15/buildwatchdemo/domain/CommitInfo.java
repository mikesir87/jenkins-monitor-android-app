package com.nerdwin15.buildwatchdemo.domain;

public class CommitInfo {

  private String committer;
  private String message;
  
  public CommitInfo(String committer, String message) {
    this.committer = committer;
    this.message = message;
  }
  
  public String getCommitter() {
    return committer;
  }
  
  public void setCommitter(String committer) {
    this.committer = committer;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
}
