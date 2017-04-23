package kz.kamadi.yandextranslate.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.data.database.history.HistoryDao;
import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.mapper.data.DictionaryDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.data.HistoryDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.data.TranslateDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.DictionaryEntityMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.HistoryEntityMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.TranslateEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.HistoryEntity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class HistoryDataRepositoryTest {

    @Mock
    HistoryDao historyDao;

    private HistoryDataRepository historyDataRepository;
    private TestObserver testObserver;
    private HistoryEntityMapper historyEntityMapper;
    private HistoryDataMapper historyDataMapper;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        historyEntityMapper = new HistoryEntityMapper(new DictionaryEntityMapper(),new TranslateEntityMapper());
        historyDataMapper = new HistoryDataMapper(new DictionaryDataMapper(),new TranslateDataMapper());
        historyDataRepository = new HistoryDataRepository(historyDao,historyEntityMapper,historyDataMapper);
        testObserver = new TestObserver();
    }

    @Test
    public void testCreate(){
        History fakeHistory = new History(1,"text");

        HistoryEntity fakeHistoryEntity = historyEntityMapper.transform(fakeHistory);

        given(historyDao.create(any(History.class))).willReturn(fakeHistory);

        historyDataRepository.create(fakeHistoryEntity).subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        verify(historyDao).create(any(History.class));

        HistoryEntity entity = (HistoryEntity) ((List<Object>) testObserver.getEvents().get(0)).get(0);

        assertEquals(entity.getId(),fakeHistoryEntity.getId());

        assertEquals(entity.getText(),fakeHistoryEntity.getText());

    }

    @Test
    public void testUpdate(){
        HistoryEntity fakeHistoryEntity = new HistoryEntity(1,"text");

        given(historyDao.update(any(History.class))).willReturn(true);

        historyDataRepository.update(fakeHistoryEntity).subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        verify(historyDao).update(any(History.class));

        Boolean result = (Boolean) ((List<Object>) testObserver.getEvents().get(0)).get(0);

        assertTrue(result);

    }

    @Test
    public void testDelete(){
        HistoryEntity fakeHistoryEntity = new HistoryEntity(1,"text");

        given(historyDao.delete(any(History.class))).willReturn(true);

        historyDataRepository.delete(fakeHistoryEntity).subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        verify(historyDao).delete(any(History.class));

        Boolean result = (Boolean) ((List<Object>) testObserver.getEvents().get(0)).get(0);

        assertTrue(result);

    }

    @Test
    public void testGetHistories(){

        List<History>histories = Arrays.asList(new History(1,"text"));

        List<HistoryEntity> fakeHistoryEntities = historyEntityMapper.transform(histories);

        int fakeOffset = 1;
        int fakeLimit = 1;
        boolean fakeFavourite = false;

        given(historyDao.getHistories(fakeOffset,fakeLimit,fakeFavourite)).willReturn(histories);

        historyDataRepository.getHistories(fakeOffset,fakeLimit,fakeFavourite).subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        verify(historyDao).getHistories(fakeOffset,fakeLimit,fakeFavourite);

        List<HistoryEntity>entities = (List<HistoryEntity>) ((List<Object>) testObserver.getEvents().get(0)).get(0);

        assertEquals(entities.size(),fakeHistoryEntities.size());

        assertEquals(entities.get(0).getId(),fakeHistoryEntities.get(0).getId());

        assertEquals(entities.get(0).getText(),fakeHistoryEntities.get(0).getText());
    }

    @Test
    public void testDeleteAll(){

        int fakeResult = 5;

        boolean fakeFavourite = false;

        given(historyDao.deleteAll(fakeFavourite)).willReturn(fakeResult);

        historyDataRepository.deleteAll(fakeFavourite).subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        verify(historyDao).deleteAll(fakeFavourite);

        Integer result = (Integer) ((List<Object>) testObserver.getEvents().get(0)).get(0);

        assertEquals(result.intValue(),fakeResult);
    }

    @Test
    public void testSearch(){

        List<History>histories = Arrays.asList(new History(1,"text"));

        List<HistoryEntity> fakeHistoryEntities = historyEntityMapper.transform(histories);

        String fakeText = "text";

        boolean fakeFavourite = false;

        given(historyDao.search(fakeText,fakeFavourite)).willReturn(histories);

        historyDataRepository.search(fakeText,fakeFavourite).subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        verify(historyDao).search(fakeText,fakeFavourite);

        List<HistoryEntity>entities = (List<HistoryEntity>) ((List<Object>) testObserver.getEvents().get(0)).get(0);

        assertEquals(entities.size(),fakeHistoryEntities.size());

        assertEquals(entities.get(0).getId(),fakeHistoryEntities.get(0).getId());

        assertEquals(entities.get(0).getText(),fakeHistoryEntities.get(0).getText());
    }
}
