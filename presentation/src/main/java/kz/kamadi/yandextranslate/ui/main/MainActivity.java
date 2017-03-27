package kz.kamadi.yandextranslate.ui.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.ui.history.HistoryFragment;
import kz.kamadi.yandextranslate.ui.settings.SettingsFragment;
import kz.kamadi.yandextranslate.ui.translate.TranslateFragment;
import kz.kamadi.yandextranslate.ui.widgets.CustomViewPager;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    CustomViewPager customViewPager;

    private MainTabPagerAdapter mainTabPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainTabPagerAdapter = new MainTabPagerAdapter(getSupportFragmentManager(), new TranslateFragment(), new HistoryFragment(), new SettingsFragment());
        customViewPager.setAdapter(mainTabPagerAdapter);
        tabLayout.setupWithViewPager(customViewPager);
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.getTabAt(0).setIcon(R.drawable.icon_tray);
        tabLayout.getTabAt(1).setIcon(R.drawable.icon_tray);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_tray);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
