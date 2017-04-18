package kz.kamadi.yandextranslate.data.entity.mapper.data;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Translation;
import kz.kamadi.yandextranslate.domain.entity.TranslationEntity;

public class TranslationDataMapper {

    private HistoryDataMapper historyDataMapper;
    private DictionaryDataMapper dictionaryDataMapper;
    private TranslateDataMapper translateDataMapper;

    @Inject
    public TranslationDataMapper(HistoryDataMapper historyDataMapper, DictionaryDataMapper dictionaryDataMapper, TranslateDataMapper translateDataMapper) {
        this.historyDataMapper = historyDataMapper;
        this.dictionaryDataMapper = dictionaryDataMapper;
        this.translateDataMapper = translateDataMapper;
    }

    public Translation transform(TranslationEntity entity) {
        Translation translation = null;
        if (entity != null) {
            translation = new Translation();
            translation.setText(entity.getText());
            translation.setLanguage(entity.getLanguage());
            translation.setHistory(historyDataMapper.transform(entity.getHistoryEntity()));
            translation.setTranslate(translateDataMapper.transform(entity.getTranslateEntity()));
            translation.setDictionary(dictionaryDataMapper.transform(entity.getDictionaryEntity()));
        }
        return translation;
    }
}
