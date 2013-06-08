package com.nerdwin15.buildwatchdemo.fragment;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;
import com.nerdwin15.buildwatchdemo.R;

/**
 * A fragment that is used to start the setup process. This fragment presents
 * the user with the two methods that can be used to enroll a device:
 * - Use a barcode
 * - Enter everything manually
 * 
 * This fragment only supports parent activities that implement the
 * {@link SetupActionCallback} interface.
 *
 * @author Michael Irwin (mikesir87)
 */
public class StartSetupFragment extends RoboSherlockFragment {

  @InjectView(R.id.setup_button_barcode)
  private Button mBarcodeButton;
  
  @InjectView(R.id.setup_button_manual)
  private Button mManualButton;
  
  private SetupActionCallback callback;

  /**
   * {@inheritDoc}
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!SetupActionCallback.class.isAssignableFrom(getActivity().getClass())) {
      throw new IllegalStateException("This fragment only supports parent "
          + "activites that implement SetupActionCallback");
    }
    this.callback = (SetupActionCallback) getActivity();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_start_setup, container, false);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mManualButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        callback.enterSetupManually();
      }
    });
    
    mBarcodeButton.setOnClickListener(new OnClickListener() {
      public void onClick(View v) {
        callback.scanBarcode();
      }
    });
  }

  /**
   * Callback to be used to notify the parent activity on what method of
   * configuration the user has requested.
   *
   * @author Michael Irwin (mikesir87)
   */
  public interface SetupActionCallback {
    
    /**
     * Callback used when the user has requested to do configuration using a
     * barcode scan
     */
    void scanBarcode();
    
    /**
     * Callback used when the user has requested to do configuration by entering
     * everything in manually
     */
    void enterSetupManually();
    
  }
  
}
