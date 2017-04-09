package kz.kamadi.yandextranslate.data.database.history;

import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.Translate;
import kz.kamadi.yandextranslate.data.utils.TestDatabase;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.STATUS_ACTIVE;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.STATUS_DELETED;

@RunWith(AndroidJUnit4.class)
public class HistoryDaoTest {

    private HistoryDaoImpl dao;

    @Before
    public void setUp() {
        TestDatabase testDatabase = new TestDatabase(getTargetContext());
        dao = new HistoryDaoImpl(testDatabase.getWritableDatabase(), new Gson());
    }

    @Test
    public void testCreate() {
        History fakeHistory = getHistory();
        History history = dao.create(fakeHistory);
        assertTrue(history != null);
        assertTrue(history.getId() > 0);
    }

    @Test
    public void testUpdate() {
        History fakeHistory = getHistory();
        History history = dao.create(fakeHistory);
        assertTrue(history != null);
        assertTrue(history.getId() > 0);

        history.setStatus(STATUS_DELETED);
        history.setFavourite(false);

        assertTrue(dao.update(history));
    }

    @Test
    public void testDelete() {
        History fakeHistory = getHistory();
        History history = dao.create(fakeHistory);
        assertTrue(history != null);
        assertTrue(history.getId() > 0);

        assertTrue(dao.delete(history));
    }

    @Test
    public void testDeleteAll() {
        assertTrue(dao.deleteAll(true) >= 0);
        assertTrue(dao.deleteAll(false) >= 0);
        for (int i = 0; i < 5; i++) {
            History fakeHistory = getHistory();
            dao.create(fakeHistory);
            fakeHistory = getHistory();
            fakeHistory.setFavourite(false);
            dao.create(fakeHistory);
        }

        assertEquals(dao.deleteAll(true), 5);
        assertEquals(dao.deleteAll(false), 5);
    }


    @Test
    public void testGetHistories() {
        dao.deleteAll(false);
        List<Integer> idList = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            History fakeHistory = getHistory();
            fakeHistory.setFavourite(false);
            idList.add(dao.create(fakeHistory).getId());
        }

        List<History> histories = dao.getHistories(0, 10, false);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 10);
        assertEquals(histories.get(0).getId(), idList.get(0));
        assertEquals(histories.get(9).getId(), idList.get(9));
        assertTrue(!histories.get(0).isFavourite());
        assertTrue(!histories.get(9).isFavourite());

        histories = dao.getHistories(10, 10, false);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 10);
        assertEquals(histories.get(0).getId(), idList.get(10));
        assertEquals(histories.get(9).getId(), idList.get(19));
        assertTrue(!histories.get(0).isFavourite());
        assertTrue(!histories.get(9).isFavourite());
    }

    @Test
    public void testGetFavourites() {
        dao.deleteAll(true);

        List<Integer> idList = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            idList.add(dao.create(getHistory()).getId());
        }

        List<History> histories = dao.getHistories(0, 10, true);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 10);
        assertEquals(histories.get(0).getId(), idList.get(0));
        assertEquals(histories.get(9).getId(), idList.get(9));
        assertTrue(histories.get(0).isFavourite());
        assertTrue(histories.get(9).isFavourite());

        histories = dao.getHistories(10, 10, true);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 10);
        assertEquals(histories.get(0).getId(), idList.get(10));
        assertEquals(histories.get(9).getId(), idList.get(19));
        assertTrue(histories.get(0).isFavourite());
        assertTrue(histories.get(9).isFavourite());
    }

    @Test
    public void testSearchHistories() {

        dao.deleteAll(false);

        for (int i = 0; i < 30; i++) {
            History fakeHistory = getHistory();
            fakeHistory.setFavourite(false);
            fakeHistory.setText("text" + i);
            fakeHistory.setTranslate(new Translate("Translation" + 1));
            dao.create(fakeHistory);
        }

        History fakeHistory = getHistory();
        fakeHistory.setStatus(STATUS_DELETED);
        dao.create(fakeHistory);

        List<History> histories = dao.search("text", false);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 30);
        assertTrue(!histories.get(0).isFavourite());

        histories = dao.search("Text", false);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 30);
        assertTrue(!histories.get(0).isFavourite());

        histories = dao.search("text29", false);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 1);
        assertTrue(!histories.get(0).isFavourite());

        histories = dao.search("trans", false);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 30);
        assertTrue(!histories.get(0).isFavourite());

        histories = dao.search("tRanSlation", false);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 30);
        assertTrue(!histories.get(0).isFavourite());

        histories = dao.search("abc", false);
        assertTrue(histories.isEmpty());
    }

    @Test
    public void testSearchFavourites() {

        dao.deleteAll(true);

        for (int i = 0; i < 30; i++) {
            History fakeHistory = getHistory();
            fakeHistory.setText("text" + i);
            fakeHistory.setTranslate(new Translate("Translation" + 1));
            dao.create(fakeHistory);
        }

        History fakeHistory = getHistory();
        fakeHistory.setStatus(STATUS_DELETED);
        dao.create(fakeHistory);

        List<History> histories = dao.search("text", true);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 30);
        assertTrue(histories.get(0).isFavourite());

        histories = dao.search("Text", true);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 30);
        assertTrue(histories.get(0).isFavourite());

        histories = dao.search("text29", true);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 1);
        assertTrue(histories.get(0).isFavourite());

        histories = dao.search("trans", true);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 30);
        assertTrue(histories.get(0).isFavourite());

        histories = dao.search("tRanSlation", true);
        assertTrue(!histories.isEmpty());
        assertEquals(histories.size(), 30);
        assertTrue(histories.get(0).isFavourite());

        histories = dao.search("abc", true);
        assertTrue(histories.isEmpty());

    }

    private History getHistory() {
        History fakeHistory = new History();
        fakeHistory.setStatus(STATUS_ACTIVE);
        fakeHistory.setFavourite(true);
        fakeHistory.setTranslate(new Translate("test"));
        fakeHistory.setText("test");
        fakeHistory.setLanguage("en-ru");
        return fakeHistory;
    }
}
