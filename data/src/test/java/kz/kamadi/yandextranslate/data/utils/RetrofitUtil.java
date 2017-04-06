package kz.kamadi.yandextranslate.data.utils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;


public class RetrofitUtil {

    public static <T> T create(MockWebServer mockWebServer, final Class<T> service) {
        return new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
//                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(service);
    }
}
