package com.nerdwin15.buildwatchdemo;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.service.JenkinsInstanceService;

public class GCMIntentService extends GCMBaseIntentService {

  private JenkinsInstanceService jenkinsService;

  private void injectServices(Context context) {
    Injector injector = Guice.createInjector(new GCMModule(
        context), new GuiceConfigurationModule());
    jenkinsService = injector.getBinding(JenkinsInstanceService.class).getProvider().get();
  }

  @Override
  protected void onError(Context arg0, String arg1) {
    // TODO Auto-generated method stub

  }

  @Override
  protected void onMessage(Context context, Intent intent) {
    injectServices(context);
    
    Log.i("message", "Received a message");
    String buildInfo = intent.getExtras().getString("m");
    handleBuild(buildInfo);
  }

  @Override
  protected void onRegistered(Context arg0, String regId) {
    Log.i("registration", "Registered with " + regId);
  }

  @Override
  protected void onUnregistered(Context arg0, String arg1) {
    // TODO Auto-generated method stub

  }

  private void handleBuild(String buildDetails) {
    String[] lines = buildDetails.split("\\n");
    String commitResult = lines[0];
    String url = getUrl(commitResult);
    String baseUrl = url.substring(0, url.indexOf("/job/")) + "/";

    Log.i("message", "URL: " + url);
    Log.i("message", "Base: " + baseUrl);
    JenkinsInstance instance = jenkinsService.retrieveInstanceByBaseUrl(baseUrl);
    Log.i("message", "New build for instance " + instance.getId());

    for (String line : lines) {
      System.out.println(line);
    }
  }

  private String getUrl(String line) {
    String[] parts = line.split("\\s");
    for (String item : parts)
      try {
        URL url = new URL(item);
        return item;
      } catch (MalformedURLException e) {
      }
    return "";
  }

  private class GCMModule extends AbstractModule {
    private Context context;

    public GCMModule(Context context) {
      this.context = context;
    }

    protected void configure() {
      bind(Context.class).toInstance(context);
    }
  }
}
