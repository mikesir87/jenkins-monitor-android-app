package com.nerdwin15.buildwatchdemo.fragment;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.nerdwin15.buildwatchdemo.R;

/**
 * A fragment that is displayed during the configuration of a Jenkins instance.
 * 
 * The fragment has three main views at work:
 *  - A form that allows the user to specify a name for the instance while
 *    configuration is taking place
 *  - The configuration loading view (a spinner to say configuration is at work)
 *  - The configuration complete view that has a button to continue
 *
 * @author Michael Irwin (mikesir87)
 */
public class ConfiguringFragment extends RoboSherlockFragment {

  @InjectView(R.id.setup_configuration_name_entry)
  private EditText mNameEntry;

  @InjectView(R.id.setup_configuring_loading)
  private LinearLayout mConfiguringLayout;

  @InjectView(R.id.setup_configuration_done)
  private LinearLayout mConfigurationCompleteLayout;
  
  @InjectView(R.id.setup_configuration_submit)
  private Button mSubmitButton;
  
  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_configuring, container, false);
  }

  /**
   * Called by the activity when the configuration has been completed.
   * 
   * The provided JenkinsInstance is populated with the name specified by the
   * user and then provided back to the callback when the user indicates they
   * are ready. 
   *  
   * @param callback A callback that is used to notify when the user has 
   * requested to move on.
   */
  public void configurationComplete(final JenkinsNameCallback callback) {
    mConfiguringLayout.setVisibility(View.GONE);
    mConfigurationCompleteLayout.setVisibility(View.VISIBLE);
    mSubmitButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        callback.instanceNameSpecified(mNameEntry.getText().toString());
      }
    });
  }
  
  /**
   * A callback to be used to pass back the name of the Jenkins Instance the
   * user has specified.
   *
   * @author Michael Irwin (mikesir87)
   */
  public interface JenkinsNameCallback {
    
    /**
     * Callback method used when the user has determined the instance name for
     * the instance being configured.
     * @param instanceName The name of the instance specified by the user
     */
    void instanceNameSpecified(String instanceName);
  }
  
}
