package kz.kamadi.yandextranslate.presenter;

import kz.kamadi.yandextranslate.ui.base.BaseView;

public interface Presenter {

    void attachView(BaseView view);

    void detachView();

    void destroy();

}
