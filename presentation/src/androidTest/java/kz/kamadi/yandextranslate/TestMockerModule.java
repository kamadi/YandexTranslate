package kz.kamadi.yandextranslate;


import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;
import kz.kamadi.yandextranslate.dependency.ActivityScope;
import kz.kamadi.yandextranslate.presenter.HistoryItemPresenter;
import kz.kamadi.yandextranslate.presenter.HistoryPresenter;
import kz.kamadi.yandextranslate.presenter.LanguagePresenter;
import kz.kamadi.yandextranslate.presenter.SplashPresenter;
import kz.kamadi.yandextranslate.presenter.TranslationPresenter;

import static org.mockito.Mockito.mock;

@Module
public class TestMockerModule {

    @ActivityScope
    @Provides
    HistoryItemPresenter provideHistoryItemPresenter(){
        return mock(HistoryItemPresenter.class);
    }

    @ActivityScope
    @Provides
    HistoryPresenter provideHistoryPresenter(){
        return mock(HistoryPresenter.class);
    }

    @ActivityScope
    @Provides
    LanguagePresenter provideLanguagePresenter(){
        return mock(LanguagePresenter.class);
    }

    @ActivityScope
    @Provides
    SplashPresenter provideSplashPresenter(){
        return Mockito.mock(SplashPresenter.class);
    }

    @ActivityScope
    @Provides
    TranslationPresenter provideTranslationPresenter(){
        return mock(TranslationPresenter.class);
    }
}
