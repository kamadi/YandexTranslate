package kz.kamadi.yandextranslate.data.entity;

public class Translation {
    private String text;
    private String language;
    private Dictionary dictionary;
    private Translate translate;
    private History history;

    public Translation() {
    }

    public Translation(History history) {
        this.history = history;
        this.translate = history.getTranslate();
        this.dictionary = history.getDictionary();
        this.text = history.getText();
        this.language = history.getLanguage();
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

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Translate getTranslate() {
        return translate;
    }

    public void setTranslate(Translate translate) {
        this.translate = translate;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }
}
