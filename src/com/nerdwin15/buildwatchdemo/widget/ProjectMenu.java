package com.nerdwin15.buildwatchdemo.widget;

import java.util.List;

import android.content.Context;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;
import com.nerdwin15.buildwatchdemo.service.ProjectService;

@Singleton
public class ProjectMenu {

  @Inject
  private ProjectService projectService;
  
  private ProjectAdapter adapter;

  public void setupNavigation(Context context, JenkinsInstance instance, 
      ActionBar actionBar, OnNavigationListener listener) {

    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
    actionBar.setDisplayShowTitleEnabled(false);
    
    List<Project> projects = projectService.retrieveProjects(instance);
    adapter = new ProjectAdapter(context, instance.getName(), projects);
    adapter.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
    
    actionBar.setListNavigationCallbacks(adapter, listener);
    actionBar.setSelectedNavigationItem(0);
  }
  
  public Project getSelected(ActionBar actionBar) {
    return adapter.getItem(actionBar.getSelectedNavigationIndex());
  }
  
  public void toggleDrawer(boolean drawerOpen, ActionBar actionBar, 
      String title) {
     actionBar.setNavigationMode(drawerOpen ? ActionBar.NAVIGATION_MODE_STANDARD 
        : ActionBar.NAVIGATION_MODE_LIST);
    actionBar.setDisplayShowTitleEnabled(drawerOpen);
    actionBar.setTitle(title);
  }
}
