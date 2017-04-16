package kz.kamadi.yandextranslate.ui.history;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import kz.kamadi.yandextranslate.presenter.HistoryItemPresenter;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;
import kz.kamadi.yandextranslate.ui.listener.OnPageVisibleListener;
import kz.kamadi.yandextranslate.ui.widgets.TranslateEditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HistoryItemFragment extends BaseFragment implements HistoryItemView, OnPageVisibleListener, HistoryAdapter.OnHistoryActionListener, TextWatcher, TranslateEditText.EditTextImeBackListener, View.OnTouchListener {
    private static final String FAVOURITE = "kz.kamadi.yandextranslate.ui.history.HistoryItemFragment.FAVOURITE";
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_edit_text)
    TranslateEditText searchEditText;
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
    private Handler handler = new Handler();
    private boolean isKeyboardOpen = false;

    public static HistoryItemFragment newInstance(boolean isFavourite) {
        Bundle args = new Bundle();
        args.putBoolean(FAVOURITE, isFavourite);
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
    }


    @Override
    protected int layoutId() {
        return R.layout.fragment_history_item;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFavourite){
            searchEditText.setHint(R.string.search_favourites);
        }else {
            searchEditText.setHint(R.string.search_history);
        }
        searchEditText.setOnTouchListener(this);
        searchEditText.addTextChangedListener(this);
        searchEditText.setOnEditTextImeBackListener(this);
        adapter = new HistoryAdapter(context, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @OnClick(R.id.clear_image_button)
    public void onClearButtonClick() {
        searchEditText.setText("");
        getHistories();
        if (!isKeyboardOpen) {
            openKeyboard();
        }
        changeSearchLayout(true);
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
        } else {
            adapter.add(histories);
        }
    }

    @Override
    public void onHistoryDeleted(int position) {
        adapter.deleteItem(position);
    }

    @Override
    public void onPageVisible() {
        presenter.attachView(this);
        getHistories();
    }

    @Override
    public void onPageHidden() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        searchEditText.setText("");
        clearImageButton.setVisibility(View.GONE);
        changeSearchLayout(false);
    }

    @Override
    public void onHistoryUpdate(History history) {
        presenter.update(history);
    }

    @Override
    public void onHistoryDelete(History history, int position) {
        Log.e("onHistoryDelete",position+"");
        new AlertDialog.Builder(context)
                .setTitle(R.string.deleting)
                .setMessage(R.string.deleting_message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    presenter.delete(history,position,isFavourite);
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {

                })
                .show();
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            histories = null;
            presenter.search(s.toString(), isFavourite);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            clearImageButton.setVisibility(View.VISIBLE);
        } else {
            clearImageButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onImeBack() {
        changeSearchLayout(false);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        changeSearchLayout(true);
        return false;
    }

    private void changeSearchLayout(boolean isActive) {
        if (isActive) {
            searchLayout.setBackground(borderBottomActive);
            searchEditText.setCursorVisible(true);
            isKeyboardOpen = true;
        } else {
            searchLayout.setBackground(borderBottom);
            searchEditText.setCursorVisible(false);
            isKeyboardOpen = false;
        }
    }

    private void openKeyboard() {
        handler.post(() -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(searchEditText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
            searchEditText.requestFocus();
            isKeyboardOpen = true;
        });
    }

    public void getHistories() {
        histories = null;
        offset = 0;
        adapter.setHistories(new ArrayList<>());
        presenter.getHistories(offset, limit, isFavourite);
    }
}
