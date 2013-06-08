package com.nerdwin15.android.jenkinsmonitor.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * An extension of the ViewPager that overrides and prevents the user from being
 * able to swipe views from side-to-side.
 * 
 * @author Michael Irwin (mikesir87)
 */
public class NonSwipeableViewPager extends ViewPager {

  /**
   * Constructs a default implementation
   * @param context The Android context
   */
  public NonSwipeableViewPager(Context context) {
    super(context);
  }

  /**
   * Constructs a implementaiton with the provided AttributeSet
   * @param context The Android context
   * @param attrs The attributes to apply
   */
  public NonSwipeableViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onInterceptTouchEvent(MotionEvent arg0) {
    // Never allow swiping to switch between pages
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // Never allow swiping to switch between pages
    return false;
  }

}
