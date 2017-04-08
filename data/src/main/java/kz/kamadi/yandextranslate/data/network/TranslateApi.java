package kz.kamadi.yandextranslate.data.network;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.entity.LanguageResponse;
import kz.kamadi.yandextranslate.data.entity.Translate;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TranslateApi {

    @FormUrlEncoded
    @POST("/api/v1.5/tr.json/translate")
    Observable<Translate> translate(@Query("lang") String lang, @Field("text") String text);

    @GET("/api/v1.5/tr.json/getLangs")
    Observable<LanguageResponse> getLanguages(@Query("ui") String ru);
}
