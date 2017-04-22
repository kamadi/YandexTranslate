package kz.kamadi.yandextranslate.domain.repository;

import java.util.List;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;

public interface LanguageRepository {

    Observable<List<LanguageEntity>> getLanguages();

    Observable<Boolean> create(List<LanguageEntity> entities);

    Observable<LanguageEntity> getLanguageEntity(String code);
}
