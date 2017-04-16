package kz.kamadi.yandextranslate.presenter;

import io.reactivex.observers.DisposableObserver;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;

public class BasePresenter implements Presenter {

    BaseView baseView;

    private UseCase useCase0, useCase1, useCase2,useCase3;

    public BasePresenter(UseCase useCase0) {
        this.useCase0 = useCase0;
    }

    public BasePresenter(UseCase useCase0, UseCase useCase1) {
        this.useCase0 = useCase0;
        this.useCase1 = useCase1;
    }

    public BasePresenter(UseCase useCase0, UseCase useCase1, UseCase useCase2) {
        this.useCase0 = useCase0;
        this.useCase1 = useCase1;
        this.useCase2 = useCase2;
    }

    public BasePresenter(UseCase useCase0, UseCase useCase1, UseCase useCase2, UseCase useCase3) {
        this.useCase0 = useCase0;
        this.useCase1 = useCase1;
        this.useCase2 = useCase2;
        this.useCase3 = useCase3;
    }

    @Override
    public void attachView(BaseView view) {
        this.baseView = view;
    }

    @Override
    public void detachView() {
        baseView = null;
    }

    @Override
    public void destroy() {
        if (this.useCase0 != null) this.useCase0.unsubscribe();
        if (this.useCase1 != null) this.useCase1.unsubscribe();
        if (this.useCase2 != null) this.useCase2.unsubscribe();
        if (this.useCase3 != null) this.useCase3.unsubscribe();
    }

    public void showLoader() {
        if (this.baseView != null) {
            this.baseView.showLoading();
        }
    }

    public void hideLoader() {
        if (this.baseView != null) {
            this.baseView.hideLoading();
        }
    }

    public void handleError(Throwable error) {
        if (this.baseView != null) {
            this.baseView.handleError(error);
        }
    }

    protected class BaseSubscriber<T> extends DisposableObserver<T> {

        @Override
        public void onComplete() {
            BasePresenter.this.hideLoader();
        }

        @Override
        public void onError(Throwable e) {
            BasePresenter.this.hideLoader();
            BasePresenter.this.handleError(e);
            e.printStackTrace();
        }

        @Override
        public void onNext(T t) {
            BasePresenter.this.hideLoader();
            //BasePresenter.this.showMessage(t.toString());
        }
    }
}
