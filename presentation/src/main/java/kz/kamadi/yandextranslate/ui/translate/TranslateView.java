package kz.kamadi.yandextranslate.ui.translate;

import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.Language;
import kz.kamadi.yandextranslate.data.entity.Translation;
import kz.kamadi.yandextranslate.ui.base.BaseView;

public interface TranslateView extends BaseView {

    void showTranslation(Translation translation);

    void onHistoryCreated(History history);

    void onLanguagesGet(Language source,Language target);
}
