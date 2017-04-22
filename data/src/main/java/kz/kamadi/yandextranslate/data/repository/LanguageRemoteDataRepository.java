package kz.kamadi.yandextranslate.data.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.network.TranslateApi;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

public class LanguageRemoteDataRepository implements LanguageRepository {

    private TranslateApi api;

    @Inject
    public LanguageRemoteDataRepository(TranslateApi api) {
        this.api = api;
    }

    @Override
    public Observable<List<LanguageEntity>> getLanguages() {
        return api.getLanguages("ru").map(response -> {
            List<LanguageEntity> entities = new ArrayList<>();
            for (Map.Entry<String, String> entry : response.getLangs().entrySet()) {
                entities.add(new LanguageEntity(entry.getValue(), entry.getKey()));
            }
            Collections.sort(entities,(o1, o2) -> o1.getCode().compareTo(o2.getCode()));
            return entities;
        });
    }

    @Override
    public Observable<Boolean> create(List<LanguageEntity> entities) {
        throw new UnsupportedOperationException("not supported");
    }

    @Override
    public Observable<LanguageEntity> getLanguageEntity(String code) {
        throw new UnsupportedOperationException("not supported");
    }
}
