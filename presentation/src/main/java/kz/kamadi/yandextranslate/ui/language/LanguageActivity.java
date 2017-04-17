package kz.kamadi.yandextranslate.ui.language;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.Language;
import kz.kamadi.yandextranslate.data.manager.LanguageManager;
import kz.kamadi.yandextranslate.presenter.LanguagePresenter;
import kz.kamadi.yandextranslate.ui.base.BaseActivity;
import kz.kamadi.yandextranslate.ui.helper.RecyclerViewItemClickListener;

public class LanguageActivity extends BaseActivity implements LanguageView, RecyclerViewItemClickListener.OnItemClickListener {

    public static final int SOURCE = 0;
    public static final int TARGET = 1;

    public static final String TYPE = "kz.kamadi.yandextranslate.ui.language.LanguageActivity.TYPE";
    public static final String SOURCE_LANGUAGE = "kz.kamadi.yandextranslate.ui.language.LanguageActivity.SOURCE_LANGUAGE";
    public static final String TARGET_LANGUAGE = "kz.kamadi.yandextranslate.ui.language.LanguageActivity.TARGET_LANGUAGE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar_title)
    TextView titleTextView;

    @Inject
    LanguagePresenter presenter;
    @Inject
    LanguageManager languageManager;

    private LanguageAdapter adapter;
    private List<Language> languages;
    private int type;
    private Language sourceLanguage, targetLanguage;

    public static void start(Activity context, int requestCode, Language sourceLanguage, Language targetLanguage, int type) {
        Intent intent = new Intent(context, LanguageActivity.class);
        intent.putExtra(TYPE, type);
        intent.putExtra(SOURCE_LANGUAGE, sourceLanguage);
        intent.putExtra(TARGET_LANGUAGE, targetLanguage);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(null);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));
        recyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this, this));
        swipeRefreshLayout.setEnabled(false);
        type = getIntent().getIntExtra(TYPE, SOURCE);

        if (type == SOURCE) {
            titleTextView.setText(R.string.source_language);
        } else {
            titleTextView.setText(R.string.target_language);
        }
        sourceLanguage = getIntent().getParcelableExtra(SOURCE_LANGUAGE);
        targetLanguage = getIntent().getParcelableExtra(TARGET_LANGUAGE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
        if (adapter == null) {
            if (languages == null) {
                presenter.getLanguages();
            } else {
                adapter = new LanguageAdapter(this, languages, getSelectedLanguage());
                recyclerView.setAdapter(adapter);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detachView();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void handleError(Throwable error) {

    }

    @Override
    public void showLanguages(List<Language> languages) {
        this.languages = languages;
        adapter = new LanguageAdapter(this, languages, getSelectedLanguage());
        recyclerView.setAdapter(adapter);
    }

    private Language getSelectedLanguage() {
        return type == SOURCE ? sourceLanguage : targetLanguage;
    }

    @Override
    public void onItemClick(View view, int position) {
        Language selectedLanguage = languages.get(position);

        if ((type == SOURCE && !selectedLanguage.equals(sourceLanguage) && selectedLanguage.equals(targetLanguage))
                || (type == TARGET && selectedLanguage.equals(sourceLanguage) && !selectedLanguage.equals(targetLanguage))) {
            languageManager.saveTargetLanguage(sourceLanguage);
            languageManager.saveSourceLanguage(targetLanguage);
        } else if (type == SOURCE && !selectedLanguage.equals(sourceLanguage) && !selectedLanguage.equals(targetLanguage)) {
            languageManager.saveSourceLanguage(selectedLanguage);
        } else if (type == TARGET && !selectedLanguage.equals(sourceLanguage) && !selectedLanguage.equals(targetLanguage)) {
            languageManager.saveTargetLanguage(selectedLanguage);
        }

        setResult(RESULT_OK);
        finish();
    }
}
