package com.nerdwin15.buildwatchdemo.widget;

/**
 * Listener for events on the DrawerMenu
 * 
 * @author Michael Irwin
 */
public interface DrawerMenuListener {

	void onDrawerOpened();
	
	void onDrawerClosed();
	
	void onDrawerItemClicked(int itemId);
}
