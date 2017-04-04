package kz.kamadi.yandextranslate.domain.repository;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.DictionaryEntity;

public interface DictionaryRepository {

    Observable<DictionaryEntity> getDictionary(String text, String lang);
}
