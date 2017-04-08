package kz.kamadi.yandextranslate.data.repository;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.TranslateEntityMapper;
import kz.kamadi.yandextranslate.data.network.TranslateApi;
import kz.kamadi.yandextranslate.data.utils.RetrofitUtil;
import kz.kamadi.yandextranslate.data.utils.TestUtils;
import kz.kamadi.yandextranslate.domain.entity.TranslateEntity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;

public class TranslateDataRepositoryTest{

    private MockWebServer mockWebServer;
    private TranslateDataRepository translateDataRepository;
    private TestObserver<TranslateEntity> testObserver;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        translateDataRepository = new TranslateDataRepository(RetrofitUtil.create(mockWebServer, TranslateApi.class,true),new TranslateEntityMapper());
        testObserver = new TestObserver<>();
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testTranslateSuccessResponse() throws IOException {

        mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/translate.json"))));

        translateDataRepository.getTranslate("yandex translate","en-ru").subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        TranslateEntity entity = (TranslateEntity) testObserver.getEvents().get(0).get(0);

        assertEquals(entity.getLang(),"en-ru");

        assertEquals(entity.getText().size(),1);

        assertEquals(entity.getText().get(0),"Яндекс. перевод");
    }

}
