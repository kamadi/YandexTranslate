package kz.kamadi.yandextranslate;

import android.app.Application;

import kz.kamadi.yandextranslate.dependency.component.ActivityComponent;
import kz.kamadi.yandextranslate.dependency.component.ApplicationComponent;
import kz.kamadi.yandextranslate.dependency.component.DaggerActivityComponent;
import kz.kamadi.yandextranslate.dependency.component.DaggerApplicationComponent;
import kz.kamadi.yandextranslate.dependency.module.ApplicationModule;

public class App extends Application {

    protected ApplicationComponent applicationComponent;
    protected ActivityComponent activityComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    protected void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        this.activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(this.applicationComponent).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
