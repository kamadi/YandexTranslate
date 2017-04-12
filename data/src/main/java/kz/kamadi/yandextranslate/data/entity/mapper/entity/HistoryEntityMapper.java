package kz.kamadi.yandextranslate.data.entity.mapper.entity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;

public class HistoryEntityMapper {

    private DictionaryEntityMapper dictionaryEntityMapper;
    private TranslateEntityMapper translateEntityMapper;

    @Inject
    public HistoryEntityMapper(DictionaryEntityMapper dictionaryEntityMapper, TranslateEntityMapper translateEntityMapper) {
        this.dictionaryEntityMapper = dictionaryEntityMapper;
        this.translateEntityMapper = translateEntityMapper;
    }

    public HistoryEntity transform(History history) {
        HistoryEntity entity = null;
        if (history != null) {
            entity = new HistoryEntity();
            entity.setId(history.getId());
            entity.setText(history.getText());
            entity.setLanguage(history.getLanguage());
            entity.setFavourite(history.isFavourite());
            entity.setStatus(history.getStatus());
            entity.setDictionaryEntity(dictionaryEntityMapper.transform(history.getDictionary()));
            entity.setTranslateEntity(translateEntityMapper.transform(history.getTranslate()));
        }
        return entity;
    }

    public List<HistoryEntity> transform(List<History> histories) {
        if (histories != null) {
            List<HistoryEntity> entities = new ArrayList<>(histories.size());
            for (History history : histories) {
                entities.add(transform(history));
            }
            return entities;
        }
        return null;
    }
}
