package kz.kamadi.yandextranslate.data.entity.mapper.entity;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Translation;
import kz.kamadi.yandextranslate.domain.entity.TranslationEntity;

public class TranslationEntityMapper {

    private HistoryEntityMapper historyEntityMapper;
    private DictionaryEntityMapper dictionaryEntityMapper;
    private TranslateEntityMapper translateEntityMapper;

    @Inject
    public TranslationEntityMapper(HistoryEntityMapper historyEntityMapper, DictionaryEntityMapper dictionaryEntityMapper, TranslateEntityMapper translateEntityMapper) {
        this.historyEntityMapper = historyEntityMapper;
        this.dictionaryEntityMapper = dictionaryEntityMapper;
        this.translateEntityMapper = translateEntityMapper;
    }

    public TranslationEntity transform(Translation translation) {
        TranslationEntity entity = null;
        if (translation != null) {
            entity = new TranslationEntity();
            entity.setText(translation.getText());
            entity.setLanguage(translation.getLanguage());
            entity.setHistoryEntity(historyEntityMapper.transform(translation.getHistory()));
            entity.setTranslateEntity(translateEntityMapper.transform(translation.getTranslate()));
            entity.setDictionaryEntity(dictionaryEntityMapper.transform(translation.getDictionary()));
        }
        return entity;
    }
}
