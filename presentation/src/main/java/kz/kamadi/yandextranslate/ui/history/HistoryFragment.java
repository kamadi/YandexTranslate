package kz.kamadi.yandextranslate.ui.history;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.presenter.HistoryPresenter;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;
import kz.kamadi.yandextranslate.ui.listener.OnPageVisibleListener;

public class HistoryFragment extends BaseFragment implements HistoryView, ViewPager.OnPageChangeListener, OnPageVisibleListener, DeleteAllButtonVisibilityListener {

    public static final String TAG = HistoryFragment.class.getSimpleName();

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.delete_image_button)
    ImageButton deleteImageButton;
    @Inject
    HistoryPresenter presenter;
    private HistoryTabPagerAdapter pagerAdapter;
    private List<HistoryItemFragment> fragments;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_history;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragments = Arrays.asList(HistoryItemFragment.newInstance(false), HistoryItemFragment.newInstance(true));
        pagerAdapter = new HistoryTabPagerAdapter(getActivity(), getChildFragmentManager(), fragments);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e(TAG,"onPageSelected");
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
        Log.e(TAG,"onPageVisible");
        ((OnPageVisibleListener) pagerAdapter.instantiateItem(viewPager, viewPager.getCurrentItem())).onPageVisible();
    }

    @Override
    public void onPageHidden() {

    }

    @OnClick(R.id.delete_image_button)
    void onDeleteImageClick() {
        boolean isFavourite = viewPager.getCurrentItem() == 1;
        String title = getResources().getStringArray(R.array.history_tabs)[viewPager.getCurrentItem()];
        String message = getResources().getStringArray(R.array.history_delete_message)[viewPager.getCurrentItem()];
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    presenter.deleteAll(isFavourite);
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {

                })
                .show();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleError(Throwable error) {

    }

    @Override
    public void onHistoriesDeleted() {
        Log.e(TAG,"onHistoriesDeleted");
        ((OnPageVisibleListener) pagerAdapter.instantiateItem(viewPager, viewPager.getCurrentItem())).onPageVisible();
    }

    @Override
    public void setVisibility(boolean isVisible) {
        if (isVisible) {
            deleteImageButton.setVisibility(View.VISIBLE);
        } else {
            deleteImageButton.setVisibility(View.GONE);
        }
    }
}
