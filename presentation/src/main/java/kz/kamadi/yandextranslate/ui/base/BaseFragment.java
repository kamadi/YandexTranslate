package kz.kamadi.yandextranslate.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import kz.kamadi.yandextranslate.App;
import kz.kamadi.yandextranslate.dependency.component.ActivityComponent;

public abstract class BaseFragment extends Fragment{

    private Unbinder unbinder;
    private ActivityComponent activityComponent;
    public Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent = ((App)getActivity().getApplication()).getActivityComponent();
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    protected abstract int layoutId();
}
