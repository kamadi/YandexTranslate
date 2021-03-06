package kz.kamadi.yandextranslate.domain.entity;


public class LanguageEntity {
    private String name;
    private String code;

    public LanguageEntity(String name, String code) {
        this.name = name;
        this.code = code;
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
