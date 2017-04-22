package kz.kamadi.yandextranslate.data.repository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kz.kamadi.yandextranslate.data.database.language.LanguageDao;
import kz.kamadi.yandextranslate.data.entity.mapper.data.LanguageDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.LanguageEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

public class LanguageLocalDataRepository implements LanguageRepository {

    private final LanguageDao languageDao;
    private LanguageEntityMapper languageEntityMapper;
    private LanguageDataMapper languageDataMapper;

    @Inject
    public LanguageLocalDataRepository(LanguageDao languageDao, LanguageEntityMapper languageEntityMapper, LanguageDataMapper languageDataMapper) {
        this.languageDao = languageDao;
        this.languageEntityMapper = languageEntityMapper;
        this.languageDataMapper = languageDataMapper;
    }

    @Override
    public Observable<List<LanguageEntity>> getLanguages() {
        return Observable.fromCallable(() -> languageEntityMapper.transform(languageDao.getLanguages()));
    }

    @Override
    public Observable<Boolean> create(List<LanguageEntity> entities) {
        return Observable.fromCallable(() -> languageDao.create(languageDataMapper.transform(entities)));
    }

    @Override
    public Observable<LanguageEntity> getLanguageEntity(String code) {
        return Observable.fromCallable(() -> languageEntityMapper.transform(languageDao.getLanguageByCode(code)));
    }
}
