package kz.kamadi.yandextranslate.ui.history;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.ui.listener.OnPageVisibleListener;
import kz.kamadi.yandextranslate.presenter.HistoryItemPresenter;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;

public class HistoryItemFragment extends BaseFragment implements View.OnFocusChangeListener, HistoryItemView,OnPageVisibleListener,HistoryAdapter.OnHistoryUpdateListener {
    private static final String FAVOURITE = "kz.kamadi.yandextranslate.ui.history.HistoryItemFragment.FAVOURITE";
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_edit_text)
    EditText searchEditText;
    @BindView(R.id.search_layout)
    RelativeLayout searchLayout;
    @BindView(R.id.clear_image_button)
    ImageButton clearImageButton;
    @BindDrawable(R.drawable.border_bottom)
    Drawable borderBottom;
    @BindDrawable(R.drawable.border_bottom_yellow)
    Drawable borderBottomActive;
    @Inject
    HistoryItemPresenter presenter;

    private List<History> histories;
    private HistoryAdapter adapter;
    private int offset = 0;
    private int limit = 25;
    private boolean isFavourite;

    public static HistoryItemFragment newInstance(boolean isFavourite) {

        Bundle args = new Bundle();
        args.putBoolean(FAVOURITE,isFavourite);
        HistoryItemFragment fragment = new HistoryItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        isFavourite = getArguments().getBoolean(FAVOURITE);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        Log.e("HistoryItemFragment","onResume");
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_history_item;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchEditText.setOnFocusChangeListener(this);
        adapter = new HistoryAdapter(context, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            searchLayout.setBackground(borderBottomActive);
            clearImageButton.setVisibility(View.VISIBLE);
        } else {
            searchLayout.setBackground(borderBottom);
        }
    }

    @OnClick(R.id.clear_image_button)
    public void onClearButtonClick() {

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
        error.printStackTrace();
    }

    @Override
    public void showHistories(List<History> histories) {
        if (this.histories == null) {
            this.histories = histories;
            adapter.setHistories(histories);
        }else {
            adapter.add(histories);
        }
    }

    @Override
    public void onPageVisible() {
        presenter.attachView(this);
        histories = null;
        offset = 0;
        adapter.setHistories(new ArrayList<>());
        presenter.getHistories(offset, limit, isFavourite);
    }

    @Override
    public void onHistoryUpdate(History history) {
        presenter.update(history);
    }
}
