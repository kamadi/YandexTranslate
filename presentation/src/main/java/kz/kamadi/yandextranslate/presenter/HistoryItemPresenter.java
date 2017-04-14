package kz.kamadi.yandextranslate.presenter;

import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.mapper.data.HistoryDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.HistoryEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.interactor.history.GetHistoriesUseCase;
import kz.kamadi.yandextranslate.domain.interactor.history.UpdateHistoryUseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;
import kz.kamadi.yandextranslate.ui.history.HistoryItemView;

public class HistoryItemPresenter extends BasePresenter {

    HistoryItemView view;
    private GetHistoriesUseCase getHistoriesUseCase;
    private UpdateHistoryUseCase updateHistoryUseCase;
    private HistoryDataMapper historyDataMapper;
    private HistoryEntityMapper historyEntityMapper;

    @Inject
    public HistoryItemPresenter(GetHistoriesUseCase getHistoriesUseCase, UpdateHistoryUseCase updateHistoryUseCase, HistoryDataMapper historyDataMapper, HistoryEntityMapper historyEntityMapper) {
        super(getHistoriesUseCase);
        this.getHistoriesUseCase = getHistoriesUseCase;
        this.updateHistoryUseCase = updateHistoryUseCase;
        this.historyDataMapper = historyDataMapper;
        this.historyEntityMapper = historyEntityMapper;
    }

    @Override
    public void attachView(BaseView view) {
        super.attachView(view);
        this.view = (HistoryItemView) view;
    }

    @Override
    public void detachView() {
        super.detachView();
        this.view = null;
    }

    public void getHistories(int offset, int limit, boolean isFavourite) {
        getHistoriesUseCase.setParams(offset, limit, isFavourite);
        getHistoriesUseCase.execute(new HistoriesGetSubscriber());
    }

    public void update(History history) {
        updateHistoryUseCase.setParam(historyEntityMapper.transform(history));
        updateHistoryUseCase.execute(new HistoryUpdateSubscriber());
    }

    protected class HistoriesGetSubscriber extends BaseSubscriber<List<HistoryEntity>> {

        @Override
        public void onNext(List<HistoryEntity> historyEntities) {
            super.onNext(historyEntities);
            if (view != null) {
                view.showHistories(historyDataMapper.transform(historyEntities));
            }
        }
    }


    protected class HistoryUpdateSubscriber extends BaseSubscriber<Boolean> {
        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Boolean aBoolean) {

        }
    }
}
