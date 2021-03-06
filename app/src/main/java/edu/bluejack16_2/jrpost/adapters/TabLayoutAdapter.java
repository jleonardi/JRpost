package edu.bluejack16_2.jrpost.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class TabLayoutAdapter extends FragmentPagerAdapter {

    ArrayList<Fragment>listOfFragment;
    ArrayList<String>listOfTab;
    Context context;

    public TabLayoutAdapter(FragmentManager fm) {
        super(fm);
        listOfFragment=new ArrayList<>();
        listOfTab=new ArrayList<>();
    }

    public void add(Fragment fragment,String tab)
    {
        listOfFragment.add(fragment);
        listOfTab.add(tab);
    }

    @Override
    public Fragment getItem(int position) {
        return listOfFragment.get(position);
    }

    @Override
    public int getCount() {
        return listOfFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listOfTab.get(position);
    }
}
