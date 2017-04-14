package kz.kamadi.yandextranslate.data.database.history;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import kz.kamadi.yandextranslate.data.database.Dao;
import kz.kamadi.yandextranslate.data.entity.Dictionary;
import kz.kamadi.yandextranslate.data.entity.History;
import kz.kamadi.yandextranslate.data.entity.Translate;

import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.COLUMN_DICTIONARY;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.COLUMN_FAVOURITE;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.COLUMN_ID;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.COLUMN_LANGUAGE;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.COLUMN_STATUS;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.COLUMN_TEXT;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.COLUMN_TRANSLATION;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.FAVOURITE;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.NO_FAVOURITE;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.STATUS_ACTIVE;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.STATUS_DELETED;
import static kz.kamadi.yandextranslate.data.database.history.HistoryScheme.TABLE_NAME;

public class HistoryDaoImpl extends Dao implements HistoryDao {

    private Cursor cursor;
    private Gson gson;

    @Inject
    public HistoryDaoImpl(SQLiteDatabase database, Gson gson) {
        super(database);
        this.gson = gson;
    }

    @Override
    protected History cursorToEntity(Cursor cursor) {
        History history = null;
        if (cursor != null) {
            history = new History();
            if (cursor.getColumnIndex(COLUMN_ID) != -1) {
                history.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            }
            if (cursor.getColumnIndex(COLUMN_TEXT) != -1) {
                history.setText(cursor.getString(cursor.getColumnIndex(COLUMN_TEXT)));
            }
            if (cursor.getColumnIndex(COLUMN_TRANSLATION) != -1) {
                history.setTranslate(new Translate(cursor.getString(cursor.getColumnIndex(COLUMN_TRANSLATION))));
            }
            if (cursor.getColumnIndex(COLUMN_DICTIONARY) != -1) {
                String json = cursor.getString(cursor.getColumnIndex(COLUMN_DICTIONARY));
                if (json != null) {
                    Dictionary dictionary = gson.fromJson(json, Dictionary.class);
                    dictionary.setContent(json);
                    history.setDictionary(dictionary);
                }
            }
            if (cursor.getColumnIndex(COLUMN_FAVOURITE) != -1) {
                history.setFavourite(cursor.getInt(cursor.getColumnIndex(COLUMN_FAVOURITE)) == 1);
            }
            if (cursor.getColumnIndex(COLUMN_STATUS) != -1) {
                history.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
            }
            if (cursor.getColumnIndex(COLUMN_LANGUAGE) != -1) {
                history.setLanguage(cursor.getString(cursor.getColumnIndex(COLUMN_LANGUAGE)));
            }
        }
        return history;
    }


    @Override
    public History create(History history) {
        ContentValues contentValues = getContentValues(history);
        long id = insert(TABLE_NAME, contentValues);
        if (id > 0) {
            history.setId((int) id);
            return history;
        }
        return null;
    }

    @Override
    public boolean update(History history) {
        ContentValues contentValues = getContentValues(history);
        return update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(history.getId())}) > 0;
    }

    @Override
    public boolean delete(History history) {
        return delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(history.getId())}) > 0;
    }

    @Override
    public int deleteAll(boolean isFavourite) {
        int deleteRows;
        int updateRows;

        ContentValues contentValues = new ContentValues();

        if (isFavourite) {
            deleteRows = delete(TABLE_NAME, COLUMN_FAVOURITE + "=? AND " + COLUMN_STATUS + "=?", new String[]{String.valueOf(FAVOURITE), String.valueOf(STATUS_DELETED)});
            contentValues.put(COLUMN_FAVOURITE, NO_FAVOURITE);
        } else {
            deleteRows = delete(TABLE_NAME, COLUMN_FAVOURITE + "=" + NO_FAVOURITE, null);
            contentValues.put(COLUMN_STATUS, STATUS_DELETED);
        }

        updateRows = update(TABLE_NAME, contentValues, COLUMN_FAVOURITE + "=?", new String[]{String.valueOf(FAVOURITE)});

        return deleteRows + updateRows;
    }

    @Override
    public List<History> getHistories(int offset, int limit, boolean isFavourite) {

        List<History> histories = new ArrayList<>();

        if (isFavourite) {
            cursor = query(TABLE_NAME, null, COLUMN_FAVOURITE + "=" + FAVOURITE, null, COLUMN_ID + " DESC", offset + "," + limit);
        } else {
            cursor = query(TABLE_NAME, null, COLUMN_STATUS + "=" + STATUS_ACTIVE, null, COLUMN_ID + " DESC", offset + "," + limit);
        }
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                histories.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
            cursor.close();

        }

        return histories;
    }

    @Override
    public List<History> search(String text, boolean isFavourite) {

        List<History> histories = new ArrayList<>();

        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE ";

        sql += "(" + COLUMN_DICTIONARY + " LIKE '%" + text + "%' OR " + COLUMN_TEXT + " LIKE '%" + text + "%' OR " + COLUMN_TRANSLATION + " LIKE '%" + text + "%' )";

        sql += " AND " + COLUMN_FAVOURITE + "=" + String.valueOf(isFavourite ? FAVOURITE : NO_FAVOURITE);

        if (!isFavourite) {
            sql += " AND " + COLUMN_STATUS + "=" + STATUS_ACTIVE;
        }

        cursor = rawQuery(sql, null);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                histories.add(cursorToEntity(cursor));
                cursor.moveToNext();
            }
            cursor.close();

        }

        return histories;
    }

    private ContentValues getContentValues(History history) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, history.getId());
        contentValues.put(COLUMN_TEXT, history.getText());
        if (history.getTranslate() != null) {
            contentValues.put(COLUMN_TRANSLATION, history.getTranslate().getTranslation());
        }
        if (history.getDictionary() != null) {
            contentValues.put(COLUMN_DICTIONARY, history.getDictionary().getContent());
        }
        contentValues.put(COLUMN_FAVOURITE, history.isFavourite() ? FAVOURITE : NO_FAVOURITE);
        contentValues.put(COLUMN_STATUS, history.getStatus());
        contentValues.put(COLUMN_LANGUAGE, history.getLanguage());
        return contentValues;
    }
}
