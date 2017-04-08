package kz.kamadi.yandextranslate.data.entity.mapper.data;

import java.util.ArrayList;
import java.util.List;

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

    public List<Language> transform(List<LanguageEntity> entities) {
        if (entities != null) {
            List<Language> languages = new ArrayList<>(entities.size());
            for (LanguageEntity entity : entities) {
                languages.add(transform(entity));
            }
        }
        return null;
    }
}
