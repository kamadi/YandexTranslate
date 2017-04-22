package kz.kamadi.yandextranslate.domain.interactor.history;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;

import static junit.framework.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class DeleteHistoryUseCaseTest {
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
    public void testDeleteHistory() {

        DeleteHistoryUseCase useCase = new DeleteHistoryUseCase(mockThreadExecutor, mockPostExecutionThread, repository);

        TestObserver<Boolean> testObserver = new TestObserver<>();

        HistoryEntity fakeHistoryEntity = new HistoryEntity(1, "text", true, 1, "en-ru");

        given(repository.delete(fakeHistoryEntity)).willReturn(Observable.just(true));

        useCase.setParam(fakeHistoryEntity);

        useCase.buildUseCaseObservable().subscribe(testObserver);

        verify(repository).delete(fakeHistoryEntity);

        Boolean result = (Boolean) testObserver.getEvents().get(0).get(0);

        assertTrue(result);

        verifyNoMoreInteractions(repository);

        verifyZeroInteractions(mockThreadExecutor);

        verifyZeroInteractions(mockPostExecutionThread);

    }

}
