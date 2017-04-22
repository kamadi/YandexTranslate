package kz.kamadi.yandextranslate.data.database.language;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.database.Dao;
import kz.kamadi.yandextranslate.data.entity.Language;

import static kz.kamadi.yandextranslate.data.database.language.LanguageScheme.COLUMN_CODE;
import static kz.kamadi.yandextranslate.data.database.language.LanguageScheme.COLUMN_NAME;
import static kz.kamadi.yandextranslate.data.database.language.LanguageScheme.TABLE_NAME;

public class LanguageDaoImpl extends Dao implements LanguageDao {
    private Cursor cursor;

    @Inject
    public LanguageDaoImpl(SQLiteDatabase db) {
        super(db);
    }

    @Override
    protected Language cursorToEntity(Cursor cursor) {
        Language language = null;
        if (cursor != null) {

            language = new Language();

            if (cursor.getColumnIndex(COLUMN_CODE) != -1) {
                language.setCode(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
            }
            if (cursor.getColumnIndex(COLUMN_NAME) != -1) {
                language.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            }
        }
        return language;
    }


    @Override
    public boolean create(List<Language> languages) {
        super.beginTransaction();
        for (Language language : languages) {
            create(language);
        }
        super.endTransaction();
        return true;
    }

    public boolean create(Language language) {
        try {
            ContentValues contentValues = getContentValues(language);
            if (super.exists(TABLE_NAME, COLUMN_CODE, String.valueOf(language.getCode()))) {
                return super.update(TABLE_NAME, contentValues, COLUMN_CODE + "=?", new String[]{language.getCode()}) > 0;
            } else {
                return super.insert(TABLE_NAME, contentValues) > 0;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Language> getLanguages() {
        List<Language> languages = new ArrayList<>();
        cursor = super.query(TABLE_NAME, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                languages.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
        }
        return languages;
    }

    @Override
    public Language getLanguageByCode(String code) {

        Language language = null;

        cursor = super.query(TABLE_NAME, null, COLUMN_CODE + "=?", new String[]{code}, null);
        if (cursor != null) {
            language = new Language();
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                language = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return language;
    }

    public boolean deleteAll() {
        try {
            return super.delete(TABLE_NAME, null, null) > 0;
        } catch (SQLiteConstraintException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    private ContentValues getContentValues(Language language) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CODE, language.getCode());
        contentValues.put(COLUMN_NAME, language.getName());
        return contentValues;
    }
}
