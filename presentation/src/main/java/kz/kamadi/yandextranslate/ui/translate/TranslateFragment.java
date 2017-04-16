package kz.kamadi.yandextranslate.ui.translate;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.OnClick;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.Language;
import kz.kamadi.yandextranslate.data.entity.Translation;
import kz.kamadi.yandextranslate.data.manager.LanguageManager;
import kz.kamadi.yandextranslate.presenter.TranslationPresenter;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;
import kz.kamadi.yandextranslate.ui.widgets.DictionaryView;
import kz.kamadi.yandextranslate.ui.widgets.TranslateEditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class TranslateFragment extends BaseFragment implements TranslateView, TextWatcher, TranslateEditText.EditTextImeBackListener, View.OnTouchListener {

    private final long DELAY = 1000;
    @BindView(R.id.dictionary_view)
    DictionaryView dictionaryView;
    @BindView(R.id.translation_text_view)
    TextView translationTextView;
    @BindView(R.id.result_layout)
    RelativeLayout resultLayout;
    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;
    @BindView(R.id.input_layout)
    RelativeLayout inputLayout;
    @BindView(R.id.text_edit_text)
    TranslateEditText translateEditText;
    @BindView(R.id.primary_lang_text_view)
    TextView primaryLangTextView;
    @BindView(R.id.translation_lang_text_view)
    TextView translationLangTextView;
    @BindDrawable(R.drawable.border)
    Drawable border;
    @BindDrawable(R.drawable.border_yellow)
    Drawable borderActive;
    @Inject
    TranslationPresenter presenter;
    @Inject
    LanguageManager languageManager;
    Set<String> texts = new HashSet<>();
    private Language primaryLanguage, translationLanguage;
    private boolean isLoading = false;
    private boolean isKeyboardClosed = true;
    private long lastEditTextTime = 0;

    private Handler handler = new Handler();
    private Runnable inputFinishChecker = () -> {
        if (System.currentTimeMillis() > (lastEditTextTime + DELAY - 500)) {
            presenter.translate(translateEditText.getText().toString(), primaryLanguage.getCode() + "-" + translationLanguage.getCode());
        }
    };

    private Translation translation;

    @Override
    protected int layoutId() {
        return R.layout.fragment_translate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        translateEditText.setOnEditTextImeBackListener(this);
        translateEditText.addTextChangedListener(this);
        translateEditText.setOnTouchListener(this);
        primaryLanguage = languageManager.getPrimaryLanguage();
        translationLanguage = languageManager.getTranslationLanguage();
        if (primaryLanguage != null) {
            primaryLangTextView.setText(primaryLanguage.getName());
        }

        if (translationLanguage != null) {
            translationLangTextView.setText(translationLanguage.getName());
        }
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
    public void showLoading() {
        resultLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
        isLoading = true;
    }

    @Override
    public void hideLoading() {
        loadingLayout.setVisibility(View.GONE);
        resultLayout.setVisibility(View.VISIBLE);
        isLoading = false;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleError(Throwable error) {

    }

    @Override
    public void showTranslation(Translation translation) {
        this.translation = translation;
        translationTextView.setText(translation.getTranslate().getText().get(0));
        dictionaryView.setDictionary(translation.getDictionary());
    }

    @Override
    public void onHistoryCreated(History history) {
        Log.e("history", history.getId() + "");
    }

    @OnClick(R.id.clear_image_button)
    void onCleatButtonClick() {
        translateEditText.setText("");
        dictionaryView.clearView();
        translationTextView.setText("");
        resultLayout.setVisibility(View.GONE);
        openKeyboard();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        handler.removeCallbacks(inputFinishChecker);
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            lastEditTextTime = System.currentTimeMillis();
            handler.postDelayed(inputFinishChecker, DELAY);
        }
    }

    @Override
    public void onImeBack() {
        inputLayout.setBackground(border);
        translateEditText.setCursorVisible(false);
        if (translation != null && !isLoading && texts.add(translateEditText.getText().toString())) {
            isKeyboardClosed = true;
            presenter.createHistory(translateEditText.getText().toString(), "en-ru", translation);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        inputLayout.setBackground(borderActive);
        translateEditText.setCursorVisible(true);
        isKeyboardClosed = false;
        return false;
    }

    @OnClick(R.id.language_switch_view)
    void onLanguageSwitchClick(){
        String name = primaryLanguage.getName();
        String code = primaryLanguage.getCode();
        primaryLanguage = new Language(translationLanguage);
        translationLanguage = new Language(name,code);
        primaryLangTextView.setText(primaryLanguage.getName());
        translationLangTextView.setText(translationLanguage.getName());
        languageManager.savePrimaryLanguage(primaryLanguage);
        languageManager.saveTranslationLanguage(translationLanguage);
    }

    private void openKeyboard() {
        handler.post(() -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.toggleSoftInputFromWindow(translateEditText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
            translateEditText.requestFocus();
            isKeyboardClosed = false;
        });
    }
}
