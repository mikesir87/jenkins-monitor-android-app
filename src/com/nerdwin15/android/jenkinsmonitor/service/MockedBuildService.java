package com.nerdwin15.android.jenkinsmonitor.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.nerdwin15.android.jenkinsmonitor.domain.Build;
import com.nerdwin15.android.jenkinsmonitor.domain.Build.Status;
import com.nerdwin15.android.jenkinsmonitor.domain.CommitInfo;
import com.nerdwin15.android.jenkinsmonitor.domain.Project;

/**
 * A mocked implementation of a BuildService that performs all operations only
 * in memory.  It starts with a in-memory built list of default builds to use.
 *
 * @author Michael Irwin (mikesir87)
 */
public class MockedBuildService implements BuildService {

  private Long nextId = 1L;
  
  /**
   * {@inheritDoc}
   */
	@Override
	public List<Build> retrieveBuildsForProject(Project project) {
		List<Build> builds = new ArrayList<Build>();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 14);
		for (int i = 5; i >= 1; i--) {
			Build build = new Build();
			build.setDate(nextDate(calendar));
			build.setId(Long.valueOf(i));
			build.setMessage("Build message for build " + i);
			build.setStatus(getRandomStatus());
			build.setCommits(getCommits(i));
			builds.add(build);
		}
		return builds;
	}
	
  /**
   * {@inheritDoc}
   */
	@Override
	public void createBuild(Build build, Project project) {
	  build.setId(nextId++);
	}
	
	private Status getRandomStatus() {
		Random random = new Random();
		int val = random.nextInt(3);
		if (val == 0)
			return Status.FAILURE;
		else if (val == 1)
			return Status.WARNING;
		return Status.SUCCESS;
	}
	
	private Date nextDate(Calendar calendar) {
		calendar.add(Calendar.HOUR, -14);
		calendar.add(Calendar.MINUTE, -16);
		return calendar.getTime();
	}
	
	private CommitInfo[] getCommits(int index) {
	  List<CommitInfo> commits = new ArrayList<CommitInfo>();
	  switch (index) {
	    case 0:
	      commits.add(createCommitInfo("Michael Irwin", "Changed html tags"));
	      commits.add(createCommitInfo("Michael Irwin", "Added js framework"));
	      break;
	    case 1:
	      commits.add(createCommitInfo("Brian Early", 
	          "Added command support for creating of fixtures"));
	      commits.add(createCommitInfo("Brian Early", 
	          "Created repository support for everything else that wasn't " 
	              + "there before"));
	      break;
	    case 2:
	      commits.add(createCommitInfo("Carl Harris", 
	          "Jetty server dependency inclusion"));
	      commits.add(createCommitInfo("Carl Harris", 
	          "Setting up jetty server to run from main"));
	      commits.add(createCommitInfo("Carl Harris", 
	          "Merged in another branch"));
	      break;
	    case 3:
        commits.add(createCommitInfo("Michael Irwin", 
            "Some other form of a commit"));
        commits.add(createCommitInfo("Brian Early", "Here is another commit"));
        break;
	    case 4:
	      break;
	    case 5:
        commits.add(createCommitInfo("Michael Irwin", 
            "Some other form of a commit, but it's a little long. "
                + "I wonder how long?"));
        commits.add(createCommitInfo("Brian Early", 
            "Here is another commit. But, it's a little long..."));
	      
	  }
	  return commits.toArray(new CommitInfo[0]);
	}
	
	private CommitInfo createCommitInfo(String name, String message) {
	  CommitInfo commit = new CommitInfo();
	  commit.setCommitter(name);
	  commit.setMessage(message);
	  commit.setId(nextId++);
	  return commit;
	}
	
}
