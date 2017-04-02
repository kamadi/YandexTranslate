package kz.kamadi.yandextranslate.ui.history;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;

public class HistoryItemFragment extends BaseFragment implements View.OnFocusChangeListener {
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

    HistoryAdapter adapter;

    @Override
    protected int layoutId() {
        return R.layout.fragment_history_item;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchEditText.setOnFocusChangeListener(this);
        adapter = new HistoryAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
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
}
