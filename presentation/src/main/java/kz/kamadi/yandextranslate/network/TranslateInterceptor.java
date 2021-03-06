package kz.kamadi.yandextranslate.network;

import java.io.IOException;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TranslateInterceptor implements Interceptor {

    @Inject
    public TranslateInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        HttpUrl url = chain.request().url()
                .newBuilder()
                .addQueryParameter("key", BuildConfig.TRANSLATE_API_KEY)
                .build();
        Request request = chain.request().newBuilder().url(url).build();
        return chain.proceed(request);
    }
}
