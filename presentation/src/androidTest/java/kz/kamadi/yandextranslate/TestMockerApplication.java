package kz.kamadi.yandextranslate;

import kz.kamadi.yandextranslate.dependency.component.ActivityComponent;

public class TestMockerApplication extends App {

    @Override
    public ActivityComponent getActivityComponent() {
        return DaggerTestMockerComponent.builder()
                .applicationComponent(this.applicationComponent)
                .testMockerModule(new TestMockerModule()).build();
    }
}
