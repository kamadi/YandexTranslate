package kz.kamadi.yandextranslate.data.repository;

import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.entity.Dictionary;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.DictionaryEntityMapper;
import kz.kamadi.yandextranslate.data.network.DictionaryApi;
import kz.kamadi.yandextranslate.domain.entity.DictionaryEntity;
import kz.kamadi.yandextranslate.domain.repository.DictionaryRepository;

public class DictionaryDataRepository implements DictionaryRepository {

    private DictionaryApi api;
    private Gson gson;
    private DictionaryEntityMapper mapper;

    @Inject
    public DictionaryDataRepository(DictionaryApi api, Gson gson, DictionaryEntityMapper mapper) {
        this.api = api;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    public Observable<DictionaryEntity> getDictionary(String text, String lang) {
        return api.getDictionary(text, lang).map(responseBody -> {
            if (responseBody.isSuccessful()) {
                String response = responseBody.body().string();
                Dictionary dictionary = gson.fromJson(response, Dictionary.class);
                dictionary.setContent(response);
                return mapper.transform(dictionary);
            }
            return new DictionaryEntity();
        });
    }
}
