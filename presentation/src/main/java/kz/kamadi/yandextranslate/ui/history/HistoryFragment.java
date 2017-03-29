package kz.kamadi.yandextranslate.ui.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import butterknife.BindView;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;

public class HistoryFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected int layoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HistoryTabPagerAdapter pagerAdapter = new HistoryTabPagerAdapter(getActivity(),getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
