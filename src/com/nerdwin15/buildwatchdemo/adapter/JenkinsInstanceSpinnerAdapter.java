package com.nerdwin15.buildwatchdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;

public class JenkinsInstanceSpinnerAdapter extends BaseAdapter {

	private Context mContext;
	private List<JenkinsInstance> instances = new ArrayList<JenkinsInstance>();
	
	public JenkinsInstanceSpinnerAdapter(Context context) {
		mContext = context;
	}
	
	public int getCount() {
		return instances.size();
	}

	@Override
	public Object getItem(int position) {
		return instances.get(position);
	}

	@Override
	public long getItemId(int position) {
		return Long.valueOf(position);
	}
	
	public void setProjects(List<JenkinsInstance> instances) {
		this.instances = instances;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		CheckedTextView view = (CheckedTextView)inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
		view.setText(instances.get(position).getName());
		return view;
	}
}
