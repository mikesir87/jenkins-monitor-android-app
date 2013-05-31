package com.nerdwin15.buildwatchdemo;

import java.util.List;

import roboguice.inject.InjectView;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.google.inject.Inject;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.fragment.BuildHistoryFragment;
import com.nerdwin15.buildwatchdemo.service.JenkinsService;
import com.nerdwin15.buildwatchdemo.util.MenuItemUtil;

public class MainActivity extends RoboSherlockFragmentActivity implements OnItemClickListener {

  private @InjectView(R.id.left_drawer) ListView mDrawerList;
  private @InjectView(R.id.drawer_layout) DrawerLayout mDrawerLayout;

  private ActionBarDrawerToggle mDrawerToggle;
  private ArrayAdapter<JenkinsInstance> mDrawerAdapter;
  private JenkinsService mJenkinsService;
  private BuildHistoryFragment mHistoryFragment;

  private List<JenkinsInstance> jenkinsInstances;
  private JenkinsInstance activeInstance;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    jenkinsInstances = mJenkinsService.retrieveJenkinsInstances();

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    mDrawerAdapter = new ArrayAdapter<JenkinsInstance>(this,
        R.layout.drawer_list_item, jenkinsInstances);
    mDrawerList.setAdapter(mDrawerAdapter);
    mDrawerList.setOnItemClickListener(this);

    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
        R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {
      public void onDrawerClosed(View view) {
        getSupportActionBar().setTitle("Title");
        supportInvalidateOptionsMenu();
      }

      public void onDrawerOpened(View drawerView) {
        getSupportActionBar().setTitle("TITLE");
        supportInvalidateOptionsMenu();
      }
    };

    mDrawerLayout
        .setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    mDrawerLayout.setDrawerListener(mDrawerToggle);
    
    activeInstance = (jenkinsInstances.size() > 0) 
        ? jenkinsInstances.get(0) : null;
  }

  @Override
  protected void onResume() {
    super.onResume();

    FragmentManager fm = getSupportFragmentManager();
    if (fm.findFragmentByTag("fragment") == null) {
      mHistoryFragment = new BuildHistoryFragment();
      Log.i("fragment", "Adding fragment");
      FragmentTransaction ft = fm.beginTransaction();
      ft.add(R.id.content_frame, mHistoryFragment, "fragment").commit();
    }
    mHistoryFragment.setSelectedInstance(activeInstance);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    setupActionBar();
    MenuInflater inflater = getSupportMenuInflater();
    inflater.inflate(R.menu.main_one_instance, menu);
    return true;
  }

  public boolean onOptionsItemSelected(MenuItem item) {
    if (mDrawerToggle.onOptionsItemSelected(MenuItemUtil.get(item))) {
      return true;
    }
    return false;
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mDrawerToggle.syncState();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
  }

  private void setupActionBar() {
    ActionBar actionBar = getSupportActionBar();
    actionBar.setTitle(activeInstance.getName());
  }

  @Inject
  public void setJenkinsService(JenkinsService mJenkinsService) {
    this.mJenkinsService = mJenkinsService;
  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    activeInstance = jenkinsInstances.get(position);
    mDrawerList.setItemChecked(position, true);
    getSupportActionBar().setTitle(activeInstance.getName());
    mHistoryFragment.setSelectedInstance(activeInstance);
    mDrawerLayout.closeDrawer(mDrawerList);
  }

}
