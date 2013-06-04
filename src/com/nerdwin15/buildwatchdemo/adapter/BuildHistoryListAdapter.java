package com.nerdwin15.buildwatchdemo.adapter;

import java.text.DateFormat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nerdwin15.buildwatchdemo.R;
import com.nerdwin15.buildwatchdemo.domain.Build;

public class BuildHistoryListAdapter extends ArrayAdapter<Build> {

	private Context context;
	private Build[] builds;
	private DateFormat dateFormat, timeFormat;
	
	public BuildHistoryListAdapter(Context context, Build[] builds) {
		super(context, R.layout.view_build_history_list_item, builds);
		this.context = context;
		this.builds = builds;
		dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	  View view;
	  if (convertView == null) {
	    LayoutInflater inflater = (LayoutInflater)context
	        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    view = inflater.inflate(R.layout.view_build_history_list_item, parent, false);
	  }
	  else {
	    view = convertView;
	  }
	  
		Build build = builds[position];
		((TextView)view.findViewById(R.id.buildNumber)).setText("Build #" + build.getId());
    ((TextView)view.findViewById(R.id.build_date)).setText(dateFormat.format(build.getDate()));
    ((TextView)view.findViewById(R.id.build_time)).setText(timeFormat.format(build.getDate()));
		
		switch (build.getStatus()) {
  		case SUCCESS:
        view.setBackgroundResource(R.drawable.build_success);
        break;
  		case FAILURE:
        view.setBackgroundResource(R.drawable.build_fail);
        break;
  		case IN_PROGRESS:
        view.setBackgroundResource(R.drawable.build_warn);
        break;
		}

		return view;
	}
}
