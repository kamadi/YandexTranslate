package kz.kamadi.yandextranslate.ui.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.Language;
import kz.kamadi.yandextranslate.data.manager.LanguageManager;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.presenter.SplashPresenter;
import kz.kamadi.yandextranslate.ui.base.BaseActivity;
import kz.kamadi.yandextranslate.ui.main.MainActivity;

public class SplashActivity extends BaseActivity implements SplashView {

    @BindView(R.id.error_text_view)
    TextView errorTextView;

    @Inject
    LanguageManager languageManager;

    @Inject
    SplashPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!languageManager.isDownloaded()) {
            presenter.attachView(this);
            presenter.downloadLanguages();
        }else {
            MainActivity.start(this);
        }
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
        errorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLanguagesDownloaded(List<LanguageEntity> entities) {
        presenter.createLanguages(entities);
    }

    @Override
    public void onLanguagesCreated() {
        languageManager.setDownloaded();
        languageManager.saveSourceLanguage(new Language("Английский", "en"));
        languageManager.saveTargetLanguage(new Language("Русский", "ru"));
        MainActivity.start(this);
    }
}
