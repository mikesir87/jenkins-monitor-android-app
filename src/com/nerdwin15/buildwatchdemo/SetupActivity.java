package com.nerdwin15.buildwatchdemo;

import org.json.JSONException;
import org.json.JSONObject;

import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockFragmentActivity;
import com.google.inject.Inject;
import com.google.zxing.integration.android.IntentIntegrator;
import com.nerdwin15.buildwatchdemo.adapter.SetupFragmentAdapter;
import com.nerdwin15.buildwatchdemo.domain.JenkinsInstance;
import com.nerdwin15.buildwatchdemo.fragment.ConfiguringFragment;
import com.nerdwin15.buildwatchdemo.fragment.ConfiguringFragment.JenkinsNameCallback;
import com.nerdwin15.buildwatchdemo.fragment.StartSetupFragment;
import com.nerdwin15.buildwatchdemo.fragment.StartSetupFragment.SetupActionCallback;
import com.nerdwin15.buildwatchdemo.service.JenkinsInstanceService;
import com.nerdwin15.buildwatchdemo.task.ConfigurationTask;
import com.nerdwin15.buildwatchdemo.task.ConfigurationTask.ConfigurationTaskCallback;

@ContentView(R.layout.activity_setup_instance)
public class SetupActivity extends RoboSherlockFragmentActivity implements
    SetupActionCallback, ConfigurationTaskCallback {

  private static final String SCAN_EXTRA = "SCAN_RESULT";

  @InjectView(R.id.pager)
  private ViewPager mViewPager;

  @Inject
  private ConfigurationTask configurationTask;

  @Inject
  private ConfiguringFragment configuringFragment;

  private SetupFragmentAdapter mFragmentAdapter;

  @Inject
  private JenkinsInstanceService jenkinsService;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mFragmentAdapter = new SetupFragmentAdapter(getSupportFragmentManager());
    mFragmentAdapter.addFragment(new StartSetupFragment());
    mFragmentAdapter.addFragment(configuringFragment);
    mViewPager.setAdapter(mFragmentAdapter);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode != Activity.RESULT_OK || data == null
        || !data.getAction().contains("SCAN") || !data.hasExtra(SCAN_EXTRA))
      return;

    doConfiguration(data.getStringExtra(SCAN_EXTRA));
  }

  private void doConfiguration(String barcodeData) {
    try {
      Log.i("barcode", "Got " + barcodeData);
      JSONObject jsonData = new JSONObject(barcodeData);
      JenkinsInstance instance = new JenkinsInstance();
      instance.setBaseUrl(jsonData.getString("url"));
      instance.setUsername(jsonData.getString("username"));
      instance.setApiToken(jsonData.getString("token"));
      instance.setSenderId(jsonData.getString("senderId"));

      mViewPager.setCurrentItem(mFragmentAdapter
          .getPosition(ConfiguringFragment.class));
      configurationTask.setCallback(this);
      configurationTask.execute(instance);
    } catch (JSONException e) {
      Log.e("setup", "Thrown JSONException from barcode", e);
      Toast.makeText(this, getString(R.string.setup_barcode_json_error),
          Toast.LENGTH_LONG).show();
    }
  }

  @Override
  public void enterSetupManually() {
    Log.i("setup", "Time to enter everything manually");
  }

  @Override
  public void scanBarcode() {
    IntentIntegrator intent = new IntentIntegrator(this);
    intent.setMessageByID(R.string.setup_barcode_need_app);
    intent.setTitle("HI");
    intent.initiateScan();
    Log.i("setup", "Time to scan a barcode");
  }

  @Override
  public void configurationComplete(final JenkinsInstance instance) {
    configuringFragment.configurationComplete(new JenkinsNameCallback() {
      public void instanceNameSpecified(String instanceName) {
        instance.setName(instanceName);
        jenkinsService.update(instance);
        Intent intent = new Intent(SetupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
      }
    });
  }

  @Override
  public void configurationFailed(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }

}
