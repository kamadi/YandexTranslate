package kz.kamadi.yandextranslate.ui.history;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import kz.kamadi.yandextranslate.ui.listener.EndlessRecyclerViewScrollListener;
import kz.kamadi.yandextranslate.ui.listener.OnLoadMoreListener;
import kz.kamadi.yandextranslate.ui.listener.OnPageVisibleListener;
import kz.kamadi.yandextranslate.ui.widgets.TranslateEditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class HistoryItemFragment extends BaseFragment implements HistoryItemView, OnPageVisibleListener, OnLoadMoreListener, HistoryAdapter.OnHistoryActionListener, TextWatcher, TranslateEditText.EditTextImeBackListener, View.OnTouchListener {
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
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.error_icon)
    ImageView errorIcon;
    @BindView(R.id.error_message)
    TextView errorMessage;
    @BindDrawable(R.drawable.border_bottom)
    Drawable borderBottom;
    @BindDrawable(R.drawable.border_bottom_yellow)
    Drawable borderBottomActive;
    @Inject
    HistoryItemPresenter presenter;

    private List<History> histories = new ArrayList<>();
    private HistoryAdapter adapter;
    private int offset = 0;
    private final int LIMIT = 7;
    private boolean isFavourite;
    private Handler handler = new Handler();
    private boolean isKeyboardOpen = false;
    private boolean isSearching = false;
    private DeleteAllButtonVisibilityListener deleteAllButtonVisibilityListener;
    private EndlessRecyclerViewScrollListener scrollListener;


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
    protected int layoutId() {
        return R.layout.fragment_history_item;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getParentFragment() instanceof DeleteAllButtonVisibilityListener) {
            deleteAllButtonVisibilityListener = (DeleteAllButtonVisibilityListener) getParentFragment();
        }
        initSearchEditText();
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new HistoryAdapter(context, this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(context, layoutManager.getOrientation()));
        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager, this);
        recyclerView.addOnScrollListener(scrollListener);
    }

    private void initSearchEditText() {
        searchEditText.setHint(isFavourite ? R.string.search_favourites : R.string.search_history);
        errorIcon.setImageResource(isFavourite ? R.drawable.no_favourites : R.drawable.no_history);
        searchEditText.setOnTouchListener(this);
        searchEditText.addTextChangedListener(this);
        searchEditText.setOnEditTextImeBackListener(this);
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
        if (!isSearching && deleteAllButtonVisibilityListener != null && this.histories.isEmpty()) {
            deleteAllButtonVisibilityListener.setVisibility(!histories.isEmpty());
        }
        if (!histories.isEmpty()) {
            scrollListener.setLoading(false);
        }
        if (isSearching) {
            updateNotFoundLayoutVisibility(histories.isEmpty());
        }
        if (!isSearching && histories.isEmpty() && this.histories.isEmpty()) {
            updateErrorLayoutVisibility(true);
        } else {
            if (!isSearching)
                updateErrorLayoutVisibility(false);
            if (this.histories.isEmpty()) {
                this.histories = histories;
                adapter.setHistories(histories);
            } else if (!histories.isEmpty()) {
                adapter.add(histories);
            }
        }
        offset = this.histories.size();
    }

    @Override
    public void onHistoryDeleted(int position) {
        adapter.deleteItem(position);
        if (this.histories.isEmpty()) {
            updateErrorLayoutVisibility(true);
            if (deleteAllButtonVisibilityListener != null) {
                deleteAllButtonVisibilityListener.setVisibility(!histories.isEmpty());
            }
        }
        offset = this.histories.size();
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
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
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
        new AlertDialog.Builder(context)
                .setTitle(R.string.deleting)
                .setMessage(R.string.deleting_message)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    presenter.delete(history, position, isFavourite);
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {

                })
                .show();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            histories.clear();
            scrollListener.setEnabled(false);
            presenter.search(s.toString(), isFavourite);
        } else if (isSearching) {
            scrollListener.setEnabled(true);
            getHistories();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        clearImageButton.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        changeSearchLayout(true);
        isSearching = true;
        return false;
    }

    @Override
    public void onLoadMore() {
        if (!isSearching && offset > 0) {
            scrollListener.setLoading(true);
            presenter.getHistories(offset, LIMIT, isFavourite);
        }
    }

    @OnClick(R.id.clear_image_button)
    public void onClearButtonClick() {
        searchEditText.setText("");
        isSearching = true;
        getHistories();
        if (!isKeyboardOpen) {
            handler.post(() -> {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(searchEditText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                searchEditText.requestFocus();
                isKeyboardOpen = true;
            });
        }
        changeSearchLayout(true);
    }

    @Override
    public void onImeBack() {
        changeSearchLayout(false);
        scrollListener.setEnabled(true);
        scrollListener.setLoading(false);
        isSearching = false;
    }

    private void getHistories() {
        histories.clear();
        offset = 0;
        adapter.setHistories(new ArrayList<>());
        presenter.getHistories(offset, LIMIT, isFavourite);
    }

    private void changeSearchLayout(boolean isActive) {
        searchLayout.setBackground(isActive ? borderBottomActive : borderBottom);
        searchEditText.setCursorVisible(isActive);
        isKeyboardOpen = isActive;
    }

    private void updateErrorLayoutVisibility(boolean isDisplay) {
        errorMessage.setText(isFavourite ? R.string.favourites_error_message : R.string.history_error_message);
        searchLayout.setVisibility(isDisplay ? View.GONE : View.VISIBLE);
        recyclerView.setVisibility(isDisplay ? View.GONE : View.VISIBLE);
        errorLayout.setVisibility(isDisplay ? View.VISIBLE : View.GONE);
    }

    private void updateNotFoundLayoutVisibility(boolean isDisplay) {
        errorMessage.setText(R.string.search_not_found_message);
        recyclerView.setVisibility(isDisplay ? View.GONE : View.VISIBLE);
        errorLayout.setVisibility(isDisplay ? View.VISIBLE : View.GONE);
    }

}
