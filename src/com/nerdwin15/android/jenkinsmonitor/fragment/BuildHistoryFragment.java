package com.nerdwin15.android.jenkinsmonitor.fragment;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.google.inject.Inject;
import com.nerdwin15.android.jenkinsmonitor.R;
import com.nerdwin15.android.jenkinsmonitor.adapter.BuildHistoryListAdapter;
import com.nerdwin15.android.jenkinsmonitor.domain.Build;
import com.nerdwin15.android.jenkinsmonitor.domain.Project;
import com.nerdwin15.android.jenkinsmonitor.service.BuildService;

/**
 * A Fragment that displays the build history for a single Project.
 * 
 * While the build history is being retrieved and prepared, a loading screen
 * is displayed to the user.
 *
 * @author Michael Irwin (mikesir87)
 */
public class BuildHistoryFragment extends RoboSherlockFragment {

  @Inject
  private BuildService buildService;
  
  @InjectView(R.id.build_history_loading)
  private LinearLayout mLoadingScreen;
  
  @InjectView(R.id.build_history_list) 
	private ListView mListView;

  private boolean setupCompleted;
	private Project currentProject;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_build_history, container, false);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	  super.onViewCreated(view, savedInstanceState);
	  setupCompleted = true;
	  setupProject();
	}
	
	/**
	 * Sets the project that the build history should be based on. Displays a
	 * loading screen until retrieval is complete.
	 * @param project
	 */
	public void setActiveProject(Project project) {
	  this.currentProject = project;
	  if (setupCompleted) {
	    toggleLoadingDisplay(true);
	    setupProject();
	  }
	}
	
	/**
	 * Toggle whether the loading or the build history should be displayed
	 * @param showLoading True if the loading screen should be displayed
	 */
	public void toggleLoadingDisplay(boolean showLoading) {
	  if (showLoading) {
	    mLoadingScreen.setVisibility(View.VISIBLE);
	    mListView.setVisibility(View.GONE);
	  } else {
      mLoadingScreen.setVisibility(View.GONE);
      mListView.setVisibility(View.VISIBLE);
	  }
	}
	
	private void setupProject() {
	  if (!setupCompleted || buildService == null || currentProject == null)
	    return;
	  Build[] builds = buildService.retrieveBuildsForProject(currentProject)
	      .toArray(new Build[0]);
    mListView.setAdapter(new BuildHistoryListAdapter(getActivity(), builds));
    toggleLoadingDisplay(false);
	}
	
}
