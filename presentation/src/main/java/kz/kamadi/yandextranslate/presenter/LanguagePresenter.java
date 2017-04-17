package kz.kamadi.yandextranslate.presenter;

import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.mapper.data.LanguageDataMapper;
import kz.kamadi.yandextranslate.dependency.ActivityScope;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.interactor.language.GetLanguagesUseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;
import kz.kamadi.yandextranslate.ui.language.LanguageView;

@ActivityScope
public class LanguagePresenter extends BasePresenter {

    private GetLanguagesUseCase getLanguagesUseCase;
    private LanguageDataMapper languageDataMapper;

    LanguageView view;

    @Inject
    public LanguagePresenter(GetLanguagesUseCase getLanguagesUseCase, LanguageDataMapper languageDataMapper) {
        super(getLanguagesUseCase);
        this.getLanguagesUseCase = getLanguagesUseCase;
        this.languageDataMapper = languageDataMapper;
    }

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.view = (LanguageView) view;
    }

    @Override
    public void detachView() {
        super.detachView();
        this.view = null;
    }

    public void getLanguages(){
        view.showLoading();
        getLanguagesUseCase.execute(new LanguagesGetSubscriber());
    }

    protected class LanguagesGetSubscriber extends BaseSubscriber<List<LanguageEntity>> {
        @Override
        public void onNext(List<LanguageEntity> entities) {
            super.onNext(entities);
            if (view != null) {
                view.showLanguages(languageDataMapper.transform(entities));
            }
        }
    }
}
