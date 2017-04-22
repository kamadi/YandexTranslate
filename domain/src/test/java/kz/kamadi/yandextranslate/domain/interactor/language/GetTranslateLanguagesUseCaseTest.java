package kz.kamadi.yandextranslate.domain.interactor.language;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.repository.LanguageRepository;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetTranslateLanguagesUseCaseTest {
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
    public void testGetTranslateLanguages() {

        GetTranslateLanguagesUseCase useCase = new GetTranslateLanguagesUseCase(mockThreadExecutor, mockPostExecutionThread, repository);

        TestObserver<Map<String, LanguageEntity>> testObserver = new TestObserver<>();

        String fakeSourceLanguageCode = "en";

        String fakeTargetLanguageCode = "ru";

        LanguageEntity fakeSourceLanguageEntity = new LanguageEntity("name", "code");

        LanguageEntity fakeTargetLanguageEntity = new LanguageEntity("name", "code");

        given(repository.getLanguageEntity(fakeSourceLanguageCode)).willReturn(Observable.just(fakeSourceLanguageEntity));

        given(repository.getLanguageEntity(fakeTargetLanguageCode)).willReturn(Observable.just(fakeTargetLanguageEntity));

        useCase.setParams(fakeSourceLanguageCode, fakeTargetLanguageCode);

        useCase.buildUseCaseObservable().subscribe(testObserver);

        verify(repository).getLanguageEntity(fakeSourceLanguageCode);

        verify(repository).getLanguageEntity(fakeTargetLanguageCode);

        Map<String, LanguageEntity> result = (Map<String, LanguageEntity>) testObserver.getEvents().get(0).get(0);

        assertEquals(result.get(GetTranslateLanguagesUseCase.SOURCE), fakeSourceLanguageEntity);

        assertEquals(result.get(GetTranslateLanguagesUseCase.TARGET), fakeTargetLanguageEntity);

        verifyNoMoreInteractions(repository);

        verifyZeroInteractions(mockThreadExecutor);

        verifyZeroInteractions(mockPostExecutionThread);

    }

}
