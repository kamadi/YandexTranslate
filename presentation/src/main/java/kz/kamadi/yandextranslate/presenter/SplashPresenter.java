package kz.kamadi.yandextranslate.presenter;

import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.dependency.ActivityScope;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.interactor.language.CreateLanguagesUseCase;
import kz.kamadi.yandextranslate.domain.interactor.language.DownloadLanguagesUseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;
import kz.kamadi.yandextranslate.ui.splash.SplashView;

@ActivityScope
public class SplashPresenter extends BasePresenter {

    SplashView view;
    private DownloadLanguagesUseCase downloadLanguagesUseCase;
    private CreateLanguagesUseCase createLanguagesUseCase;

    @Inject
    public SplashPresenter(DownloadLanguagesUseCase downloadLanguagesUseCase, CreateLanguagesUseCase createLanguagesUseCase) {
        super(downloadLanguagesUseCase, createLanguagesUseCase);
        this.downloadLanguagesUseCase = downloadLanguagesUseCase;
        this.createLanguagesUseCase = createLanguagesUseCase;
    }

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.view = (SplashView) view;
    }

    @Override
    public void detachView() {
        super.detachView();
        this.view = null;
    }

    public void downloadLanguages() {
        downloadLanguagesUseCase.execute(new LanguagesDownloadSubscriber());
    }

    public void createLanguages(List<LanguageEntity> entities) {
        createLanguagesUseCase.setParam(entities);
        createLanguagesUseCase.execute(new LanguagesCreateSubscriber());
    }

    protected class LanguagesDownloadSubscriber extends BaseSubscriber<List<LanguageEntity>> {

        @Override
        public void onNext(List<LanguageEntity> entities) {
            super.onNext(entities);
            if (view != null) {
                view.onLanguagesDownloaded(entities);
            }
        }
    }

    protected class LanguagesCreateSubscriber extends BaseSubscriber<Boolean> {

        @Override
        public void onNext(Boolean aBoolean) {
            super.onNext(aBoolean);
            if (view != null) {
                view.onLanguagesCreated();
            }
        }
    }
}
