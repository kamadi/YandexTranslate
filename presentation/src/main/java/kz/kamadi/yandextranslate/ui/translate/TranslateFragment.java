package kz.kamadi.yandextranslate.ui.translate;

import android.content.Intent;
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
import android.widget.ImageButton;
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
import kz.kamadi.yandextranslate.ui.language.LanguageActivity;
import kz.kamadi.yandextranslate.ui.widgets.DictionaryView;
import kz.kamadi.yandextranslate.ui.widgets.TranslateEditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class TranslateFragment extends BaseFragment implements TranslateView, TextWatcher, TranslateEditText.EditTextImeBackListener, View.OnTouchListener {

    private static final int REQUEST_CODE = 5;

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
    @BindView(R.id.source_lang_text_view)
    TextView primaryLangTextView;
    @BindView(R.id.target_lang_text_view)
    TextView translationLangTextView;
    @BindView(R.id.favourite_button)
    ImageButton favouriteButton;
    @BindDrawable(R.drawable.border)
    Drawable border;
    @BindDrawable(R.drawable.border_yellow)
    Drawable borderActive;
    @Inject
    TranslationPresenter presenter;
    @Inject
    LanguageManager languageManager;
    Set<String> texts = new HashSet<>();
    private Language sourceLanguage, targetLanguage;
    private boolean isLoading = false;
    private boolean isKeyboardOpen = true;
    private long lastEditTextTime = 0;

    private Handler handler = new Handler();
    private Runnable inputFinishChecker = () -> {
        if (System.currentTimeMillis() > (lastEditTextTime + DELAY - 500)) {
            translate();
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
        initLanguages();
    }

    private void initLanguages() {
        sourceLanguage = languageManager.getSourceLanguage();
        targetLanguage = languageManager.getTargetLanguage();
        if (sourceLanguage != null) {
            primaryLangTextView.setText(sourceLanguage.getName());
        }

        if (targetLanguage != null) {
            translationLangTextView.setText(targetLanguage.getName());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            initLanguages();
            handler.postDelayed(inputFinishChecker, DELAY);
        }
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
        if (!isKeyboardOpen) {
            presenter.createHistory(translation);
            texts.add(translation.getText());
        }
    }

    @Override
    public void onHistoryCreated(History history) {
        Log.e("history", history.getId() + "");
        translation.setHistory(history);
    }

    @OnClick(R.id.clear_image_button)
    void onCleatButtonClick() {
        translateEditText.setText("");
        dictionaryView.clearView();
        translationTextView.setText("");
        resultLayout.setVisibility(View.GONE);
        if (!isKeyboardOpen) {
            handler.post(() -> {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(translateEditText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                translateEditText.requestFocus();
                isKeyboardOpen = true;
            });
        }
        changeSearchLayout(true);
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
        changeSearchLayout(false);
        if (translation != null && !isLoading && texts.add(translation.getText())) {
            presenter.createHistory(translation);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        changeSearchLayout(true);
        return false;
    }

    private void changeSearchLayout(boolean isActive) {
        if (isActive) {
            inputLayout.setBackground(borderActive);
            translateEditText.setCursorVisible(true);
            isKeyboardOpen = true;
        } else {
            inputLayout.setBackground(border);
            translateEditText.setCursorVisible(false);
            isKeyboardOpen = false;
        }
    }

    @OnClick(R.id.language_switch_view)
    void onLanguageSwitchClick() {
        String name = sourceLanguage.getName();
        String code = sourceLanguage.getCode();
        sourceLanguage = new Language(targetLanguage);
        targetLanguage = new Language(name, code);
        primaryLangTextView.setText(sourceLanguage.getName());
        translationLangTextView.setText(targetLanguage.getName());
        languageManager.saveSourceLanguage(sourceLanguage);
        languageManager.saveTargetLanguage(targetLanguage);
        translate();
    }

    @OnClick(R.id.source_lang_text_view)
    void onSourceLangClick() {
        start(LanguageActivity.SOURCE);
    }

    @OnClick(R.id.target_lang_text_view)
    void onTargetLangClick() {
        start(LanguageActivity.TARGET);
    }


    private void start(int type) {
        Intent intent = new Intent(context, LanguageActivity.class);
        intent.putExtra(LanguageActivity.TYPE, type);
        intent.putExtra(LanguageActivity.SOURCE_LANGUAGE, sourceLanguage);
        intent.putExtra(LanguageActivity.TARGET_LANGUAGE, targetLanguage);
        startActivityForResult(intent, REQUEST_CODE);
    }


    private void translate() {
        if (!translateEditText.getText().toString().isEmpty()) {
            favouriteButton.setImageResource(R.drawable.favourite_not_selected);
            presenter.translate(translateEditText.getText().toString(), sourceLanguage.getCode() + "-" + targetLanguage.getCode());
        }
    }

    @OnClick(R.id.favourite_button)
    void onFavouriteButtonClick() {
        if (translation != null && translation.getHistory() != null) {
            History history = translation.getHistory();
            history.setFavourite(!history.isFavourite());
            if (history.isFavourite()) {
                favouriteButton.setImageResource(R.drawable.favourite_selected);
            } else {
                favouriteButton.setImageResource(R.drawable.favourite_not_selected);
            }
            presenter.updateHistory(history);
        }
    }

    @OnClick(R.id.share_button)
    void onShareButtonClick() {
        if (translation != null) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, translation.getText());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    @OnClick(R.id.fullscreen_button)
    void onFullscreenButtonClick() {
        if (translation != null) {
            TranslationActivity.start(getContext(),translation.getTranslate().getText().get(0));
        }
    }
}
