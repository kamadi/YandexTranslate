package kz.kamadi.yandextranslate.domain.entity;

public class HistoryEntity {
    private int id;
    private boolean isFavourite;
    private int status;
    private int language;
    private TranslateEntity translateEntity;
    private DictionaryEntity dictionaryEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public TranslateEntity getTranslateEntity() {
        return translateEntity;
    }

    public void setTranslateEntity(TranslateEntity translateEntity) {
        this.translateEntity = translateEntity;
    }

    public DictionaryEntity getDictionaryEntity() {
        return dictionaryEntity;
    }

    public void setDictionaryEntity(DictionaryEntity dictionaryEntity) {
        this.dictionaryEntity = dictionaryEntity;
    }
}
