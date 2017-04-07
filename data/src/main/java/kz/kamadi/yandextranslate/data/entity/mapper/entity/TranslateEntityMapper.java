package kz.kamadi.yandextranslate.data.entity.mapper.entity;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Translate;
import kz.kamadi.yandextranslate.domain.entity.TranslateEntity;

public class TranslateEntityMapper {

    @Inject
    public TranslateEntityMapper() {
    }

    public TranslateEntity transform(Translate translate) {
        TranslateEntity entity = null;
        if (translate != null) {
            entity = new TranslateEntity();
            entity.setLang(translate.getLang());
            entity.setText(translate.getText());
        }
        return entity;
    }
}
