package com.nerdwin15.buildwatchdemo.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nerdwin15.buildwatchdemo.R;
import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.Build.Status;

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
		Log.d("history_adapter", "Requesting item " + position);
		Build build = builds[position];
		LayoutInflater inflater = (LayoutInflater)context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_build_history_list_item, parent, false);
		((TextView)view.findViewById(R.id.buildNumber)).setText("Build #" + build.getId());
		if (build.getStatus().equals(Status.SUCCESS))
			view.setBackgroundResource(R.drawable.build_success);
		else if (build.getStatus().equals(Status.FAILURE))
			view.setBackgroundResource(R.drawable.build_fail);
		else
			view.setBackgroundResource(R.drawable.build_warn);
		((TextView)view.findViewById(R.id.build_date)).setText(dateFormat.format(build.getDate()));
		((TextView)view.findViewById(R.id.build_time)).setText(timeFormat.format(build.getDate()));
		return view;
	}
}
