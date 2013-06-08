package com.nerdwin15.buildwatchdemo.task;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.google.inject.Inject;
import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.CommitInfo;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;
import com.nerdwin15.buildwatchdemo.service.BuildService;
import com.nerdwin15.buildwatchdemo.service.CommitInfoService;
import com.nerdwin15.buildwatchdemo.service.JenkinsInstanceService;
import com.nerdwin15.buildwatchdemo.service.ProjectService;
import com.nerdwin15.buildwatchdemo.util.JenkinsHttpClientFactory;

public class ConfigurationTask extends AsyncTask<JenkinsInstance, Void, 
    TaskResult<JenkinsInstance>> {

  @Inject
  private Context context;
  
  @Inject
  private JenkinsInstanceService jenkinsService;
  
  @Inject
  private ProjectService projectService;
  
  @Inject
  private BuildService buildService;
  
  @Inject
  private CommitInfoService commitService;
  
  @Inject
  private JenkinsHttpClientFactory httpClientFactory;
  
  private ConfigurationTaskCallback callback;
  
  @Override
  protected TaskResult<JenkinsInstance> doInBackground(JenkinsInstance... params) {
    try {
      JenkinsInstance jenkinsInstance = params[0];

      HttpClient client = httpClientFactory.getHttpClient(jenkinsInstance);
      registerWithJenkins(client, jenkinsInstance);
      List<Project> projects = getProjects(client, jenkinsInstance);
      jenkinsService.createJenkinsInstance(jenkinsInstance);
      for (Project project : projects) {
        projectService.createProject(project, jenkinsInstance);
        for (Build build: project.getBuilds()) {
          buildService.createBuild(build, project);
          for (CommitInfo commit : build.getCommits())
            commitService.createCommit(commit, build);
        }
      }
      
      return new TaskResult<JenkinsInstance>(jenkinsInstance);
    } catch (Exception e) {
      Log.e("configuration", "Error occurred: ", e);
      return new TaskResult<JenkinsInstance>(e);
    }
  }
  
  @Override
  protected void onPostExecute(TaskResult<JenkinsInstance> result) {
    if (result.errorOccurred())
      callback.configurationFailed("Error occurred: " + 
          result.getError().getMessage());
    else
      callback.configurationComplete(result.getResult());
  }
  
  private void registerWithJenkins(HttpClient client, JenkinsInstance instance)
      throws Exception {
    String gcmToken = getGcmToken(instance.getSenderId());
    HttpPost post = new HttpPost(instance.getBaseUrl() + "/gcm/register");
    
    List<NameValuePair> postParams = new ArrayList<NameValuePair>();
    postParams.add(new BasicNameValuePair("token", gcmToken));
    post.setEntity(new UrlEncodedFormEntity(postParams));
    String authCode = Base64.encodeToString(
        new String(instance.getUsername() + ":" + instance.getApiToken()).getBytes(), 
        Base64.URL_SAFE | Base64.NO_WRAP);
    post.addHeader("Authorization", "Basic " + authCode);
    
    
    HttpResponse response = client.execute(post);

    int statusCode = response.getStatusLine().getStatusCode();
    boolean success = statusCode >= 200 && statusCode < 400;
    if (!success)
      throw new Exception("Got a " + statusCode + " from Jenkins");
  }
  
  private List<Project> getProjects(HttpClient client, 
      JenkinsInstance instance) throws Exception {
    List<Project> projects = new ArrayList<Project>();
    HttpGet request = new HttpGet(instance.getBaseUrl() + "/api/json");
    String authCode = Base64.encodeToString(
        new String(instance.getUsername() + ":" + instance.getApiToken()).getBytes(), 
        Base64.URL_SAFE | Base64.NO_WRAP);
    request.addHeader("Authorization", "Basic " + authCode);
    
    HttpResponse resp = client.execute(request);
    JSONObject data = new JSONObject(EntityUtils.toString(resp.getEntity()));
    JSONArray jobs = data.getJSONArray("jobs");

    Log.i("jobs", "Jenkins has " + jobs.length() + " jobs");
    for (int i = 0; i < jobs.length(); i++) {
      JSONObject job = jobs.getJSONObject(i);
      Project project = new Project();
      project.setName(job.getString("name"));
      project.setUrl(job.getString("url"));
      populateStatusAndLastCommit(project, client, instance);
      projects.add(project);
    }
    return projects;
  }
  
  private void populateStatusAndLastCommit(Project project, HttpClient client,
      JenkinsInstance instance) throws Exception {
    HttpGet get = new HttpGet(project.getUrl() + "/api/json");
    String authCode = Base64.encodeToString(
        new String(instance.getUsername() + ":" + instance.getApiToken()).getBytes(), 
        Base64.URL_SAFE | Base64.NO_WRAP);
    get.addHeader("Authorization", "Basic " + authCode);
    HttpResponse resp = client.execute(get);
    JSONObject data = new JSONObject(EntityUtils.toString(resp.getEntity()));
    String color = (data.has("color")) ? data.getString("color") : "";
    if (color.equals("blue"))
      project.setStatus(Build.Status.SUCCESS);
    else if (color.equals("red"))
      project.setStatus(Build.Status.FAILURE);
    else if (color.equals("yellow"))
      project.setStatus(Build.Status.WARNING);
    
    JSONArray builds = data.getJSONArray("builds");
    if (builds.length() == 0)
      return;
    
    get.setURI(URI.create(builds.getJSONObject(0).getString("url") + "/api/json"));
    resp = client.execute(get);
    data = new JSONObject(EntityUtils.toString(resp.getEntity()));
    
    Build build = new Build();
    build.setDate(new Date(data.getLong("timestamp")));
    build.setStatus(Build.Status.valueOf(data.getString("result")));
    
    List<CommitInfo> commits = new ArrayList<CommitInfo>();
    if (data.has("changeSet")) {
      JSONArray changeSet = data.getJSONObject("changeSet").getJSONArray("items");
      for (int i = 0 ; i < changeSet.length(); i++) {
        JSONObject commitData = changeSet.getJSONObject(i);
        
        CommitInfo info = new CommitInfo();
        info.setCommitter(commitData.getJSONObject("author").getString("fullName"));
        info.setMessage(commitData.getString("msg"));
        info.setTimestamp(new Date(commitData.getLong("timestamp")));
        
        commits.add(info);
      }
    }
    build.setCommits(commits.toArray(new CommitInfo[0]));
    project.setBuilds(new Build[]{build});
  }
  
  private String getGcmToken(String senderId) {
    GCMRegistrar.register(context, senderId);
    while (true) {
      if (GCMRegistrar.isRegistered(context))
        return GCMRegistrar.getRegistrationId(context);
      try { Thread.sleep(500); } catch (InterruptedException e) {}
    }
  }
  
  public void setCallback(ConfigurationTaskCallback callback) {
    this.callback = callback;
  }
  
  public interface ConfigurationTaskCallback {
    void configurationComplete(JenkinsInstance instance);
    void configurationFailed(String message);
  }
  
}
