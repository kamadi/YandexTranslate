package kz.kamadi.yandextranslate.presenter;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import kz.kamadi.yandextranslate.data.entity.mapper.data.LanguageDataMapper;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.interactor.language.GetLanguagesUseCase;
import kz.kamadi.yandextranslate.ui.language.LanguageView;

import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class LanguagePresenterTest {
    @Mock
    GetLanguagesUseCase getLanguagesUseCase;
    @Mock
    LanguageDataMapper languageDataMapper;
    @Mock
    LanguageView view;

    private LanguagePresenter presenter;
    private LanguagePresenter.LanguagesGetSubscriber languagesGetSubscriber;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new LanguagePresenter(getLanguagesUseCase,languageDataMapper);
        presenter.attachView(view);
        languagesGetSubscriber = presenter.new LanguagesGetSubscriber();
    }

    @Test
    public void testGetLanguages(){

        presenter.getLanguages();

        verify(view).showLoading();

        verify(getLanguagesUseCase).execute(any(LanguagePresenter.LanguagesGetSubscriber.class));
    }

    @Test
    public void testDestroy() {

        this.presenter.destroy();

        verify(getLanguagesUseCase).unsubscribe();
    }

    @Test
    public void testDetachView() {

        this.presenter.detachView();

        assertNull(this.presenter.view);

    }

    @Test
    public void testSubscriberOnCompleted() {

        this.languagesGetSubscriber.onComplete();

        verify(this.view).hideLoading();
    }

    @Test
    public void testSubscriberOnError() {

        this.languagesGetSubscriber.onError(new Exception());

        verify(this.view).hideLoading();
        verify(this.view).handleError(any(Throwable.class));
    }

    @Test
    public void testSubscriberOnNext() {

        this.languagesGetSubscriber.onNext(Arrays.asList(new LanguageEntity("name","code")));

        verify(this.view).hideLoading();
    }
}
