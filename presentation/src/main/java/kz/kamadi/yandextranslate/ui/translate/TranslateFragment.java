package kz.kamadi.yandextranslate.ui.translate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.BindView;
import kz.kamadi.yandextranslate.R;
import kz.kamadi.yandextranslate.ui.base.BaseFragment;
import kz.kamadi.yandextranslate.ui.widgets.DictionaryView;

public class TranslateFragment extends BaseFragment {

    @BindView(R.id.dictionary_view)
    DictionaryView dictionaryView;

    @Override
    protected int layoutId() {
        return R.layout.fragment_translate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
