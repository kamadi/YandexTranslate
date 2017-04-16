package kz.kamadi.yandextranslate.data.manager;

import android.content.SharedPreferences;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.entity.Language;

public class LanguageManager {

    private static final String DOWNLOAD = "kz.kamadi.yandextranslate.data.manager.LanguageManager.DOWNLOAD";
    private static final String PRIMARY_NAME = "kz.kamadi.yandextranslate.data.manager.LanguageManager.PRIMARY_NAME";
    private static final String PRIMARY_CODE = "kz.kamadi.yandextranslate.data.manager.LanguageManager.PRIMARY_CODE";
    private static final String TRANSLATION_NAME = "kz.kamadi.yandextranslate.data.manager.LanguageManager.TRANSLATION_NAME";
    private static final String TRANSLATION_CODE = "kz.kamadi.yandextranslate.data.manager.LanguageManager.TRANSLATION_CODE";

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

    public Language getPrimaryLanguage() {
        Language language = null;

        String name = sharedPreferences.getString(PRIMARY_NAME, null);

        if (name != null) {
            language = new Language();
            language.setName(name);
            language.setCode(sharedPreferences.getString(PRIMARY_CODE, null));
            return language;
        }
        return language;
    }

    public Language getTranslationLanguage() {
        Language language = null;

        String name = sharedPreferences.getString(TRANSLATION_NAME, null);

        if (name != null) {
            language = new Language();
            language.setName(name);
            language.setCode(sharedPreferences.getString(TRANSLATION_CODE, null));
            return language;
        }
        return language;
    }

    public boolean savePrimaryLanguage(Language language) {
        if (language.getName() == null || language.getCode() == null)
            return false;

        return sharedPreferences.edit()
                .putString(PRIMARY_NAME, language.getName())
                .putString(PRIMARY_CODE, language.getCode())
                .commit();
    }

    public boolean saveTranslationLanguage(Language language) {
        if (language.getName() == null || language.getCode() == null)
            return false;

        return sharedPreferences.edit()
                .putString(TRANSLATION_NAME, language.getName())
                .putString(TRANSLATION_CODE, language.getCode())
                .commit();
    }
}
