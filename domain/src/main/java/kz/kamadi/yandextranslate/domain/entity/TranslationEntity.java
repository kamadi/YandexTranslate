package kz.kamadi.yandextranslate.domain.entity;

public class TranslationEntity {
    private String text;
    private String language;
    private DictionaryEntity dictionaryEntity;
    private TranslateEntity translateEntity;
    private HistoryEntity historyEntity;


    public TranslationEntity() {
    }

    public TranslationEntity(TranslateEntity translateEntity, DictionaryEntity dictionaryEntity) {
        this.translateEntity = translateEntity;
        this.dictionaryEntity = dictionaryEntity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public DictionaryEntity getDictionaryEntity() {
        return dictionaryEntity;
    }

    public void setDictionaryEntity(DictionaryEntity dictionaryEntity) {
        this.dictionaryEntity = dictionaryEntity;
    }

    public TranslateEntity getTranslateEntity() {
        return translateEntity;
    }

    public void setTranslateEntity(TranslateEntity translateEntity) {
        this.translateEntity = translateEntity;
    }

    public HistoryEntity getHistoryEntity() {
        return historyEntity;
    }

    public void setHistoryEntity(HistoryEntity historyEntity) {
        this.historyEntity = historyEntity;
    }
}
