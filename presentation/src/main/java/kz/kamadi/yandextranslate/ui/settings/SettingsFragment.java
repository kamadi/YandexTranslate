package kz.kamadi.yandextranslate.ui.settings;


import android.content.Intent;
import android.view.View;

import butterknife.OnClick;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;

public class SettingsFragment extends BaseFragment {
    @Override
    protected int layoutId() {
        return R.layout.fragment_settings;
    }

    @OnClick(R.id.about_layout)
    void onAboutClick(View view) {
        startActivity(new Intent(context, AboutActivity.class));
    }
}
