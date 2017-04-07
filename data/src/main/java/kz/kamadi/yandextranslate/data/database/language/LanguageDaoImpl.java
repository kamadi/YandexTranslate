package kz.kamadi.yandextranslate.data.database.language;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.database.Dao;
import kz.kamadi.yandextranslate.data.entity.Language;

public class LanguageDaoImpl extends Dao implements LanguageDao {

    @Inject
    public LanguageDaoImpl(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected Language cursorToEntity(Cursor cursor) {
        return null;
    }


    @Override
    public boolean create(List<Language> languages) {
        return false;
    }

    @Override
    public List<Language> getLanguages() {
        return null;
    }
}
