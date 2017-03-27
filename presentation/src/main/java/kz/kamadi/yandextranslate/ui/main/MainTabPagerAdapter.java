package kz.kamadi.yandextranslate.ui.main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import kz.kamadi.yandextranslate.ui.history.HistoryFragment;
import kz.kamadi.yandextranslate.ui.settings.SettingsFragment;
import kz.kamadi.yandextranslate.ui.translate.TranslateFragment;

public class MainTabPagerAdapter extends FragmentPagerAdapter {
    private TranslateFragment translateFragment;
    private HistoryFragment historyFragment;
    private SettingsFragment settingsFragment;

    public MainTabPagerAdapter(FragmentManager fm, TranslateFragment translateFragment, HistoryFragment historyFragment, SettingsFragment settingsFragment) {
        super(fm);
        this.translateFragment = translateFragment;
        this.historyFragment = historyFragment;
        this.settingsFragment = settingsFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return translateFragment;
            case 1:
                return historyFragment;
            case 2:
                return settingsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
