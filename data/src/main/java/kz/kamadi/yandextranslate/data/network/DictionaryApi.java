package kz.kamadi.yandextranslate.data.network;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DictionaryApi {

    @GET("/api/v1/dicservice.json/lookup")
    Observable<Response<ResponseBody>> getDictionary(@Query("text") String text, @Query("lang") String language);
}
