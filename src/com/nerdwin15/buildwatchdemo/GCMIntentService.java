package com.nerdwin15.buildwatchdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onMessage(Context arg0, Intent intent) {
		Log.i("message", "Received a message");
		Bundle bundle = intent.getExtras();
		for (String key : bundle.keySet())
			Log.i("message", "KEY: '" + key + "' : " + bundle.getString(key));
	}

	@Override
	protected void onRegistered(Context arg0, String regId) {
		Log.i("registration", "Registered with " + regId);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

}
