package kz.kamadi.yandextranslate.data.database.language;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import kz.kamadi.yandextranslate.data.entity.Language;
import kz.kamadi.yandextranslate.data.utils.TestDatabase;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LanguageDaoTest {

    private LanguageDaoImpl dao;
    private TestDatabase testDatabase;

    @Before
    public void setUp() {
        testDatabase = new TestDatabase(getTargetContext());
        dao = new LanguageDaoImpl(testDatabase.getWritableDatabase());
    }

    @Test
    public void testDao(){
        dao.deleteAll();

        List<Language>fakeLanguages = Arrays.asList(new Language("af","Африкаанс"),new Language("am","Амхарский"),new Language("ar","Арабский"));

        assertTrue(dao.create(fakeLanguages));;

        List<Language>languages = dao.getLanguages();

        assertEquals(languages.size(),fakeLanguages.size());

        assertEquals(languages.get(0).getCode(),fakeLanguages.get(0).getCode());

        assertEquals(languages.get(1).getName(),fakeLanguages.get(1).getName());
    }
}
