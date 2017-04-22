package kz.kamadi.yandextranslate.domain.interactor.translate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.domain.entity.DictionaryEntity;
import kz.kamadi.yandextranslate.domain.entity.TranslateEntity;
import kz.kamadi.yandextranslate.domain.entity.TranslationEntity;
import kz.kamadi.yandextranslate.domain.executor.PostExecutionThread;
import kz.kamadi.yandextranslate.domain.executor.ThreadExecutor;
import kz.kamadi.yandextranslate.domain.repository.DictionaryRepository;
import kz.kamadi.yandextranslate.domain.repository.TranslateRepository;

import static junit.framework.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetTranslationUseCaseTest {
    @Mock
    private ThreadExecutor mockThreadExecutor;
    @Mock
    private PostExecutionThread mockPostExecutionThread;
    @Mock
    private TranslateRepository translateRepository;
    @Mock
    private DictionaryRepository dictionaryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetTranslation() {

        GetTranslationUseCase useCase = new GetTranslationUseCase(mockThreadExecutor, mockPostExecutionThread, translateRepository, dictionaryRepository);

        TestObserver<TranslationEntity> testObserver = new TestObserver<>();

        String fakeText = "text";

        String fakeLang = "en-ru";

        TranslateEntity fakeTranslateEntity = new TranslateEntity(Arrays.asList("test"), fakeLang);

        DictionaryEntity fakeDictionaryEntity = new DictionaryEntity("content");

        given(translateRepository.getTranslate(fakeText, fakeLang)).willReturn(Observable.just(fakeTranslateEntity));

        given(dictionaryRepository.getDictionary(fakeText, fakeLang)).willReturn(Observable.just(fakeDictionaryEntity));

        useCase.setParams(fakeText, fakeLang);

        useCase.buildUseCaseObservable().subscribe(testObserver);

        verify(translateRepository).getTranslate(fakeText, fakeLang);

        verify(dictionaryRepository).getDictionary(fakeText, fakeLang);

        TranslationEntity translationEntity = (TranslationEntity) testObserver.getEvents().get(0).get(0);

        assertEquals(translationEntity.getDictionaryEntity().getContent(), fakeDictionaryEntity.getContent());

        assertEquals(translationEntity.getTranslateEntity().getLang(), fakeTranslateEntity.getLang());

        assertEquals(translationEntity.getTranslateEntity().getText(), fakeTranslateEntity.getText());

        verifyNoMoreInteractions(translateRepository);

        verifyNoMoreInteractions(dictionaryRepository);

        verifyZeroInteractions(mockThreadExecutor);

        verifyZeroInteractions(mockPostExecutionThread);
    }

}
