package kz.kamadi.yandextranslate.data.repository;

import java.util.List;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.network.TranslateApi;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

public class LanguageRemoteDataRepository implements LanguageRepository{

    private TranslateApi api;

    public LanguageRemoteDataRepository(TranslateApi api) {
        this.api = api;
    }

    @Override
    public Observable<List<LanguageEntity>> getLanguages() {
        return null;
    }

    @Override
    public Observable<Boolean> create(List<LanguageEntity> entities) {
        return null;
    }
}
