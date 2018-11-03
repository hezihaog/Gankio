package me.wally.gankio.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CommonFragmentStatePageAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public CommonFragmentStatePageAdapter(FragmentManager fm, ArrayList<? extends Fragment> fragments) {
        super(fm);
        this.mFragments.clear();
        this.mFragments.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void setFragments(List<Fragment> fragments) {
        mFragments.clear();
        addFragments(fragments);
    }

    public void addFragments(List<Fragment> fragments) {
        mFragments.addAll(fragments);
    }
}