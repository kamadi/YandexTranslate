package kz.kamadi.yandextranslate.data.entity.mapper.data;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;

public class HistoryDataMapper {

    private DictionaryDataMapper dictionaryDataMapper;
    private TranslateDataMapper translateDataMapper;

    @Inject
    public HistoryDataMapper(DictionaryDataMapper dictionaryDataMapper, TranslateDataMapper translateDataMapper) {
        this.dictionaryDataMapper = dictionaryDataMapper;
        this.translateDataMapper = translateDataMapper;
    }

    public History transform(HistoryEntity history) {
        History entity = null;
        if (history != null) {
            entity = new History();
            entity.setId(history.getId());
            entity.setLanguage(history.getLanguage());
            entity.setFavourite(history.isFavourite());
            entity.setStatus(history.getStatus());
            entity.setDictionary(dictionaryDataMapper.transform(history.getDictionaryEntity()));
            entity.setTranslate(translateDataMapper.transform(history.getTranslateEntity()));
        }
        return entity;
    }

    public List<History> transform(List<HistoryEntity> entities) {
        if (entities != null) {
            List<History> histories = new ArrayList<>(entities.size());
            for (HistoryEntity entity : entities) {
                histories.add(transform(entity));
            }
            return histories;
        }
        return null;
    }
}
