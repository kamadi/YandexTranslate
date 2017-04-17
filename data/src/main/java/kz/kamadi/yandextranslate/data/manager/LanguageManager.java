package kz.kamadi.yandextranslate.data.manager;

import android.content.SharedPreferences;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Language;

public class LanguageManager {

    private static final String DOWNLOAD = "kz.kamadi.yandextranslate.data.manager.LanguageManager.DOWNLOAD";
    private static final String SOURCE_NAME = "kz.kamadi.yandextranslate.data.manager.LanguageManager.SOURCE_NAME";
    private static final String SOURCE_CODE = "kz.kamadi.yandextranslate.data.manager.LanguageManager.SOURCE_CODE";
    private static final String TARGET_NAME = "kz.kamadi.yandextranslate.data.manager.LanguageManager.TARGET_NAME";
    private static final String TARGET_CODE = "kz.kamadi.yandextranslate.data.manager.LanguageManager.TARGET_CODE";

    private SharedPreferences sharedPreferences;

    @Inject
    public LanguageManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean isDownloaded() {
        return sharedPreferences.getBoolean(DOWNLOAD, false);
    }

    public boolean setDownloaded() {
        return sharedPreferences.edit()
                .putBoolean(DOWNLOAD, true)
                .commit();
    }

    public Language getSourceLanguage() {
        Language language = null;

        String name = sharedPreferences.getString(SOURCE_NAME, null);

        if (name != null) {
            language = new Language();
            language.setName(name);
            language.setCode(sharedPreferences.getString(SOURCE_CODE, null));
            return language;
        }
        return language;
    }

    public Language getTargetLanguage() {
        Language language = null;

        String name = sharedPreferences.getString(TARGET_NAME, null);

        if (name != null) {
            language = new Language();
            language.setName(name);
            language.setCode(sharedPreferences.getString(TARGET_CODE, null));
            return language;
        }
        return language;
    }

    public boolean saveSourceLanguage(Language language) {
        if (language.getName() == null || language.getCode() == null)
            return false;

        return sharedPreferences.edit()
                .putString(SOURCE_NAME, language.getName())
                .putString(SOURCE_CODE, language.getCode())
                .commit();
    }

    public boolean saveTargetLanguage(Language language) {
        if (language.getName() == null || language.getCode() == null)
            return false;

        return sharedPreferences.edit()
                .putString(TARGET_NAME, language.getName())
                .putString(TARGET_CODE, language.getCode())
                .commit();
    }
}
