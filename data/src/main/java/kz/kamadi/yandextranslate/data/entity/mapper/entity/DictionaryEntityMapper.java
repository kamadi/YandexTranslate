package kz.kamadi.yandextranslate.data.entity.mapper.entity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Dictionary;
import kz.kamadi.yandextranslate.domain.entity.DictionaryEntity;

public class DictionaryEntityMapper {

    @Inject
    public DictionaryEntityMapper() {
    }

    public DictionaryEntity transform(Dictionary dictionary) {
        DictionaryEntity entity = null;
        if (dictionary != null) {
            entity = new DictionaryEntity();
            entity.setDef(transformDef(dictionary.getDef()));
            entity.setContent(dictionary.getContent());
        }
        return entity;
    }

    private List<DictionaryEntity.Def> transformDef(List<Dictionary.Def> defs) {
        if (defs != null) {
            List<DictionaryEntity.Def> entityDefs = new ArrayList<>(defs.size());
            DictionaryEntity.Def entityDef = null;
            for (Dictionary.Def def : defs) {
                entityDef = new DictionaryEntity.Def(new DictionaryEntity.Item(def.getGen(), def.getPos(), def.getText()));
                entityDef.setTs(def.getTs());
                entityDef.setTr(transformTr(def.getTr()));
                entityDefs.add(entityDef);
            }
            return entityDefs;
        }
        return null;
    }


    private List<DictionaryEntity.Tr> transformTr(List<Dictionary.Tr> trs) {

        if (trs != null) {

            List<DictionaryEntity.Tr> trEntities = new ArrayList<>();

            DictionaryEntity.Tr trEntity = null;

            for (Dictionary.Tr tr : trs) {
                if (tr != null) {
                    trEntity = new DictionaryEntity.Tr(new DictionaryEntity.Item(tr.getGen(), tr.getPos(), tr.getText()));
                    trEntity.setAsp(tr.getAsp());
                    trEntity.setMean(transformItems(tr.getMean()));
                    trEntity.setSyn(transformItems(tr.getSyn()));
                    trEntity.setEx(transformDef(tr.getEx()));
                    trEntities.add(trEntity);
                } else {
                    trEntities.add(null);
                }
            }
            return trEntities;
        }
        return null;
    }

    private List<DictionaryEntity.Item> transformItems(List<Dictionary.Item> items) {
        if (items != null) {
            List<DictionaryEntity.Item> entityItems = new ArrayList<>(items.size());
            for (Dictionary.Item item : items) {
                if (item != null) {
                    DictionaryEntity.Item entityItem = new DictionaryEntity.Item();
                    entityItem.setText(item.getText());
                    entityItem.setPos(item.getPos());
                    entityItem.setGen(item.getGen());
                    entityItems.add(entityItem);
                } else {
                    entityItems.add(null);
                }
            }
            return entityItems;
        }
        return null;
    }

}
