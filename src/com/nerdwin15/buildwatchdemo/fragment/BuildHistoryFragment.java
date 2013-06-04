package com.nerdwin15.buildwatchdemo.fragment;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.google.inject.Inject;
import com.nerdwin15.buildwatchdemo.R;
import com.nerdwin15.buildwatchdemo.adapter.BuildHistoryListAdapter;
import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.Project;
import com.nerdwin15.buildwatchdemo.service.BuildService;

public class BuildHistoryFragment extends RoboSherlockFragment {

  @Inject
  private BuildService buildService;
  
  @InjectView(R.id.build_history_list) 
	private ListView mListView;

  private boolean setupCompleted;
	private Project currentProject;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_build_history, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	  super.onViewCreated(view, savedInstanceState);
	  setupCompleted = true;
	  setupProject();
	}
	
	public void setActiveProject(Project project) {
	  this.currentProject = project;
	  setupProject();
	}
	
	private void setupProject() {
	  if (!setupCompleted || buildService == null || currentProject == null)
	    return;
	  
	  Build[] builds = buildService.retrieveBuildsForProject(currentProject)
	      .toArray(new Build[0]);
    mListView.setAdapter(new BuildHistoryListAdapter(getActivity(), builds));
	}
	
}
