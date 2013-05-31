package com.nerdwin15.buildwatchdemo.fragment;

import java.util.List;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.google.inject.Inject;
import com.nerdwin15.buildwatchdemo.R;
import com.nerdwin15.buildwatchdemo.adapter.BuildHistoryListAdapter;
import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.domain.Project;
import com.nerdwin15.buildwatchdemo.service.BuildService;
import com.nerdwin15.buildwatchdemo.service.ProjectService;

public class BuildHistoryFragment extends RoboSherlockFragment implements OnItemSelectedListener {

	private @InjectView(R.id.build_history_list) ListView mListView;
	private @InjectView(R.id.build_history_project_selector) Spinner mProjectSelector;
	
	private ArrayAdapter<Project> spinnerAdapter;
	private ProjectService projectService;
	private BuildService buildService;
	private JenkinsInstance currentJenkinsInstance;
	
	private boolean setupCompleted;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_build_history, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		updateProjectAdapter(new Project[0]);
		mProjectSelector.setOnItemSelectedListener(this);
		
		updateDisplayForJenkinsInstance();
		setupCompleted = true;
	}

	public void setSelectedInstance(JenkinsInstance instance) {
		currentJenkinsInstance = instance;
		if (setupCompleted)
			updateDisplayForJenkinsInstance();
	}
	
	private void updateProjectAdapter(Project[] projects) {
		spinnerAdapter = new ArrayAdapter<Project>(getSherlockActivity(), 
				android.R.layout.simple_spinner_dropdown_item, projects);
		mProjectSelector.setAdapter(spinnerAdapter);
	}
	
	private void updateDisplayForJenkinsInstance() {
		Log.i("jenkins", "Select instance " + currentJenkinsInstance.getName());
		List<Project> projects = projectService.retrieveProjects(currentJenkinsInstance);
		updateProjectAdapter(projects.toArray(new Project[projects.size()]));
		
		Project project = projects.get(0);
		Build[] builds = buildService.retrieveBuildsForProject(project).toArray(new Build[0]);
		mListView.setAdapter(new BuildHistoryListAdapter(getActivity(), builds));
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parentView, View view, 
			int position, long id) {
		Log.i("onClick", "Clicked position " + position);
		Log.i("onClick", ((Project)spinnerAdapter.getItem(position)).getName());
		Project project = (Project)spinnerAdapter.getItem(position);
		Build[] builds = buildService.retrieveBuildsForProject(project).toArray(new Build[0]);
		mListView.setAdapter(new BuildHistoryListAdapter(getActivity(), builds));
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// Do nothing
	}
	
	@Inject
	public void setBuildService(BuildService buildService) {
		this.buildService = buildService;
	}
	
	@Inject
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
}
