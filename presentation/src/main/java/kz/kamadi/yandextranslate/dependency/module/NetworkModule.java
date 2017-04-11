package kz.kamadi.yandextranslate.dependency.module;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.kamadi.yandextranslate.BuildConfig;
import kz.kamadi.yandextranslate.data.network.DictionaryApi;
import kz.kamadi.yandextranslate.data.network.TranslateApi;
import kz.kamadi.yandextranslate.network.DictionaryInterceptor;
import kz.kamadi.yandextranslate.network.TranslateInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Module
public class NetworkModule {

    @Named("translate")
    @Provides
    @Singleton
    OkHttpClient provideTranslateOkHttpClient(TranslateInterceptor interceptor) {
        return new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
    }

    @Named("dictionary")
    @Provides
    @Singleton
    OkHttpClient provideDictionaryOkHttpClient(DictionaryInterceptor interceptor) {
        return new OkHttpClient().newBuilder().addInterceptor(interceptor).build();
    }

    @Provides
    @Singleton
    TranslateApi provideTranslateApi(@Named("translate") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.TRANSLATE_API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(TranslateApi.class);
    }

    @Provides
    @Singleton
    DictionaryApi provideDictionaryApi(@Named("translate") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.DICTIONARY_API_URL)
                .client(okHttpClient)
                .build()
                .create(DictionaryApi.class);
    }
}
