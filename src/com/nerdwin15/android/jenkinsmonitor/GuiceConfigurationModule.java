package com.nerdwin15.android.jenkinsmonitor;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.nerdwin15.android.jenkinsmonitor.db.BuildDomainDbManager;
import com.nerdwin15.android.jenkinsmonitor.db.CommitInfoDomainDbManager;
import com.nerdwin15.android.jenkinsmonitor.db.DomainDbManager;
import com.nerdwin15.android.jenkinsmonitor.db.JenkinsInstanceDomainDbManager;
import com.nerdwin15.android.jenkinsmonitor.db.ProjectDomainDbManager;
import com.nerdwin15.android.jenkinsmonitor.service.BuildService;
import com.nerdwin15.android.jenkinsmonitor.service.CommitInfoService;
import com.nerdwin15.android.jenkinsmonitor.service.DbBuildService;
import com.nerdwin15.android.jenkinsmonitor.service.DbCommitInfoService;
import com.nerdwin15.android.jenkinsmonitor.service.DbJenkinsService;
import com.nerdwin15.android.jenkinsmonitor.service.DbProjectService;
import com.nerdwin15.android.jenkinsmonitor.service.JenkinsInstanceService;
import com.nerdwin15.android.jenkinsmonitor.service.ProjectService;

public class GuiceConfigurationModule extends AbstractModule {

  @Override
  protected void configure() {
    bindServices();
    bindDatabaseHandlers();
  }

  private void bindServices() {
//    bind(BuildService.class).to(MockedBuildService.class);
    bind(BuildService.class).to(DbBuildService.class);
    // bind(JenkinsService.class).to(MockedSingleInstanceJenkinsService.class);
    // bind(JenkinsService.class).to(MockedMultipleInstanceJenkinsService.class);
    bind(JenkinsInstanceService.class).to(DbJenkinsService.class);
//    bind(ProjectService.class).to(MockedProjectService.class);
    bind(ProjectService.class).to(DbProjectService.class);
    bind(CommitInfoService.class).to(DbCommitInfoService.class);
  }

  private void bindDatabaseHandlers() {
    Multibinder<DomainDbManager> dbBinder = Multibinder.newSetBinder(binder(),
        DomainDbManager.class);
    dbBinder.addBinding().to(JenkinsInstanceDomainDbManager.class);
    dbBinder.addBinding().to(ProjectDomainDbManager.class);
    dbBinder.addBinding().to(BuildDomainDbManager.class);
    dbBinder.addBinding().to(CommitInfoDomainDbManager.class);
  }
}
