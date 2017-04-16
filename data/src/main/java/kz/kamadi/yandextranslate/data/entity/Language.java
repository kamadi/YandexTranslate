package kz.kamadi.yandextranslate.data.entity;


public class Language {

    private String code;
    private String name;

    public Language() {
    }

    public Language(String name, String code) {
        this.code = code;
        this.name = name;
    }

    public Language(Language language) {
        this.name = language.getName();
        this.code = language.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
