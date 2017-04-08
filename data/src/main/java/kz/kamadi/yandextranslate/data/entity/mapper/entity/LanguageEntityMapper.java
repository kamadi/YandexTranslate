package kz.kamadi.yandextranslate.data.entity.mapper.entity;

import java.util.ArrayList;
import java.util.List;

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
            entity = new LanguageEntity(language.getName(), language.getCode());
        }
        return entity;
    }

    public List<LanguageEntity> transform(List<Language> languages) {
        if (languages != null) {
            List<LanguageEntity> entities = new ArrayList<>(languages.size());
            for (Language language : languages) {
                entities.add(transform(language));
            }
            return entities;
        }
        return null;
    }
}
