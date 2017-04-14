package kz.kamadi.yandextranslate.ui.history;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import butterknife.BindArray;
import butterknife.ButterKnife;
import kz.kamadi.yandextranslate.R;

public class HistoryTabPagerAdapter extends FragmentPagerAdapter {
    @BindArray(R.array.history_tabs)
    String[] tabs;

    public HistoryTabPagerAdapter(Activity context, FragmentManager fm) {
        super(fm);
        ButterKnife.bind(this, context);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return HistoryItemFragment.newInstance(false);
        return HistoryItemFragment.newInstance(true);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
