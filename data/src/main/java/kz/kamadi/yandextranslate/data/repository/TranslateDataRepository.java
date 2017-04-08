package kz.kamadi.yandextranslate.data.repository;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.TranslateEntityMapper;
import kz.kamadi.yandextranslate.data.network.TranslateApi;
import kz.kamadi.yandextranslate.domain.entity.TranslateEntity;
import kz.kamadi.yandextranslate.domain.repository.TranslateRepository;

public class TranslateDataRepository implements TranslateRepository {

    private TranslateApi api;
    private TranslateEntityMapper mapper;

    @Inject
    public TranslateDataRepository(TranslateApi api, TranslateEntityMapper mapper) {
        this.api = api;
        this.mapper = mapper;
    }

    @Override
    public Observable<TranslateEntity> getTranslate(String text, String lang) {
        return api.translate(text,lang).map(translate -> mapper.transform(translate));
    }
}
