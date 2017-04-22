package kz.kamadi.yandextranslate.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import kz.kamadi.yandextranslate.domain.interactor.language.CreateLanguagesUseCase;
import kz.kamadi.yandextranslate.domain.interactor.language.DownloadLanguagesUseCase;
import kz.kamadi.yandextranslate.ui.splash.SplashView;

import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class SplashPresenterTest {
    @Mock SplashView view;
    @Mock DownloadLanguagesUseCase downloadLanguagesUseCase;
    @Mock CreateLanguagesUseCase createLanguagesUseCase;

    private SplashPresenter presenter;
    private SplashPresenter.LanguagesCreateSubscriber languagesCreateSubscriber;
    private SplashPresenter.LanguagesDownloadSubscriber languagesDownloadSubscriber;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        presenter = new SplashPresenter(downloadLanguagesUseCase,createLanguagesUseCase);
        presenter.attachView(view);
        languagesCreateSubscriber = presenter.new LanguagesCreateSubscriber();
        languagesDownloadSubscriber = presenter.new LanguagesDownloadSubscriber();
    }

    @Test
    public void testCreateLanguages(){

        this.presenter.createLanguages(Arrays.asList(new LanguageEntity("name","code")));

        verify(createLanguagesUseCase).execute(any(SplashPresenter.LanguagesCreateSubscriber.class));
    }

    @Test
    public void testDownloadLanuages(){

        this.presenter.downloadLanguages();

        verify(downloadLanguagesUseCase).execute(any(SplashPresenter.LanguagesDownloadSubscriber.class));

    }

    @Test
    public void testDestroy() {

        this.presenter.destroy();

        verify(downloadLanguagesUseCase).unsubscribe();

        verify(createLanguagesUseCase).unsubscribe();
    }

    @Test
    public void testDetachView() {

        this.presenter.detachView();

        assertNull(this.presenter.view);

    }

    @Test
    public void testLanguagesCreateSubscriberOnCompleted() {

        this.languagesCreateSubscriber.onComplete();

        verify(this.view).hideLoading();
    }

    @Test
    public void testLanguagesCreateSubscriberOnError() {

        this.languagesCreateSubscriber.onError(new Exception());

        verify(this.view).hideLoading();
        verify(this.view).handleError(any(Throwable.class));
    }

    @Test
    public void testLanguagesCreateSubscriberOnNext() {

        this.languagesCreateSubscriber.onNext(true);

        verify(this.view).hideLoading();
    }

    @Test
    public void testLanguagesDownloadSubscriberOnCompleted() {

        this.languagesDownloadSubscriber.onComplete();

        verify(this.view).hideLoading();
    }

    @Test
    public void testLanguagesDownloadSubscriberOnError() {

        this.languagesDownloadSubscriber.onError(new Exception());

        verify(this.view).hideLoading();
        verify(this.view).handleError(any(Throwable.class));
    }

    @Test
    public void testLanguagesDownloadSubscriberOnNext() {

        this.languagesDownloadSubscriber.onNext(Arrays.asList(new LanguageEntity("name","code")));

        verify(this.view).hideLoading();
    }
}
