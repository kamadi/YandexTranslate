package kz.kamadi.yandextranslate.presenter;


import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.mapper.data.TranslationDataMapper;
import kz.kamadi.yandextranslate.domain.entity.TranslationEntity;
import kz.kamadi.yandextranslate.domain.interactor.translate.GetTranslationUseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;
import kz.kamadi.yandextranslate.ui.translate.TranslateView;

public class TranslationPresenter extends BasePresenter {

    TranslateView view;
    private GetTranslationUseCase getTranslationUseCase;
    private TranslationDataMapper translationDataMapper;

    @Inject
    public TranslationPresenter(GetTranslationUseCase getTranslationUseCase, TranslationDataMapper translationDataMapper) {
        super(getTranslationUseCase);
        this.getTranslationUseCase = getTranslationUseCase;
        this.translationDataMapper = translationDataMapper;
    }

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.view = (TranslateView) view;
    }

    @Override
    public void detachView() {
        super.detachView();
        this.view = null;
    }

    public void translate(String text, String lang) {
        getTranslationUseCase.setParams(text, lang);
        getTranslationUseCase.execute(new TranslateGetSubscriber());
    }

    protected class TranslateGetSubscriber extends BaseSubscriber<TranslationEntity> {

        @Override
        public void onNext(TranslationEntity translationEntity) {
            super.onNext(translationEntity);
            if (view != null) {
                view.showTranslation(translationDataMapper.transform(translationEntity));
            }
        }
    }
}
