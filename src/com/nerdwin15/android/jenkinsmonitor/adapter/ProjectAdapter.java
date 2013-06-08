package com.nerdwin15.android.jenkinsmonitor.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.nerdwin15.android.jenkinsmonitor.R;
import com.nerdwin15.android.jenkinsmonitor.domain.Project;

/**
 * An adapter that is used to populate a Spinner that displays the various
 * projects for a JenkinsInstance.
 * 
 * @author Michael Irwin (mikesir87)
 */
public class ProjectAdapter extends ArrayAdapter<Project> implements
    SpinnerAdapter {

  private LayoutInflater inflater;
  private String instanceName;
  private List<Project> projects;

  public ProjectAdapter(Context context, String instanceName,
      List<Project> projects) {
    super(context, R.layout.actionbar_spinner_title_item, projects);
    setDropDownViewResource(R.layout.spinner_project_dropdown_item);

    inflater = LayoutInflater.from(context);
    this.instanceName = instanceName;
    this.projects = projects;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    if (view == null) {
      view = inflater.inflate(R.layout.actionbar_spinner_title_item, parent,
          false);
    }
    Project project = getItem(position);
    setText(view, R.id.spinner_title, instanceName);
    setText(view, R.id.spinner_subtitle, project.getName());
    return view;
  }

  @Override
  public View getDropDownView(int position, View view, ViewGroup parent) {
    if (view == null) {
      view = inflater.inflate(R.layout.spinner_project_dropdown_item, parent,
          false);
    }

    LinearLayout itemView = (LinearLayout) view;
    Project project = projects.get(position);
    setText(view, R.id.projectName, project.getName());

    ImageView status = ((ImageView) itemView.findViewById(R.id.project_status));
    switch (project.getStatus()) {
      case FAILURE:
        status.setImageResource(R.drawable.spinner_project_failed);
        break;
      case WARNING:
        status.setImageResource(R.drawable.spinner_project_warning);
        break;
      case SUCCESS:
        status.setImageResource(R.drawable.spinner_project_success);
        break;
      case IN_PROGRESS:
        break;
    }
    return view;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long getItemId(int position) {
    if (projects.size() > position && projects.get(position) != null) {
      return projects.get(position).getId();
    } 
    else {
      return -1;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Project getItem(int position) {
    if (projects.size() > position) {
      return projects.get(position);
    } else {
      return null;
    }
  }

  private void setText(View view, int resId, String value) {
    ((TextView) view.findViewById(resId)).setText(value);
  }

}
