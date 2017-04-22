package kz.kamadi.yandextranslate.domain.interactor.history;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DeleteAllHistoryUseCaseTest {
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private HistoryRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteAll() {

        DeleteAllHistoryUseCase useCase = new DeleteAllHistoryUseCase(mockThreadExecutor, mockPostExecutionThread, repository);

        TestObserver<Integer> testObserver = new TestObserver<>();

        Boolean fakeFavourite = true;

        Integer fakeResult = 1;

        given(repository.deleteAll(fakeFavourite)).willReturn(Observable.just(fakeResult));

        useCase.setParam(fakeFavourite);

        useCase.buildUseCaseObservable().subscribe(testObserver);

        verify(repository).deleteAll(fakeFavourite);

        Integer result = (Integer) testObserver.getEvents().get(0).get(0);

        assertEquals(result, fakeResult);

        verifyNoMoreInteractions(repository);

        verifyZeroInteractions(mockThreadExecutor);

        verifyZeroInteractions(mockPostExecutionThread);

    }
}
