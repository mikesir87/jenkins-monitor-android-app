package com.nerdwin15.buildwatchdemo.widget;

/**
 * Listener for events on the DrawerMenu
 * 
 * @author Michael Irwin (mikesir87)
 */
public interface DrawerMenuListener {

  /**
   * Callback used to notify when the drawer has been opened
   */
	void onDrawerOpened();
	
  /**
   * Callback used to notify when the drawer has been closed
   */
	void onDrawerClosed();
	
	/**
	 * Callback to be used when an item on the drawer's list has been clicked
	 * @param itemId The id of the item clicked
	 */
	void onDrawerItemClicked(int itemId);
}
