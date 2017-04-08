package kz.kamadi.yandextranslate.data.utils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitUtil {

    public static <T> T create(MockWebServer mockWebServer, final Class<T> service, boolean isConverterEnabled) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        if (isConverterEnabled) {
            builder.addConverterFactory(GsonConverterFactory.create());
        }

        return builder.build()
                .create(service);
    }
}
