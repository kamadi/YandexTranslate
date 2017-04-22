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

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetLanguagesUseCaseTest {
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
    public void testGetLanguages() {

        GetLanguagesUseCase useCase = new GetLanguagesUseCase(mockThreadExecutor, mockPostExecutionThread, repository);

        TestObserver<List<LanguageEntity>> testObserver = new TestObserver<>();

        List<LanguageEntity> fakeLanguageEntities = Arrays.asList(new LanguageEntity("name", "code"));

        given(repository.getLanguages()).willReturn(Observable.just(fakeLanguageEntities));

        useCase.buildUseCaseObservable().subscribe(testObserver);

        verify(repository).getLanguages();

        List<LanguageEntity> languageEntities = (List<LanguageEntity>) testObserver.getEvents().get(0).get(0);

        assertEquals(languageEntities.size(), fakeLanguageEntities.size());

        assertEquals(languageEntities.get(0).getName(), fakeLanguageEntities.get(0).getName());

        assertEquals(languageEntities.get(0).getCode(), fakeLanguageEntities.get(0).getCode());

        verifyNoMoreInteractions(repository);

        verifyZeroInteractions(mockThreadExecutor);

        verifyZeroInteractions(mockPostExecutionThread);

    }

}