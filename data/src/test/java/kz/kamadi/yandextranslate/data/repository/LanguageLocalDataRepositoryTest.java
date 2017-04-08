package kz.kamadi.yandextranslate.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;
import kz.kamadi.yandextranslate.data.database.language.LanguageDao;
import kz.kamadi.yandextranslate.data.entity.Language;
import kz.kamadi.yandextranslate.data.entity.mapper.data.LanguageDataMapper;
import kz.kamadi.yandextranslate.data.entity.mapper.entity.LanguageEntityMapper;
import kz.kamadi.yandextranslate.domain.entity.LanguageEntity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

public class LanguageLocalDataRepositoryTest {

    @Mock
    LanguageDao languageDao;

    private LanguageLocalDataRepository languageLocalDataRepository;

    private TestObserver testObserver;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        languageLocalDataRepository = new LanguageLocalDataRepository(languageDao, new LanguageEntityMapper(), new LanguageDataMapper());
        testObserver = new TestObserver();
    }

    @Test
    public void testLanguageCreate() {

        when(languageDao.create(anyList())).thenReturn(true);

        languageLocalDataRepository.create(new ArrayList<>()).subscribe(testObserver);

        testObserver.awaitTerminalEvent();

        Boolean aBoolean = (Boolean) ((List<Object>) testObserver.getEvents().get(0)).get(0);

        assertTrue(aBoolean);
    }

    @Test
    public void testGetLanguagesSuccess() {

        List<Language> fakeLanguages = Arrays.asList(new Language("af", "Африкаанс"), new Language("am", "Амхарский"), new Language("ar", "Арабский"));

        when(languageDao.getLanguages()).thenReturn(fakeLanguages);

        languageLocalDataRepository.getLanguages().subscribe(testObserver);

        List<LanguageEntity> entities = (List<LanguageEntity>) ((List<Object>) testObserver.getEvents().get(0)).get(0);

        assertEquals(entities.size(), fakeLanguages.size());

        assertEquals(entities.get(0).getCode(), fakeLanguages.get(0).getCode());

        assertEquals(entities.get(0).getName(), fakeLanguages.get(0).getName());
    }
}
