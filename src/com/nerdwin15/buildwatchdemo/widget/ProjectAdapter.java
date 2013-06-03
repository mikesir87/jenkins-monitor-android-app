package com.nerdwin15.buildwatchdemo.widget;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.actionbarsherlock.internal.view.menu.ListMenuItemView;
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
    setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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

      TextView titleView = (TextView) convertView.findViewById(R.id.spinner_title);
      TextView subtitleView = (TextView) convertView.findViewById(R.id.spinner_subtitle);

      Project project = getItem(position);

      titleView.setText(title);
      if (project != null) {
          subtitleView.setText(project.getName());
      }

      return convertView;
  }

  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
      if (convertView == null) {
          convertView = inflater.inflate(
              com.actionbarsherlock.R.layout.abs__popup_menu_item_layout, 
              parent, false);

          TextView titleView = (TextView) convertView.findViewById(R.id.abs__title);
          titleView.setTextAppearance(getContext(), 
              R.style.TextAppearance_Sherlock_Widget_PopupMenu_Large);
          titleView.setTextColor(Color.WHITE);
          titleView.setFocusable(false);
          titleView.setFocusableInTouchMode(false);
          titleView.setDuplicateParentStateEnabled(true);
      }

      ListMenuItemView itemView = (ListMenuItemView) convertView;

      itemView.setVisibility(View.VISIBLE);

      if (projects != null && projects.size() > position) {
          itemView.setTitle(projects.get(position).getName());
      } else {
          itemView.setTitle(null);
      }

      itemView.setShortcut(false, '0');
      itemView.setEnabled(true);

      return convertView;
  }

  @Override
  public long getItemId(int position) {
      if (projects.size() > position && projects.get(position) != null) {
        Log.i("itemId", "Requesting " + position);
        Log.i("ID", "Got id of " + projects.get(position).getId());
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

  public int getPosition(long projectId) {
      int position = 0;
      for (Project project : projects) {
          if (project.getId().equals(projectId)) {
              return position;
          }
          position++;
      }
      return position;
  }
}
