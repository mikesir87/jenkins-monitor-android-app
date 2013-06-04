package com.nerdwin15.buildwatchdemo.widget;

import android.content.Context;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.nerdwin15.buildwatchdemo.R;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.service.JenkinsService;

/**
 * Widget that helps initialize and handle the DrawerMenu
 * 
 * @author Michael Irwin
 */
@Singleton
public class DrawerMenu {

  @Inject
  private Context context;
  
  @Inject
  private JenkinsService jenkinsService;

  public ActionBarDrawerToggle initDrawer(DrawerLayout drawerLayout, 
      ListView drawerMenuListView,
      final SherlockFragmentActivity activity,
      final DrawerMenuListener drawerMenuListener) {

    activity.supportInvalidateOptionsMenu();
    //drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    
    ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(activity,
        drawerLayout, R.drawable.ic_drawer, R.string.drawer_open,
        R.string.drawer_close) {

      public void onDrawerClosed(View view) {
        if (drawerMenuListener != null) {
          drawerMenuListener.onDrawerClosed();
        }
        activity.supportInvalidateOptionsMenu();
      }

      public void onDrawerOpened(View drawerView) {
        if (drawerMenuListener != null) {
          drawerMenuListener.onDrawerOpened();
        }
        activity.supportInvalidateOptionsMenu();
      }
    };
    
    drawerLayout.setDrawerListener(drawerToggle);
    createDrawerView(drawerLayout, drawerMenuListView, 
        drawerMenuListener);
    return drawerToggle;
  }

  private void createDrawerView(final DrawerLayout drawerLayout, 
      final ListView drawerMenuListView,
      final DrawerMenuListener drawerMenuListener) {

    ListAdapter mDrawerAdapter = new ArrayAdapter<JenkinsInstance>(context,
        R.layout.drawer_list_item, jenkinsService.retrieveJenkinsInstances());
    drawerMenuListView.setAdapter(mDrawerAdapter);
    drawerMenuListView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position,
          long id) {
        drawerMenuListView.setSelection(position);
        if (drawerMenuListener != null) {
          drawerMenuListener.onDrawerItemClicked(position);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
      }
    });
  }

  public void toggle(DrawerLayout drawerLayout) {
      if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
          drawerLayout.closeDrawer(GravityCompat.START);
      } else {
          drawerLayout.openDrawer(GravityCompat.START);
      }
  }
}
