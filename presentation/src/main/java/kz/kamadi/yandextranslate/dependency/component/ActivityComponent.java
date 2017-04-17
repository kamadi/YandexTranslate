package kz.kamadi.yandextranslate.dependency.component;

import dagger.Component;
import kz.kamadi.yandextranslate.dependency.ActivityScope;
import kz.kamadi.yandextranslate.ui.history.HistoryFragment;
import kz.kamadi.yandextranslate.ui.history.HistoryItemFragment;
import kz.kamadi.yandextranslate.ui.language.LanguageActivity;
import kz.kamadi.yandextranslate.ui.main.MainActivity;
import kz.kamadi.yandextranslate.ui.splash.SplashActivity;
import kz.kamadi.yandextranslate.ui.translate.TranslateFragment;

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface ActivityComponent {

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(LanguageActivity languageActivity);

    void inject(TranslateFragment translateFragment);

    void inject(HistoryFragment historyFragment);

    void inject(HistoryItemFragment historyItemFragment);
}
