package kz.kamadi.yandextranslate.presenter;

import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.mapper.data.HistoryDataMapper;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.interactor.history.GetHistoriesUseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;
import kz.kamadi.yandextranslate.ui.history.HistoryItemView;

public class HistoryItemPresenter extends BasePresenter {

    HistoryItemView view;
    private GetHistoriesUseCase getHistoriesUseCase;
    private HistoryDataMapper historyDataMapper;

    @Inject
    public HistoryItemPresenter(GetHistoriesUseCase getHistoriesUseCase, HistoryDataMapper historyDataMapper) {
        super(getHistoriesUseCase);
        this.getHistoriesUseCase = getHistoriesUseCase;
        this.historyDataMapper = historyDataMapper;
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
        getHistoriesUseCase.setParams(offset, limit,isFavourite);
        getHistoriesUseCase.execute(new HistoriesGetSubscriber());
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
}
