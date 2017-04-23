package kz.kamadi.yandextranslate.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.mapper.data.HistoryDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.HistoryEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.interactor.history.DeleteHistoryUseCase;
import kz.kamadi.yandextranslate.domain.interactor.history.GetHistoriesUseCase;
import kz.kamadi.yandextranslate.domain.interactor.history.SearchHistoriesUseCase;
import kz.kamadi.yandextranslate.domain.interactor.history.UpdateHistoryUseCase;
import kz.kamadi.yandextranslate.ui.history.HistoryItemView;

import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class HistoryItemPresenterTest {
    @Mock HistoryItemView view;
    @Mock GetHistoriesUseCase getHistoriesUseCase;
    @Mock UpdateHistoryUseCase updateHistoryUseCase;
    @Mock SearchHistoriesUseCase searchHistoriesUseCase;
    @Mock DeleteHistoryUseCase deleteHistoryUseCase;
    @Mock HistoryDataMapper historyDataMapper;
    @Mock HistoryEntityMapper historyEntityMapper;

    private HistoryItemPresenter presenter;
    private HistoryItemPresenter.HistoriesGetSubscriber historiesGetSubscriber;
    private HistoryItemPresenter.HistoryDeleteSubscriber historyDeleteSubscriber;
    private HistoryItemPresenter.HistoryUpdateSubscriber historyUpdateSubscriber;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new HistoryItemPresenter(getHistoriesUseCase,updateHistoryUseCase, searchHistoriesUseCase,deleteHistoryUseCase,historyDataMapper,historyEntityMapper);
        presenter.attachView(view);
        historiesGetSubscriber = presenter.new HistoriesGetSubscriber();
        historyDeleteSubscriber = presenter.new HistoryDeleteSubscriber(1);
        historyUpdateSubscriber = presenter.new HistoryUpdateSubscriber();
    }

    @Test
    public void testGetHistories(){

        this.presenter.getHistories(0,0,false);

        verify(this.getHistoriesUseCase).execute(any(HistoryItemPresenter.HistoriesGetSubscriber.class));
    }

    @Test
    public void testUpdate(){

        this.presenter.update(new History());

        verify(this.updateHistoryUseCase).execute(any(HistoryItemPresenter.HistoryUpdateSubscriber.class));

    }

    @Test
    public void testSearch(){

        this.presenter.search("text",false);

        verify(this.searchHistoriesUseCase).execute(any(HistoryItemPresenter.HistoriesGetSubscriber.class));

    }

    @Test
    public void testDelete(){

        this.presenter.delete(new History(),1,false);

        verify(this.deleteHistoryUseCase).execute(any(HistoryItemPresenter.HistoryDeleteSubscriber.class));

        this.presenter.delete(new History(),1,true);

        verify(this.updateHistoryUseCase).execute(any(HistoryItemPresenter.HistoryDeleteSubscriber.class));

    }

    @Test
    public void testDetachView() {

        this.presenter.detachView();

        assertNull(this.presenter.view);

    }

    @Test
    public void testHistoriesGetSubscriberOnCompleted() {

        this.historiesGetSubscriber.onComplete();

        verify(this.view).hideLoading();
    }

    @Test
    public void testHistoriesGetSubscriberOnError() {

        this.historiesGetSubscriber.onError(new Exception());

        verify(this.view).hideLoading();
        verify(this.view).handleError(any(Throwable.class));
    }

    @Test
    public void testHistoriesGetSubscriberOnNext() {

        this.historiesGetSubscriber.onNext(Arrays.asList(new HistoryEntity()));

        verify(this.view).hideLoading();
    }

    @Test
    public void testHistoryUpdateSubscriberOnCompleted() {

        this.historyUpdateSubscriber.onComplete();

        verify(this.view,never()).hideLoading();
    }

    @Test
    public void testHistoryUpdateSubscriberOnError() {

        this.historyUpdateSubscriber.onError(new Exception());

        verify(this.view,never()).hideLoading();
        verify(this.view,never()).handleError(any(Throwable.class));
    }

    @Test
    public void testHistoryUpdateSubscriberOnNext() {

        this.historyUpdateSubscriber.onNext(true);

        verify(this.view,never()).hideLoading();
    }

    @Test
    public void testHistoryDeleteSubscriberOnCompleted() {

        this.historyDeleteSubscriber.onComplete();

        verify(this.view,never()).hideLoading();
    }

    @Test
    public void testHistoryDeleteSubscriberOnError() {

        this.historyDeleteSubscriber.onError(new Exception());

        verify(this.view,never()).hideLoading();
        verify(this.view,never()).handleError(any(Throwable.class));
    }

    @Test
    public void testHistoryDeleteSubscriberOnNext() {

        this.historyDeleteSubscriber.onNext(true);

        verify(this.view,never()).hideLoading();
    }
}
