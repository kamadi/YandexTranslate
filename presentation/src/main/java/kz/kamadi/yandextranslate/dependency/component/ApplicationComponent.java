package kz.kamadi.yandextranslate.dependency.component;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import kz.kamadi.yandextranslate.dependency.module.ApplicationModule;
import kz.kamadi.yandextranslate.dependency.module.DataModule;
import kz.kamadi.yandextranslate.dependency.module.NetworkModule;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;

@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, NetworkModule.class})
public interface ApplicationComponent {

    Context context();

    SharedPreferences sharedPreferences();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();



}
