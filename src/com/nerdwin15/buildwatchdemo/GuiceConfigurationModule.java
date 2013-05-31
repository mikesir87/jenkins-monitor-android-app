package com.nerdwin15.buildwatchdemo;

import com.google.inject.AbstractModule;
import com.nerdwin15.buildwatchdemo.annotations.GCMSenderId;
import com.nerdwin15.buildwatchdemo.service.BuildService;
import com.nerdwin15.buildwatchdemo.service.JenkinsService;
import com.nerdwin15.buildwatchdemo.service.MockedBuildService;
import com.nerdwin15.buildwatchdemo.service.MockedMultipleInstanceJenkinsService;
import com.nerdwin15.buildwatchdemo.service.MockedProjectService;
import com.nerdwin15.buildwatchdemo.service.ProjectService;

public class GuiceConfigurationModule extends AbstractModule {

	private static final String GCM_SENDER_ID = "367953236203";
	
	@Override
	protected void configure() {
		bindServices();
		bindProperties();
	}
	
	private void bindServices() {
		bind(BuildService.class).to(MockedBuildService.class);
//		bind(JenkinsService.class).to(MockedSingleInstanceJenkinsService.class);
		bind(JenkinsService.class).to(MockedMultipleInstanceJenkinsService.class);
		bind(ProjectService.class).to(MockedProjectService.class);
	}
	
	private void bindProperties() {
		bindConstant().annotatedWith(GCMSenderId.class).to(GCM_SENDER_ID);
	}
	
}
