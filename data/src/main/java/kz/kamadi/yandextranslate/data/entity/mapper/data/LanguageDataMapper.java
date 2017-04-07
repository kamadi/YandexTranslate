package kz.kamadi.yandextranslate.data.entity.mapper.data;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Language;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;

public class LanguageDataMapper {

    @Inject
    public LanguageDataMapper() {
    }

    public Language transform(LanguageEntity entity) {
        Language language = null;
        if (entity != null) {
            language= new Language(entity.getName(),entity.getCode());
        }
        return language;
    }
}
