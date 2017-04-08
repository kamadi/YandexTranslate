package kz.kamadi.yandextranslate.data.entity;

import java.util.HashMap;

public class LanguageResponse {

    private HashMap<String, String> langs;

    public HashMap<String, String> getLangs() {
        return langs;
    }

    public void setLangs(HashMap<String, String> langs) {
        this.langs = langs;
    }
}
