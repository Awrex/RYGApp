package com.ryg;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 23/08/2017.
 */

public class StatePagerAdapter extends FragmentStatePagerAdapter {
    int id = 0;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    public StatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addItem(Fragment fragment, String title){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
    public String getTag(int pos) {return mFragmentTitleList.get(pos);}
    @Override
    public Fragment getItem(int position) { return mFragmentList.get(position); }
    @Override
    public int getItemPosition(Object obj) { return PagerAdapter.POSITION_NONE;  }
    public long getItemId(int pos){return id + pos;}
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    public void notifyPosChange(int n) {
        id += getCount() + n;
    }
}
