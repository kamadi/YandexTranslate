package kz.kamadi.yandextranslate;

import dagger.Component;
import kz.kamadi.yandextranslate.dependency.ActivityScope;
import kz.kamadi.yandextranslate.dependency.component.ActivityComponent;
import kz.kamadi.yandextranslate.dependency.component.ApplicationComponent;

@ActivityScope
@Component(modules = TestMockerModule.class, dependencies = ApplicationComponent.class)
public interface TestMockerComponent extends ActivityComponent{

}
