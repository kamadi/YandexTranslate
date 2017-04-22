package kz.kamadi.yandextranslate.presenter;

import java.util.List;

import io.reactivex.observers.DisposableObserver;
import kz.kamadi.yandextranslate.domain.interactor.UseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;

public class BasePresenter implements Presenter {

    BaseView baseView;

    private UseCase useCase0, useCase1, useCase2;
    private List<UseCase>useCases;

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


    public BasePresenter(List<UseCase> useCases) {
        this.useCases = useCases;
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
        if (this.useCases != null) {
            for (UseCase useCase:useCases){
                useCase.unsubscribe();
            }
        }
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
