package kz.kamadi.yandextranslate.presenter;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.dependency.ActivityScope;
import kz.kamadi.yandextranslate.domain.interactor.history.DeleteAllHistoryUseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;
import kz.kamadi.yandextranslate.ui.history.HistoryView;

@ActivityScope
public class HistoryPresenter extends BasePresenter {

    HistoryView view;
    private DeleteAllHistoryUseCase deleteAllHistoryUseCase;

    @Inject
    public HistoryPresenter(DeleteAllHistoryUseCase deleteAllHistoryUseCase) {
        super(deleteAllHistoryUseCase);
        this.deleteAllHistoryUseCase = deleteAllHistoryUseCase;
    }

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.view = (HistoryView) view;
    }

    @Override
    public void detachView() {
        super.detachView();
        view = null;
    }

    public void deleteAll(boolean isFavourite) {
        deleteAllHistoryUseCase.setParam(isFavourite);
        deleteAllHistoryUseCase.execute(new HistoryDeleteSubscriber());
    }

    protected class HistoryDeleteSubscriber extends BaseSubscriber<Integer> {

        @Override
        public void onNext(Integer integer) {
            super.onNext(integer);
            if (view != null) {
                if (integer>0){
                    view.onHistoriesDeleted();
                }
            }
        }
    }
}
