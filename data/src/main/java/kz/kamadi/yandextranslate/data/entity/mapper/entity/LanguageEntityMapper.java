package kz.kamadi.yandextranslate.data.entity.mapper.entity;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Language;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;

public class LanguageEntityMapper {

    @Inject
    public LanguageEntityMapper() {
    }

    public LanguageEntity transform(Language language) {
        LanguageEntity entity = null;
        if (language != null) {
            entity= new LanguageEntity(language.getName(),language.getCode());
        }
        return entity;
    }
}
