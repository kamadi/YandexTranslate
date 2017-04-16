package kz.kamadi.yandextranslate.ui.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.BindView;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;
import kz.kamadi.yandextranslate.ui.listener.OnPageVisibleListener;

public class HistoryFragment extends BaseFragment implements ViewPager.OnPageChangeListener, OnPageVisibleListener {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private HistoryTabPagerAdapter pagerAdapter;

    @Override
    protected int layoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagerAdapter = new HistoryTabPagerAdapter(getActivity(), getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((OnPageVisibleListener) pagerAdapter.instantiateItem(viewPager, position)).onPageVisible();
        int otherPosition = 0;
        if (position == 0) {
            otherPosition = 1;
        }
        ((OnPageVisibleListener) pagerAdapter.instantiateItem(viewPager, otherPosition)).onPageHidden();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPageVisible() {
        ((OnPageVisibleListener) pagerAdapter.instantiateItem(viewPager, viewPager.getCurrentItem())).onPageVisible();
    }

    @Override
    public void onPageHidden() {

    }
}
