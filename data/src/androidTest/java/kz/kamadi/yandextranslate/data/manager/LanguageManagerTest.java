package kz.kamadi.yandextranslate.data.manager;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.Before;
import org.junit.Test;

import kz.kamadi.yandextranslate.data.entity.Language;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class LanguageManagerTest {

    private LanguageManager languageManager;

    @Before
    public void setUp() {
        SharedPreferences sharedPreferences = getTargetContext().getSharedPreferences("base_shared_preferences", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
        languageManager = new LanguageManager(sharedPreferences);
    }

    @Test
    public void testDownload() {
        assertFalse(languageManager.isDownloaded());
        assertTrue(languageManager.setDownloaded());
        assertTrue(languageManager.isDownloaded());
    }

    @Test
    public void testPrimaryLanguage(){

        assertNull(languageManager.getPrimaryLanguage());

        assertFalse(languageManager.savePrimaryLanguage(new Language("English",null)));

        assertFalse(languageManager.savePrimaryLanguage(new Language(null,"en")));

        Language mockLanguage = new Language("English","en");

        assertTrue(languageManager.savePrimaryLanguage(mockLanguage));

        Language language = languageManager.getPrimaryLanguage();

        assertEquals(language.getName(),mockLanguage.getName());

        assertEquals(language.getCode(),mockLanguage.getCode());

    }

    @Test
    public void testTranslationLanguage(){

        assertNull(languageManager.getTranslationLanguage());

        assertFalse(languageManager.saveTranslationLanguage(new Language("English",null)));

        assertFalse(languageManager.saveTranslationLanguage(new Language(null,"en")));

        Language mockLanguage = new Language("English","en");

        assertTrue(languageManager.saveTranslationLanguage(mockLanguage));

        Language language = languageManager.getTranslationLanguage();

        assertEquals(language.getName(),mockLanguage.getName());

        assertEquals(language.getCode(),mockLanguage.getCode());

    }
}
