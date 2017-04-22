package kz.kamadi.yandextranslate.domain.entity;

import java.util.List;

public class TranslateEntity {
    private List<String> text;
    private String lang;

    public TranslateEntity() {
    }

    public TranslateEntity(List<String> text, String lang) {
        this.text = text;
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
}
