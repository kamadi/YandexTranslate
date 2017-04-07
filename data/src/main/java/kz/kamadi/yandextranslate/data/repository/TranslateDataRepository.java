package kz.kamadi.yandextranslate.data.repository;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.network.TranslateApi;
import kz.kamadi.yandextranslate.domain.entity.TranslateEntity;
import kz.kamadi.yandextranslate.domain.repository.TranslateRepository;

public class TranslateDataRepository implements TranslateRepository {

    private TranslateApi api;

    @Inject
    public TranslateDataRepository(TranslateApi api) {
        this.api = api;
    }

    @Override
    public Observable<TranslateEntity> getTranslate(String text, String lang) {
        return null;
    }
}
