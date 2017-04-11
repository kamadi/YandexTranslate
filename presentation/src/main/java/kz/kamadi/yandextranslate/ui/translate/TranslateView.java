package kz.kamadi.yandextranslate.ui.translate;

import kz.kamadi.yandextranslate.data.entity.Translation;
import kz.kamadi.yandextranslate.ui.base.BaseView;

public interface TranslateView extends BaseView {

    void showTranslation(Translation translation);

}
