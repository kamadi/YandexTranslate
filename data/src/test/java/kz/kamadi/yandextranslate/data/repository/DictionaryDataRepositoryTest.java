package kz.kamadi.yandextranslate.data.repository;

import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.DictionaryEntityMapper;
import kz.kamadi.yandextranslate.data.network.DictionaryApi;
import kz.kamadi.yandextranslate.data.utils.RetrofitUtil;
import kz.kamadi.yandextranslate.data.utils.TestUtils;
import kz.kamadi.yandextranslate.domain.entity.DictionaryEntity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;

public class DictionaryDataRepositoryTest {
    private MockWebServer mockWebServer;
    private DictionaryDataRepository dictionaryDataRepository;
    private TestObserver<DictionaryEntity> testObserver;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        dictionaryDataRepository = new DictionaryDataRepository(RetrofitUtil.create(mockWebServer, DictionaryApi.class), new Gson(), new DictionaryEntityMapper());
        testObserver = new TestObserver<>();
    }

    @After
    public void tearDown() throws Exception {
        mockWebServer.shutdown();
    }

    @Test
    public void testGetDictionarySuccessResponse() throws Exception {
        this.mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(
                FileUtils.readFileToString(
                        TestUtils.getFileFromPath(this, "res/dictionary.json"))));

        this.dictionaryDataRepository.getDictionary("travel","en-ru").subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        DictionaryEntity entity = (DictionaryEntity) testObserver.getEvents().get(0).get(0);

        assertEquals(entity.getDef().size(),3);
    }
}
