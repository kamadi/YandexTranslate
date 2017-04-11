package kz.kamadi.yandextranslate.dependency.component;

import dagger.Component;
import kz.kamadi.yandextranslate.dependency.ActivityScope;
import kz.kamadi.yandextranslate.ui.main.MainActivity;
import kz.kamadi.yandextranslate.ui.translate.TranslateFragment;

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

    void inject(TranslateFragment translateFragment);

}
