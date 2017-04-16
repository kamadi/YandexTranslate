package kz.kamadi.yandextranslate.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.ui.history.HistoryFragment;
import kz.kamadi.yandextranslate.ui.listener.OnPageVisibleListener;
import kz.kamadi.yandextranslate.ui.settings.SettingsFragment;
import kz.kamadi.yandextranslate.ui.translate.TranslateFragment;
import kz.kamadi.yandextranslate.ui.widgets.CustomViewPager;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindColor(R.color.tab_selected)
    int colorTabSelected;
    @BindColor(R.color.background_button)
    int colorButtonBackground;

    @BindView(R.id.view_pager)
    CustomViewPager customViewPager;

    private MainTabPagerAdapter mainTabPagerAdapter;

    public static void start(Context context) {
        context.startActivity(new Intent(context,MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainTabPagerAdapter = new MainTabPagerAdapter(getSupportFragmentManager(), new TranslateFragment(), new HistoryFragment(), new SettingsFragment());
        customViewPager.setAdapter(mainTabPagerAdapter);
        customViewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(customViewPager);
        initTabs();
    }

    private void initTabs() {
        tabLayout.addOnTabSelectedListener(this);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_tab_translate);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_tab_fav);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_tab_settings);
        setTabColor(tabLayout.getTabAt(0),colorTabSelected);
        setTabColor(tabLayout.getTabAt(0),colorButtonBackground);
        setTabColor(tabLayout.getTabAt(0),colorButtonBackground);
    }

    private void setTabColor(TabLayout.Tab tab,int color){
        if (tab.getIcon()!=null){
            tab.getIcon().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        setTabColor(tab,colorTabSelected);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTabColor(tab,colorButtonBackground);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Fragment fragment = (Fragment) mainTabPagerAdapter.instantiateItem(customViewPager, position);
        if (fragment instanceof OnPageVisibleListener){
            ((OnPageVisibleListener)fragment).onPageVisible();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
