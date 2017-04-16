package kz.kamadi.yandextranslate.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import kz.kamadi.yandextranslate.App;
import kz.kamadi.yandextranslate.dependency.component.ActivityComponent;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent activityComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App app = (App) getApplication();
        activityComponent = app.getActivityComponent();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
