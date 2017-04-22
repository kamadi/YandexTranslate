package kz.kamadi.yandextranslate.domain.entity;

public class HistoryEntity {
    private Integer id;
    private String text;
    private boolean isFavourite;
    private int status;
    private String language;
    private TranslateEntity translateEntity;
    private DictionaryEntity dictionaryEntity;

    public HistoryEntity() {
    }

    public HistoryEntity(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public HistoryEntity(Integer id, String text, boolean isFavourite, int status, String language) {
        this.id = id;
        this.text = text;
        this.isFavourite = isFavourite;
        this.status = status;
        this.language = language;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
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
