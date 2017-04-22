package kz.kamadi.yandextranslate.dependency.component;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import kz.kamadi.yandextranslate.dependency.module.ApplicationModule;
import kz.kamadi.yandextranslate.dependency.module.DataModule;
import kz.kamadi.yandextranslate.dependency.module.NetworkModule;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.repository.DictionaryRepository;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;
import kz.kamadi.yandextranslate.domain.repository.TranslateRepository;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, NetworkModule.class})
public interface ApplicationComponent {

    Context context();

    SharedPreferences sharedPreferences();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    TranslateRepository translateRepository();

    HistoryRepository historyRepository();

    @Named("local")
    LanguageRepository localLanguageRepository();

    @Named("remote")
    LanguageRepository remoteLanguageRepository();

    DictionaryRepository dictionaryRepository();
    
}
