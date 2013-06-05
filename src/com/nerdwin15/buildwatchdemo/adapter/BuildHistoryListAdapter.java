package com.nerdwin15.buildwatchdemo.adapter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nerdwin15.buildwatchdemo.R;
import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.CommitInfo;

public class BuildHistoryListAdapter extends ArrayAdapter<Build> {

	private Context context;
	private Build[] builds;
	
	public BuildHistoryListAdapter(Context context, Build[] builds) {
		super(context, R.layout.view_build_history_list_item, builds);
		this.context = context;
		this.builds = builds;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	  View view;
    LayoutInflater inflater = (LayoutInflater) context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	  if (convertView == null) {
	    view = inflater.inflate(R.layout.view_build_history_list_item, parent, false);
	  }
	  else {
	    view = convertView;
	  }
	  
		Build build = builds[position];
		TextView buildNumber = (TextView) view.findViewById(R.id.buildNumber);
    LinearLayout commitHistory = (LinearLayout) view.findViewById(R.id.commitContainer);
		TextView timeAgo = (TextView) view.findViewById(R.id.timeAgo);
    
		buildNumber.setText("#" + build.getId());
		populateCommitHistory(inflater, commitHistory, build.getCommits());
		timeAgo.setText(DateUtils.getRelativeTimeSpanString(build.getDate().getTime()));
		
		switch (build.getStatus()) {
  		case SUCCESS:
        view.setBackgroundResource(R.drawable.build_success);
        break;
  		case FAILURE:
        view.setBackgroundResource(R.drawable.build_fail);
        break;
  		case WARNING:
        view.setBackgroundResource(R.drawable.build_warn);
        break;
  		case IN_PROGRESS:
  		  break;
		}

		return view;
	}

  private void populateCommitHistory(LayoutInflater inflater,
      LinearLayout commitHistory, CommitInfo[] commits) {

    if (commits.length == 0) {
      inflater.inflate(R.layout.view_build_history_commit_item, commitHistory);
    }
    else {
      int maxToDisplay = (commits.length <= 2) ? commits.length : 2;
      for (int i = 0; i < maxToDisplay; i++) {
        CommitInfo commit = commits[i];
        View view = inflater.inflate(R.layout.view_build_history_commit_item, commitHistory, false);
        ((TextView) view.findViewById(R.id.committer)).setText(commit.getCommitter());
        ((TextView) view.findViewById(R.id.commit)).setText(commit.getMessage());
        commitHistory.addView(view);
      }
      if (commits.length > 2) {
        View view = inflater.inflate(R.layout.view_build_history_commit_item, commitHistory, false);
        ((TextView) view.findViewById(R.id.committer)).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.commit)).setText("...");
        commitHistory.addView(view);
      }
    }
  }
  
}
