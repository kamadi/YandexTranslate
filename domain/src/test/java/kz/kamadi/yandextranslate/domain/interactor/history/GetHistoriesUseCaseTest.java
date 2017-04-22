package kz.kamadi.yandextranslate.domain.interactor.history;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.repository.HistoryRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetHistoriesUseCaseTest {
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
    public void testGetHistories() {

        GetHistoriesUseCase useCase = new GetHistoriesUseCase(mockThreadExecutor, mockPostExecutionThread, repository);

        TestObserver<List<HistoryEntity>> testObserver = new TestObserver<>();

        int fakeOffset = 1;

        int fakeLimit = 1;

        boolean fakeFavourite = true;

        List<HistoryEntity> fakeHistoryEntities = Arrays.asList(new HistoryEntity(1, "text1"), new HistoryEntity(2, "text2"));

        given(repository.getHistories(fakeOffset, fakeLimit, fakeFavourite)).willReturn(Observable.just(fakeHistoryEntities));

        useCase.setParams(fakeOffset, fakeLimit, fakeFavourite);

        useCase.buildUseCaseObservable().subscribe(testObserver);

        verify(repository).getHistories(fakeOffset, fakeLimit, fakeFavourite);

        List<HistoryEntity> historyEntities = (List<HistoryEntity>) testObserver.getEvents().get(0).get(0);

        assertEquals(historyEntities.size(), fakeHistoryEntities.size());

        assertEquals(historyEntities.get(0).getId(), fakeHistoryEntities.get(0).getId());

        assertEquals(historyEntities.get(0).getText(), fakeHistoryEntities.get(0).getText());

        verifyNoMoreInteractions(repository);

        verifyZeroInteractions(mockThreadExecutor);

        verifyZeroInteractions(mockPostExecutionThread);

    }
}
