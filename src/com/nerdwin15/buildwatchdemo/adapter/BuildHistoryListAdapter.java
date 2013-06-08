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

/**
 * A ListAdapter for the BuildHistory that creates list items for each build
 * based on its commits.
 * 
 * @author Michael Irwin (mikesir87)
 */
public class BuildHistoryListAdapter extends ArrayAdapter<Build> {

	private LayoutInflater inflater;
	private Build[] builds;
	
	/**
	 * Construct the ListAdapter with the provided context and set of Builds.
	 * @param context The Android context
	 * @param builds The builds to be used in the adapter
	 */
	public BuildHistoryListAdapter(Context context, Build[] builds) {
		super(context, R.layout.view_build_history_list_item, builds);
		this.builds = builds;
		this.inflater = LayoutInflater.from(context);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Creates the list item view for a particular build.
	 */
	@Override
	public View getView(int position, View view, ViewGroup parent) {
	  if (view == null) {
	    view = inflater.inflate(R.layout.view_build_history_list_item, parent, 
	        false);
	  }
	  
		Build build = builds[position];
		
		setText(view, R.id.buildNumber, "#" + build.getId());
		setText(view, R.id.timeAgo, DateUtils
		    .getRelativeTimeSpanString(build.getDate().getTime()).toString());
		
    LinearLayout commits = 
        (LinearLayout) view.findViewById(R.id.commitContainer);
		populateCommitHistory(commits, build.getCommits());
	
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

	/**
	 * Creates the commit history view
	 * @param inflater
	 * @param commitHistory
	 * @param commits
	 */
  private void populateCommitHistory(LinearLayout commitHistory, 
      CommitInfo[] commits) {

    if (commits.length == 0) {
      inflater.inflate(R.layout.view_build_history_commit_item, commitHistory);
    }
    else {
      int maxToDisplay = (commits.length <= 2) ? commits.length : 2;
      for (int i = 0; i < maxToDisplay; i++) {
        CommitInfo commit = commits[i];
        View view = inflater.inflate(R.layout.view_build_history_commit_item, 
            commitHistory, false);
        setText(view, R.id.committer, commit.getCommitter());
        setText(view, R.id.commit, commit.getMessage());
        commitHistory.addView(view);
      }
      if (commits.length > 2) {
        View view = inflater.inflate(R.layout.view_build_history_commit_item, 
            commitHistory, false);
        ((TextView) view.findViewById(R.id.committer)).setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.commit)).setText("...");
        commitHistory.addView(view);
      }
    }
  }
  
  private void setText(View view, int resId, String value) {
    ((TextView) view.findViewById(resId)).setText(value);
  }
}
