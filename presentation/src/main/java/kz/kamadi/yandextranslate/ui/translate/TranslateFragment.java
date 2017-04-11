package kz.kamadi.yandextranslate.ui.translate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.data.entity.Translation;
import kz.kamadi.yandextranslate.presenter.TranslationPresenter;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;
import kz.kamadi.yandextranslate.ui.widgets.DictionaryView;

public class TranslateFragment extends BaseFragment implements TranslateView {

    @BindView(R.id.dictionary_view)
    DictionaryView dictionaryView;

    @Inject
    TranslationPresenter presenter;

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

    }

    @Override
    public void showTranslation(Translation translation) {

    }

    @OnClick(R.id.clear_image_button)
    void onCleatButtonClick(){
        presenter.translate("travel","en-ru");
    }
}
