package kz.kamadi.yandextranslate.dependency.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.kamadi.yandextranslate.data.repository.DictionaryDataRepository;
import kz.kamadi.yandextranslate.data.repository.FavouriteDataRepository;
import kz.kamadi.yandextranslate.data.repository.HistoryDataRepository;
import kz.kamadi.yandextranslate.data.repository.LanguageLocalDataRepository;
import kz.kamadi.yandextranslate.data.repository.LanguageRemoteDataRepository;
import kz.kamadi.yandextranslate.data.repository.TranslateDataRepository;
import kz.kamadi.yandextranslate.domain.repository.DictionaryRepository;
import kz.kamadi.yandextranslate.domain.repository.FavouriteRepository;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;
import kz.kamadi.yandextranslate.domain.repository.TranslateRepository;

@Module
public class DataModule {

    @Provides
    @Singleton
    TranslateRepository provideTranslateRepository(TranslateDataRepository translateDataRepository) {
        return translateDataRepository;
    }

    @Provides
    @Singleton
    HistoryRepository provideHistoryRepository(HistoryDataRepository historyDataRepository) {
        return historyDataRepository;
    }

    @Provides
    @Singleton
    FavouriteRepository provideFavouriteRepository(FavouriteDataRepository favouriteDataRepository) {
        return favouriteDataRepository;
    }

    @Named("local")
    @Provides
    @Singleton
    LanguageRepository provideLanguageRepositoryLocal(LanguageLocalDataRepository languageLocalDataRepository) {
        return languageLocalDataRepository;
    }

    @Named("local")
    @Provides
    @Singleton
    LanguageRepository provideLanguageRepositoryRemote(LanguageRemoteDataRepository languageRemoteDataRepository) {
        return languageRemoteDataRepository;
    }

    @Provides
    @Singleton
    DictionaryRepository provideDictionaryRepository(DictionaryDataRepository dictionaryDataRepository) {
        return dictionaryDataRepository;
    }
}
