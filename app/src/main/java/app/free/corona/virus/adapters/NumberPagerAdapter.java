package app.free.corona.virus.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import app.free.corona.virus.fragments.Number.NumberOverviewFragment;

/**
 * Created by nguyenvanhien on 4/16/18.
 */

public class NumberPagerAdapter extends FragmentPagerAdapter {
    private String[] mFragmentTitleList= {
            "Overview",
            "Countries",
            "States"
    };

    public NumberPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return NumberOverviewFragment.newInstance();
            case 1:
                return NumberOverviewFragment.newInstance();
            case 2:
                return NumberOverviewFragment.newInstance();
        }
        return NumberOverviewFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList[position];
    }
}
