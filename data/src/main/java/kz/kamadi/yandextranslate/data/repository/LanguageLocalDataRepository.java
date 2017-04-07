package kz.kamadi.yandextranslate.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.database.language.LanguageDao;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

public class LanguageLocalDataRepository implements LanguageRepository {

    private LanguageDao languageDao;

    @Inject
    public LanguageLocalDataRepository(LanguageDao languageDao) {
        this.languageDao = languageDao;
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
