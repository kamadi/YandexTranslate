package kz.kamadi.yandextranslate.data.repository;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.data.network.TranslateApi;
import kz.kamadi.yandextranslate.data.utils.RetrofitUtil;
import kz.kamadi.yandextranslate.data.utils.TestUtils;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;

public class LanguageRemoteDataRepositoryTest {
    private MockWebServer mockWebServer;
    private LanguageRemoteDataRepository languageRemoteDataRepository;
    private TestObserver<List<LanguageEntity>> testObserver;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        languageRemoteDataRepository = new LanguageRemoteDataRepository(RetrofitUtil.create(mockWebServer, TranslateApi.class,true));
        testObserver = new TestObserver<>();
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetLangsSuccessResponse() throws IOException {

        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/language.json"))));


        languageRemoteDataRepository.getLanguages().subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        List<LanguageEntity>entities = (List<LanguageEntity>) testObserver.getEvents().get(0).get(0);

        assertEquals(entities.size(),93);

        assertEquals(entities.get(0).getCode(),"af");
        assertEquals(entities.get(0).getName(),"Африкаанс");

        assertEquals(entities.get(entities.size()-1).getCode(),"zh");
        assertEquals(entities.get(entities.size()-1).getName(),"Китайский");
    }
}
