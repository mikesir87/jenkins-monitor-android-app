package com.nerdwin15.buildwatchdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Fragment adapter that is used to display and hold the fragments used for the
 * ViewPager in the SetupActivity.
 * 
 * @author Michael Irwin (mikesir87)
 */
public class SetupFragmentAdapter extends FragmentPagerAdapter {

  private List<Fragment> fragments;
  
  /**
   * Construct a new instance
   * @param fm The FragmentManager
   */
  public SetupFragmentAdapter(FragmentManager fm) {
    super(fm);
    fragments = new ArrayList<Fragment>();
  }
  
  /**
   * Add a fragment to the internal list of Fragments that are being used.
   * @param fragment The Fragment to add
   */
  public void addFragment(Fragment fragment) {
    fragments.add(fragment);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Fragment getItem(int position) {
    return fragments.get(position);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getCount() {
    return fragments.size();
  }

  /**
   * Get the position for a particular type of fragment.
   * @param fragmentClass The type of fragment to find the position for.
   * @return The index of the fragment type requested
   */
  public int getPosition(Class<? extends Fragment> fragmentClass) {
    for (int i = 0; i < fragments.size(); i++) {
      if (fragments.get(i).getClass().equals(fragmentClass)) {
        return i;
      }
    }
    return -1;
  }
  
}
