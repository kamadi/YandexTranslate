package kz.kamadi.yandextranslate.domain.repository;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.TranslateEntity;

public interface TranslateRepository {

    Observable<TranslateEntity> getTranslate(String text, String lang);
}
