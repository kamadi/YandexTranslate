package kz.kamadi.yandextranslate.data.entity.mapper.data;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Translate;
import kz.kamadi.yandextranslate.domain.entity.TranslateEntity;

public class TranslateDataMapper {

    @Inject
    public TranslateDataMapper() {
    }

    public Translate transform(TranslateEntity entity) {
        Translate translate = null;
        if (entity != null) {
            translate = new Translate();
            translate.setLang(entity.getLang());
            translate.setText(entity.getText());
        }
        return translate;
    }
}
