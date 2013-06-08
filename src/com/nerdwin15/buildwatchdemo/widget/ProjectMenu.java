package com.nerdwin15.buildwatchdemo.widget;

import java.util.List;

import android.content.Context;

import com.actionbarsherlock.R;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.buildwatchdemo.adapter.ProjectAdapter;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;
import com.nerdwin15.buildwatchdemo.service.ProjectService;

/**
 * A widget that helps in setting up of the top-level project menu/spinner.
 *
 * @author Michael Irwin (mikesir87)
 */
@Singleton
public class ProjectMenu {

  @Inject
  private ProjectService projectService;
  
  private ProjectAdapter adapter;

  /**
   * Performs setup to get the spinner into the ActionBar
   * @param context The Android context
   * @param instance The JenkinsInstance being used
   * @param actionBar The ActionBar
   * @param listener A callback to notify of navigation events
   */
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
  
  /**
   * Toggle whether the ActionBar displays the spinner or whether it displays
   * only a title. This is used when the drawer slides out and back in.
   * @param hideSpinner True if the spinner should be hidden and only the title
   * is displayed
   * @param actionBar The ActionBar to work on
   * @param title The title that should be displayed
   */
  public void toggleActionBar(boolean hideSpinner, ActionBar actionBar, 
      String title) {
     actionBar.setNavigationMode(hideSpinner ? ActionBar.NAVIGATION_MODE_STANDARD 
        : ActionBar.NAVIGATION_MODE_LIST);
    actionBar.setDisplayShowTitleEnabled(hideSpinner);
    actionBar.setTitle(title);
  }
}
