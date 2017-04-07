package kz.kamadi.yandextranslate.data.database.language;

import java.util.List;

import kz.kamadi.yandextranslate.data.entity.Language;

public interface LanguageDao {

    boolean create(List<Language> languages);

    List<Language> getLanguages();
}
