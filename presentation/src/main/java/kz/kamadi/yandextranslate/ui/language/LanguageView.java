package kz.kamadi.yandextranslate.ui.language;

import java.util.List;

import kz.kamadi.yandextranslate.data.entity.Language;
import kz.kamadi.yandextranslate.ui.base.BaseView;

public interface LanguageView extends BaseView {
    void showLanguages(List<Language> languages);
}
