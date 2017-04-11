package kz.kamadi.yandextranslate.ui.base;

public interface BaseView {

    void showLoading();

    void hideLoading();

    void showMessage(String message);

    void handleError(Throwable error);

}
