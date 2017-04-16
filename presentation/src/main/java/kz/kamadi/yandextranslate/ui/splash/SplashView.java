package kz.kamadi.yandextranslate.ui.splash;

import java.util.List;

import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.ui.base.BaseView;

public interface SplashView extends BaseView {
    void onLanguagesDownloaded(List<LanguageEntity> entities);

    void onLanguagesCreated();
}
