package kz.kamadi.yandextranslate.ui.language;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.Language;

public class ViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.name_text_view)
    TextView nameTextView;

    ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(Language language) {
        nameTextView.setText(language.getName());
    }
}
