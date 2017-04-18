package kz.kamadi.yandextranslate.ui.translate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kz.kamadi.yandextranslate.R;

public class TranslationActivity extends AppCompatActivity {

    private static final String TRANSLATION = "kz.kamadi.yandextranslate.ui.translate.TranslationActivity.TRANSLATION";

    @BindView(R.id.translation_text_view)
    TextView translationTextView;

    public static void start(Context context, String translation) {
        Intent intent = new Intent(context, TranslationActivity.class);
        intent.putExtra(TRANSLATION, translation);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);
        ButterKnife.bind(this);
        String translation = getIntent().getStringExtra(TRANSLATION);
        if (translation.length() > 75) {
            translationTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.translation_small));
        } else if (translation.length() > 50) {
            translationTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.translation_medium));
        } else {
            translationTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.translation_large));
        }
        translationTextView.setText(translation);
    }

    @OnClick(R.id.close_button)
    void onCancelClick() {
        finish();
    }
}
