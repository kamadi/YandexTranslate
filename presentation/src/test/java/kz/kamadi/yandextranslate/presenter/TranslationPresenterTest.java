package kz.kamadi.yandextranslate.presenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.Translation;
import kz.kamadi.yandextranslate.data.entity.mapper.data.HistoryDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.data.LanguageDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.data.TranslationDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.HistoryEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;
import kz.kamadi.yandextranslate.domain.entity.TranslationEntity;
import kz.kamadi.yandextranslate.domain.interactor.history.CreateHistoryUseCase;
import kz.kamadi.yandextranslate.domain.interactor.history.UpdateHistoryUseCase;
import kz.kamadi.yandextranslate.domain.interactor.language.GetTranslateLanguagesUseCase;
import kz.kamadi.yandextranslate.domain.interactor.translate.GetTranslationUseCase;
import kz.kamadi.yandextranslate.ui.translate.TranslateView;

import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class TranslationPresenterTest {

    @Mock TranslateView view;
    @Mock GetTranslationUseCase getTranslationUseCase;
    @Mock CreateHistoryUseCase createHistoryUseCase;
    @Mock UpdateHistoryUseCase updateHistoryUseCase;
    @Mock GetTranslateLanguagesUseCase getTranslateLanguagesUseCase;
    @Mock TranslationDataMapper translationDataMapper;
    @Mock HistoryEntityMapper historyEntityMapper;
    @Mock HistoryDataMapper historyDataMapper;
    @Mock LanguageDataMapper languageDataMapper;

    private TranslationPresenter presenter;
    private TranslationPresenter.HistoryCreateSubscriber historyCreateSubscriber;
    private TranslationPresenter.HistoryUpdateSubscriber historyUpdateSubscriber;
    private TranslationPresenter.LanguagesGetSubscriber languagesGetSubscriber;
    private TranslationPresenter.TranslateGetSubscriber translateGetSubscriber;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new TranslationPresenter(getTranslationUseCase,createHistoryUseCase,updateHistoryUseCase,getTranslateLanguagesUseCase,
                translationDataMapper,historyEntityMapper,historyDataMapper,languageDataMapper);
        presenter.attachView(view);
        historyCreateSubscriber = presenter.new HistoryCreateSubscriber();
        historyUpdateSubscriber = presenter.new HistoryUpdateSubscriber();
        languagesGetSubscriber = presenter.new LanguagesGetSubscriber();
        translateGetSubscriber = presenter.new TranslateGetSubscriber("text","lang");
    }

    @Test
    public void testTranslate(){

        this.presenter.translate("text","lang");

        verify(this.view).showLoading();

        verify(this.getTranslationUseCase).execute(any(TranslationPresenter.TranslateGetSubscriber.class));
    }

    @Test
    public void testCreateHistory(){

        this.presenter.createHistory(new Translation());

        verify(this.view,never()).showLoading();

        verify(this.createHistoryUseCase).execute(any(TranslationPresenter.HistoryCreateSubscriber.class));

    }

    @Test
    public void testUpdateHistory(){

        this.presenter.updateHistory(new History());

        verify(this.view,never()).showLoading();

        verify(this.updateHistoryUseCase).execute(any(TranslationPresenter.HistoryUpdateSubscriber.class));

    }

    @Test
    public void testGetLanguages(){

        this.presenter.getLanguages("source","target");

        verify(this.view,never()).showLoading();

        verify(this.getTranslateLanguagesUseCase).execute(any(TranslationPresenter.LanguagesGetSubscriber.class));

    }

    @Test
    public void testDetachView() {

        this.presenter.detachView();

        assertNull(this.presenter.view);

    }

    @Test
    public void testTranslateGetSubscriberOnCompleted() {

        this.translateGetSubscriber.onComplete();

        verify(this.view).hideLoading();
    }

    @Test
    public void translateGetSubscriber() {

        this.translateGetSubscriber.onError(new Exception());

        verify(this.view).hideLoading();
        verify(this.view).handleError(any(Throwable.class));
    }

    @Test
    public void testTranslateGetSubscriberOnNext() {

        this.translateGetSubscriber.onNext(new TranslationEntity());

        verify(this.view).hideLoading();
    }


    @Test
    public void testHistoryUpdateSubscriberOnCompleted() {

        this.historyUpdateSubscriber.onComplete();

        verify(this.view,never()).hideLoading();
    }

    @Test
    public void testHistoryUpdateSubscriberOnError() {

        this.historyUpdateSubscriber.onError(new Exception());

        verify(this.view,never()).hideLoading();
        verify(this.view,never()).handleError(any(Throwable.class));
    }

    @Test
    public void testHistoryUpdateSubscriberOnNext() {

        this.historyUpdateSubscriber.onNext(true);

        verify(this.view,never()).hideLoading();
    }

    @Test
    public void testHistoryCreateSubscriberOnCompleted() {

        this.historyCreateSubscriber.onComplete();

        verify(this.view,never()).hideLoading();
    }

    @Test
    public void testHistoryCreateSubscriberOnError() {

        this.historyCreateSubscriber.onError(new Exception());

        verify(this.view,never()).hideLoading();
        verify(this.view,never()).handleError(any(Throwable.class));
    }

    @Test
    public void testHistoryCreateSubscriberOnNext() {

        this.historyCreateSubscriber.onNext(new HistoryEntity());

        verify(this.view,never()).hideLoading();
    }

    @Test
    public void testLanguageGetSubscriberOnCompleted() {

        this.languagesGetSubscriber.onComplete();

        verify(this.view,never()).hideLoading();
    }

    @Test
    public void testLanguageGetSubscriberOnError() {

        this.languagesGetSubscriber.onError(new Exception());

        verify(this.view,never()).hideLoading();
        verify(this.view,never()).handleError(any(Throwable.class));
    }

    @Test
    public void testLanguageGetSubscriberOnNext() {

        this.languagesGetSubscriber.onNext(new HashMap<>());

        verify(this.view,never()).hideLoading();
    }

}
