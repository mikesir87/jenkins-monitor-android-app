package com.nerdwin15.buildwatchdemo;

import java.util.List;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.google.inject.Inject;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;
import com.nerdwin15.buildwatchdemo.fragment.BuildHistoryFragment;
import com.nerdwin15.buildwatchdemo.service.JenkinsService;
import com.nerdwin15.buildwatchdemo.widget.DrawerMenu;
import com.nerdwin15.buildwatchdemo.widget.DrawerMenuListener;
import com.nerdwin15.buildwatchdemo.widget.ProjectMenu;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboSherlockFragmentActivity implements
    DrawerMenuListener, OnNavigationListener {

  @Inject
  private DrawerMenu drawerMenu;
  
  @Inject
  private ProjectMenu projectMenu;
  
  @Inject
  private JenkinsService mJenkinsService;
  
  @InjectView(R.id.left_drawer)
  private ListView mDrawerList;
  
  @InjectView(R.id.drawer_layout)
  private DrawerLayout mDrawerLayout;

  private BuildHistoryFragment mHistoryFragment;

  private boolean instanceChanged = false;
  private boolean drawerEnabled = true;
  private List<JenkinsInstance> jenkinsInstances;
  private JenkinsInstance activeInstance;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    jenkinsInstances = mJenkinsService.retrieveJenkinsInstances();
    activeInstance = (jenkinsInstances.size() > 0) ? 
        jenkinsInstances.get(0) : null;

    ActionBar actionBar = getSupportActionBar();
    if (jenkinsInstances.size() <= 1) {
      drawerEnabled = false;
      mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    else {
      actionBar.setDisplayHomeAsUpEnabled(true);
      actionBar.setHomeButtonEnabled(true);
      drawerMenu.initDrawer(mDrawerLayout, mDrawerList, this, this);
    }
    projectMenu.setupNavigation(this, activeInstance, actionBar, this);
  }

  @Override
  protected void onResume() {
    super.onResume();

    FragmentManager fm = getSupportFragmentManager();
    if (fm.findFragmentByTag("fragment") == null || mHistoryFragment == null) {
      mHistoryFragment = new BuildHistoryFragment();
      Log.i("fragment", "Adding fragment");
      FragmentTransaction ft = fm.beginTransaction();
      ft.add(R.id.content_frame, mHistoryFragment, "fragment").commit();
    }
    
    Project project = projectMenu.getSelected(getSupportActionBar());
    mHistoryFragment.setActiveProject(project);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        if (drawerEnabled)
          drawerMenu.toggle(mDrawerLayout);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected(int itemPosition, long itemId) {
    Project project = projectMenu.getSelected(getSupportActionBar());
    mHistoryFragment.setActiveProject(project);
    return true;
  }

  @Override
  public void onDrawerOpened() {
    projectMenu.toggleDrawer(true, getSupportActionBar(), 
        getString(R.string.drawer_title_opened));
  }

  @Override
  public void onDrawerClosed() {
    projectMenu.toggleDrawer(false, getSupportActionBar(), 
        activeInstance.getName());
    if (instanceChanged) {
      projectMenu.setupNavigation(this, activeInstance, getSupportActionBar(), 
          this);
    }
  }
  
  @Override
  public void onDrawerItemClicked(int position) {
    JenkinsInstance instance = jenkinsInstances.get(position);
    instanceChanged = !instance.equals(activeInstance);
    activeInstance = instance;
    if (instanceChanged) {
      mHistoryFragment.toggleLoadingDisplay(true);
    }
  }
  
}
