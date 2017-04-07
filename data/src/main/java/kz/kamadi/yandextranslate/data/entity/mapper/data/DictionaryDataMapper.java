package kz.kamadi.yandextranslate.data.entity.mapper.data;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Dictionary;
import kz.kamadi.yandextranslate.domain.entity.DictionaryEntity;

public class DictionaryDataMapper {

    @Inject
    public DictionaryDataMapper() {
    }

    public Dictionary transform(DictionaryEntity entity) {
        Dictionary dictionary = null;
        if (entity != null) {
            dictionary = new Dictionary();
            dictionary.setDef(transformDef(entity.getDef()));
            dictionary.setContent(entity.getContent());
        }
        return dictionary;
    }

    private List<Dictionary.Def> transformDef(List<DictionaryEntity.Def> entityDefs) {
        if (entityDefs != null) {
            List<Dictionary.Def> defs = new ArrayList<>(entityDefs.size());
            Dictionary.Def def = null;
            for (DictionaryEntity.Def entityDef : entityDefs) {
                def = new Dictionary.Def(new Dictionary.Item(entityDef.getGen(), entityDef.getPos(), entityDef.getText()));
                def.setTs(entityDef.getTs());
                def.setTr(transformTr(entityDef.getTr()));
                defs.add(def);
            }
            return defs;
        }
        return null;
    }


    private List<Dictionary.Tr> transformTr(List<DictionaryEntity.Tr> trEntities) {

        if (trEntities != null) {

            List<Dictionary.Tr> trs = new ArrayList<>();

            Dictionary.Tr tr = null;

            for (DictionaryEntity.Tr trEntity : trEntities) {
                if (trEntity != null) {
                    tr = new Dictionary.Tr(new Dictionary.Item(trEntity.getGen(), trEntity.getPos(), trEntity.getText()));
                    tr.setAsp(trEntity.getAsp());
                    tr.setMean(transformItems(trEntity.getMean()));
                    tr.setSyn(transformItems(trEntity.getSyn()));
                    tr.setEx(transformDef(trEntity.getEx()));
                    trs.add(tr);
                } else {
                    trs.add(null);
                }
            }
            return trs;
        }
        return null;
    }

    private List<Dictionary.Item> transformItems(List<DictionaryEntity.Item> itemEntities) {
        if (itemEntities != null) {
            List<Dictionary.Item> items = new ArrayList<>(itemEntities.size());
            for (DictionaryEntity.Item itemEntity : itemEntities) {
                if (itemEntity != null) {
                    Dictionary.Item item = new Dictionary.Item();
                    item.setText(itemEntity.getText());
                    item.setPos(itemEntity.getPos());
                    item.setGen(itemEntity.getGen());
                    items.add(item);
                } else {
                    items.add(null);
                }
            }
            return items;
        }
        return null;
    }

}
