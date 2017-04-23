package kz.kamadi.yandextranslate.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import kz.kamadi.yandextranslate.domain.interactor.history.DeleteAllHistoryUseCase;
import kz.kamadi.yandextranslate.ui.history.HistoryView;

import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class HistoryPresenterTest {
    @Mock
    HistoryView view;
    @Mock
    DeleteAllHistoryUseCase deleteAllHistoryUseCase;

    private HistoryPresenter historyPresenter;
    private HistoryPresenter.HistoryDeleteSubscriber deleteSubscriber;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        historyPresenter = new HistoryPresenter(deleteAllHistoryUseCase);
        historyPresenter.attachView(view);
        deleteSubscriber = historyPresenter.new HistoryDeleteSubscriber();
    }

    @Test
    public void testDestroy() {

        this.historyPresenter.destroy();

        verify(this.deleteAllHistoryUseCase).unsubscribe();

    }

    @Test
    public void testDetachView() {

        this.historyPresenter.detachView();

        assertNull(this.historyPresenter.view);

    }

    @Test
    public void testDeleteAll(){

        this.historyPresenter.deleteAll(true);

        verify(this.deleteAllHistoryUseCase).execute(any(HistoryPresenter.HistoryDeleteSubscriber.class));

    }

    @Test
    public void testSubscriberOnCompleted() {

        this.deleteSubscriber.onComplete();

        verify(this.view).hideLoading();
    }

    @Test
    public void testSubscriberOnError() {

        this.deleteSubscriber.onError(new Exception());

        verify(this.view).hideLoading();
        verify(this.view).handleError(any(Throwable.class));
    }

    @Test
    public void testSubscriberOnNext() {

        this.deleteSubscriber.onNext(1);

        verify(this.view).hideLoading();
    }
}
