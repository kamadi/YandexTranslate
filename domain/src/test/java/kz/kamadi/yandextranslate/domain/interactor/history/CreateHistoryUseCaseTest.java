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

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class CreateHistoryUseCaseTest {
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
    public void testCreateHistory() {

        CreateHistoryUseCase useCase = new CreateHistoryUseCase(mockThreadExecutor, mockPostExecutionThread, repository);

        TestObserver<HistoryEntity> testObserver = new TestObserver<>();

        HistoryEntity fakeHistoryEntity = new HistoryEntity(1, "text", true, 1, "en-ru");

        given(repository.create(fakeHistoryEntity)).willReturn(Observable.just(fakeHistoryEntity));

        useCase.setParam(fakeHistoryEntity);

        useCase.buildUseCaseObservable().subscribe(testObserver);

        verify(repository).create(fakeHistoryEntity);

        HistoryEntity historyEntity = (HistoryEntity) testObserver.getEvents().get(0).get(0);

        assertEquals(historyEntity.getId(), fakeHistoryEntity.getId());

        assertEquals(historyEntity.getLanguage(), fakeHistoryEntity.getLanguage());

        assertEquals(historyEntity.getText(), fakeHistoryEntity.getText());

        assertEquals(historyEntity.getStatus(), fakeHistoryEntity.getStatus());

        assertEquals(historyEntity.isFavourite(), fakeHistoryEntity.isFavourite());

        verifyNoMoreInteractions(repository);

        verifyZeroInteractions(mockThreadExecutor);

        verifyZeroInteractions(mockPostExecutionThread);

    }


}
