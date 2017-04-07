package kz.kamadi.yandextranslate.data.entity;

import java.util.List;

public class Translate {

    private List<String> text;
    private String lang;

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
