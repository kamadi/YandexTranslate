package kz.kamadi.yandextranslate.ui.history;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.History;

public class HistoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_text_view)
    TextView textTextView;
    @BindView(R.id.translation_text_view)
    TextView translationTextView;
    @BindView(R.id.language_text_view)
    TextView languageTextView;

    private HistoryAdapter.OnHistoryUpdateListener onHistoryUpdateListener;
    private History history;

    public HistoryViewHolder(View itemView, HistoryAdapter.OnHistoryUpdateListener onHistoryUpdateListener) {
        super(itemView);
        this.onHistoryUpdateListener = onHistoryUpdateListener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(History history) {
        this.history = history;
        textTextView.setText(history.getText());
        translationTextView.setText(history.getTranslate().getText().get(0));
        languageTextView.setText(history.getLanguage());
    }

    @OnClick(R.id.favourite_button)
    void onFavouriteButtonClick() {
        history.setFavourite(!history.isFavourite());
        onHistoryUpdateListener.onHistoryUpdate(history);
    }
}
