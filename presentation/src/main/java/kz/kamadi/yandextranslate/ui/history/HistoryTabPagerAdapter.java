package kz.kamadi.yandextranslate.ui.history;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import butterknife.BindArray;
import butterknife.ButterKnife;
import kz.kamadi.yandextranslate.R;

public class HistoryTabPagerAdapter extends FragmentPagerAdapter {
    @BindArray(R.array.history_tabs)
    String[] tabs;

    private List<HistoryItemFragment>fragments;

    public HistoryTabPagerAdapter(Activity context, FragmentManager fm, List<HistoryItemFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        ButterKnife.bind(this, context);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
