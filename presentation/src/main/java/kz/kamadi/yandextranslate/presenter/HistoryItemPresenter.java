package kz.kamadi.yandextranslate.presenter;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.mapper.data.HistoryDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.HistoryEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.interactor.history.DeleteHistoryUseCase;
import kz.kamadi.yandextranslate.domain.interactor.history.GetHistoriesUseCase;
import kz.kamadi.yandextranslate.domain.interactor.history.SearchHistoriesUseCase;
import kz.kamadi.yandextranslate.domain.interactor.history.UpdateHistoryUseCase;
import kz.kamadi.yandextranslate.ui.base.BaseView;
import kz.kamadi.yandextranslate.ui.history.HistoryItemView;

public class HistoryItemPresenter extends BasePresenter {

    HistoryItemView view;
    private GetHistoriesUseCase getHistoriesUseCase;
    private UpdateHistoryUseCase updateHistoryUseCase;
    private SearchHistoriesUseCase searchHistoriesUseCase;
    private DeleteHistoryUseCase deleteHistoryUseCase;
    private HistoryDataMapper historyDataMapper;
    private HistoryEntityMapper historyEntityMapper;

    @Inject
    public HistoryItemPresenter(GetHistoriesUseCase getHistoriesUseCase,
                                UpdateHistoryUseCase updateHistoryUseCase,
                                SearchHistoriesUseCase searchHistoriesUseCase,
                                DeleteHistoryUseCase deleteHistoryUseCase, HistoryDataMapper historyDataMapper, HistoryEntityMapper historyEntityMapper) {
        super(Arrays.asList(getHistoriesUseCase, updateHistoryUseCase, getHistoriesUseCase, deleteHistoryUseCase));
        this.getHistoriesUseCase = getHistoriesUseCase;
        this.updateHistoryUseCase = updateHistoryUseCase;
        this.searchHistoriesUseCase = searchHistoriesUseCase;
        this.deleteHistoryUseCase = deleteHistoryUseCase;
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

    public void search(String text, boolean isFavourite) {
        searchHistoriesUseCase.setParam(text, isFavourite);
        searchHistoriesUseCase.execute(new HistoriesGetSubscriber());
    }

    public void delete(History history, int position, boolean isFavourite) {
        if (isFavourite) {
            history.setFavourite(false);
            updateHistoryUseCase.setParam(historyEntityMapper.transform(history));
            updateHistoryUseCase.execute(new HistoryDeleteSubscriber(position));
        } else {
            deleteHistoryUseCase.setParam(historyEntityMapper.transform(history));
            deleteHistoryUseCase.execute(new HistoryDeleteSubscriber(position));
        }
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

    protected class HistoryDeleteSubscriber extends BaseSubscriber<Boolean> {

        private int position;

        public HistoryDeleteSubscriber(int position) {
            this.position = position;
        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Boolean aBoolean) {
            if (view != null) {
                if (aBoolean)
                    view.onHistoryDeleted(position);
            }
        }
    }
}
