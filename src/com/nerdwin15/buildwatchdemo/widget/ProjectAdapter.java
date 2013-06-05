package com.nerdwin15.buildwatchdemo.widget;

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

import com.nerdwin15.buildwatchdemo.R;
import com.nerdwin15.buildwatchdemo.domain.Project;

public class ProjectAdapter extends ArrayAdapter<Project> implements
    SpinnerAdapter {

  private LayoutInflater inflater;
  private String title;
  private List<Project> projects;

  public ProjectAdapter(Context context, int titleResID, List<Project> units) {
    this(context, context.getString(titleResID), units);
  }

  public ProjectAdapter(Context context, String title, List<Project> projects) {
    super(context, R.layout.actionbar_spinner_title_item, projects);
    setDropDownViewResource(R.layout.spinner_project_dropdown_item);

    inflater = LayoutInflater.from(context);
    this.title = title;
    this.projects = projects;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.actionbar_spinner_title_item,
          parent, false);
    }

    TextView titleView = (TextView) convertView
        .findViewById(R.id.spinner_title);
    TextView subtitleView = (TextView) convertView
        .findViewById(R.id.spinner_subtitle);

    Project project = getItem(position);

    titleView.setText(title);
    if (project != null) {
      subtitleView.setText(project.getName());
    }

    return convertView;
  }

  @Override
  public View getDropDownView(int position, View convertView, 
      ViewGroup parent) {
    if (convertView == null) {
      convertView = inflater.inflate(
          R.layout.spinner_project_dropdown_item, parent,
          false);
    }

    LinearLayout itemView = (LinearLayout) convertView;
    Project project = projects.get(position);
    ((TextView) itemView.findViewById(R.id.projectName))
        .setText(project.getName());
    ImageView status = ((ImageView) itemView.findViewById(R.id.project_status));
    switch(project.getStatus()) {
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
    return convertView;
  }

  @Override
  public long getItemId(int position) {
    if (projects.size() > position && projects.get(position) != null) {
      return projects.get(position).getId();
    } else {
      return -1;
    }
  }

  @Override
  public Project getItem(int position) {
    if (projects.size() > position) {
      return projects.get(position);
    } else {
      return null;
    }
  }

}
