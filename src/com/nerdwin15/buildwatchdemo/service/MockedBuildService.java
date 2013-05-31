package com.nerdwin15.buildwatchdemo.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.util.Log;

import com.nerdwin15.buildwatchdemo.domain.Build;
import com.nerdwin15.buildwatchdemo.domain.Project;
import com.nerdwin15.buildwatchdemo.domain.Build.Status;

public class MockedBuildService implements BuildService {

	@Override
	public List<Build> retrieveBuildsForProject(Project project) {
		List<Build> builds = new ArrayList<Build>();
		Calendar calendar = Calendar.getInstance();
		for (int i = 15; i >= 0; i--) {
			Build build = new Build();
			build.setDate(nextDate(calendar));
			build.setId(Long.valueOf(i));
			build.setMessage("Build message for build " + i);
			build.setStatus(getRandomStatus());
			builds.add(build);
		}
		Log.d("project", "Returning " + builds.size() + " builds");
		return builds;
	}
	
	private Status getRandomStatus() {
		Random random = new Random();
		int val = random.nextInt(3);
		if (val == 0)
			return Status.FAILURE;
		else if (val == 1)
			return Status.IN_PROGRESS;
		return Status.SUCCESS;
	}
	
	private Date nextDate(Calendar calendar) {
		calendar.add(Calendar.DATE, -1);
		calendar.add(Calendar.HOUR, -2);
		calendar.add(Calendar.MINUTE, -16);
		return calendar.getTime();
	}
	
}
