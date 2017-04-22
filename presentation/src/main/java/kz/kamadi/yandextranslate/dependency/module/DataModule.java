package kz.kamadi.yandextranslate.dependency.module;

import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.kamadi.yandextranslate.data.database.history.HistoryDao;
import kz.kamadi.yandextranslate.data.database.history.HistoryDaoImpl;
import kz.kamadi.yandextranslate.data.database.language.LanguageDao;
import kz.kamadi.yandextranslate.data.database.language.LanguageDaoImpl;
import kz.kamadi.yandextranslate.data.repository.DictionaryDataRepository;
import kz.kamadi.yandextranslate.data.repository.HistoryDataRepository;
import kz.kamadi.yandextranslate.data.repository.LanguageLocalDataRepository;
import kz.kamadi.yandextranslate.data.repository.LanguageRemoteDataRepository;
import kz.kamadi.yandextranslate.data.repository.TranslateDataRepository;
import kz.kamadi.yandextranslate.database.DatabaseHelper;
import kz.kamadi.yandextranslate.domain.repository.DictionaryRepository;
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

    @Named("local")
    @Provides
    @Singleton
    LanguageRepository provideLanguageRepositoryLocal(LanguageLocalDataRepository languageLocalDataRepository) {
        return languageLocalDataRepository;
    }

    @Named("remote")
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

    @Provides
    @Singleton
    HistoryDao provideHistoryDao(HistoryDaoImpl historyDao){
        return historyDao;
    }

    @Provides
    @Singleton
    LanguageDao provideLanguageDao(LanguageDaoImpl languageDao){
        return languageDao;
    }

    @Provides
    @Singleton
    SQLiteDatabase provideSqLiteDatabase(DatabaseHelper databaseHelper){
        return databaseHelper.getWritableDatabase();
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new Gson();
    }
}
