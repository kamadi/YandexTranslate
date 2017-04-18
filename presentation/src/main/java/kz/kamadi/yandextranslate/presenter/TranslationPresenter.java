package kz.kamadi.yandextranslate.presenter;


import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.database.history.HistoryScheme;
import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.Translation;
import kz.kamadi.yandextranslate.data.entity.mapper.data.HistoryDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.data.TranslationDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.HistoryEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.entity.TranslationEntity;
import kz.kamadi.yandextranslate.domain.interactor.history.CreateHistoryUseCase;
import kz.kamadi.yandextranslate.domain.interactor.history.UpdateHistoryUseCase;
import kz.kamadi.yandextranslate.domain.interactor.translate.GetTranslationUseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;
import kz.kamadi.yandextranslate.ui.translate.TranslateView;

public class TranslationPresenter extends BasePresenter {

    TranslateView view;
    private GetTranslationUseCase getTranslationUseCase;
    private CreateHistoryUseCase createHistoryUseCase;
    private UpdateHistoryUseCase updateHistoryUseCase;
    private TranslationDataMapper translationDataMapper;
    private HistoryEntityMapper historyEntityMapper;
    private HistoryDataMapper historyDataMapper;

    @Inject
    public TranslationPresenter(GetTranslationUseCase getTranslationUseCase, CreateHistoryUseCase createHistoryUseCase, UpdateHistoryUseCase updateHistoryUseCase,
                                TranslationDataMapper translationDataMapper, HistoryEntityMapper historyEntityMapper, HistoryDataMapper historyDataMapper) {
        super(getTranslationUseCase);
        this.getTranslationUseCase = getTranslationUseCase;
        this.createHistoryUseCase = createHistoryUseCase;
        this.updateHistoryUseCase = updateHistoryUseCase;
        this.translationDataMapper = translationDataMapper;
        this.historyEntityMapper = historyEntityMapper;
        this.historyDataMapper = historyDataMapper;
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
        if (view != null) {
            view.showLoading();
            getTranslationUseCase.setParams(text, lang);
            getTranslationUseCase.execute(new TranslateGetSubscriber(text, lang));
        }
    }

    public void createHistory(Translation translation) {
        if (view != null) {
            History history = new History();
            history.setText(translation.getText());
            history.setLanguage(translation.getLanguage());
            history.setFavourite(false);
            history.setDictionary(translation.getDictionary());
            history.setTranslate(translation.getTranslate());
            history.setStatus(HistoryScheme.STATUS_ACTIVE);
            createHistoryUseCase.setParam(historyEntityMapper.transform(history));
            createHistoryUseCase.execute(new HistoryCreateSubscriber());
        }
    }

    public void updateHistory(History history) {
        updateHistoryUseCase.setParam(historyEntityMapper.transform(history));
        updateHistoryUseCase.execute(new HistoryUpdateSubscriber());
    }

    protected class TranslateGetSubscriber extends BaseSubscriber<TranslationEntity> {

        private String text;
        private String lang;

        public TranslateGetSubscriber(String text, String lang) {
            this.text = text;
            this.lang = lang;
        }

        @Override
        public void onNext(TranslationEntity translationEntity) {
            super.onNext(translationEntity);
            translationEntity.setText(text);
            translationEntity.setLanguage(lang);
            if (view != null) {
                view.showTranslation(translationDataMapper.transform(translationEntity));
            }
        }
    }

    protected class HistoryCreateSubscriber extends BaseSubscriber<HistoryEntity> {
        @Override
        public void onComplete() {

        }

        @Override
        public void onNext(HistoryEntity entity) {
            if (view != null) {
                view.onHistoryCreated(historyDataMapper.transform(entity));
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    }

    protected class HistoryUpdateSubscriber extends BaseSubscriber<Boolean> {

        @Override
        public void onComplete() {

        }

        @Override
        public void onNext(Boolean aBoolean) {

        }

        @Override
        public void onError(Throwable e) {

        }
    }
}
