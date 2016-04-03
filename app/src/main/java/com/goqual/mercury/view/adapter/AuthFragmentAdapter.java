package com.goqual.mercury.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ladmusician on 16. 2. 12..
 */
public class AuthFragmentAdapter extends FragmentPagerAdapter {
    private int mNumOfTabs = 0;
    List<Fragment> mFragmentList = null;

    public AuthFragmentAdapter(FragmentManager fm, int mNumOfTabs, List<Fragment> mFragmentList) {
        super(fm);
        this.mNumOfTabs = mNumOfTabs;
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
