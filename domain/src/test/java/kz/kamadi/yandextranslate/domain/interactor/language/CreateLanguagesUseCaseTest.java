package kz.kamadi.yandextranslate.domain.interactor.language;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class CreateLanguagesUseCaseTest {
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private LanguageRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateLanguage() {

        CreateLanguagesUseCase useCase = new CreateLanguagesUseCase(mockThreadExecutor, mockPostExecutionThread, repository);

        TestObserver<Boolean> testObserver = new TestObserver<>();

        List<LanguageEntity> fakeLanguageEntities = Arrays.asList(new LanguageEntity("name", "code"));

        given(repository.create(fakeLanguageEntities)).willReturn(Observable.just(true));

        useCase.setParam(fakeLanguageEntities);

        useCase.buildUseCaseObservable().subscribe(testObserver);

        verify(repository).create(fakeLanguageEntities);

        Boolean result = (Boolean) testObserver.getEvents().get(0).get(0);

        assertTrue(result);

        verifyNoMoreInteractions(repository);

        verifyZeroInteractions(mockThreadExecutor);

        verifyZeroInteractions(mockPostExecutionThread);

    }
}
