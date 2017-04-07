package kz.kamadi.yandextranslate.data.database.history;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import kz.kamadi.yandextranslate.data.database.Dao;
import kz.kamadi.yandextranslate.data.entity.History;

public class HistoryDaoImpl extends Dao implements HistoryDao {

    public HistoryDaoImpl(SQLiteDatabase database) {
        super(database);
    }

    @Override
    protected History cursorToEntity(Cursor cursor) {
        return null;
    }


    @Override
    public History create(History entity) {
        return null;
    }

    @Override
    public boolean update(History entity) {
        return false;
    }

    @Override
    public boolean delete(History history, boolean isFavourite) {
        return false;
    }

    @Override
    public boolean deleteAll(boolean isFavourite) {
        return false;
    }

    @Override
    public List<History> getHistories(int offset, int limit, boolean isFavourite) {
        return null;
    }

    @Override
    public List<History> search(String text, boolean isFavourite) {
        return null;
    }
}
