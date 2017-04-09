package kz.kamadi.yandextranslate.data.entity;

import java.util.Collections;
import java.util.List;

public class Translate {

    private List<String> text;
    private String lang;

    public Translate() {
    }

    public Translate(String text) {
        this.text = Collections.singletonList(text);
    }

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

    public String getTranslation() {
        if (text != null && text.size() > 0) {
            return text.get(0);
        }
        return null;
    }
}
