package kz.kamadi.yandextranslate.data.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

public abstract class Dao {
    private SQLiteDatabase mDb;

    public Dao(SQLiteDatabase db) {
        this.mDb = db;
    }

    protected int delete(String tableName, String selection,
                         String[] selectionArgs) {
        return mDb.delete(tableName, selection, selectionArgs);
    }

    protected long insert(String tableName, ContentValues values) {
        return mDb.insert(tableName, null, values);
    }

    protected abstract <T> T cursorToEntity(Cursor cursor);

    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String sortOrder) {
        return mDb.query(tableName, columns,
                selection, selectionArgs, null, null, sortOrder);
    }

    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String sortOrder,
                        String limit) {

        return mDb.query(tableName, columns, selection,
                selectionArgs, null, null, sortOrder, limit);
    }

    public Cursor query(String tableName, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {

        return mDb.query(tableName, columns, selection,
                selectionArgs, groupBy, having, orderBy, limit);
    }

    public int update(String tableName, ContentValues values,
                      String selection, String[] selectionArgs) {
        return mDb.update(tableName, values, selection,
                selectionArgs);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return mDb.rawQuery(sql, selectionArgs);
    }

    protected void beginTransaction() {
        try {
            mDb.beginTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void endTransaction() {
        try {
            mDb.setTransactionSuccessful();
        } finally {
            mDb.endTransaction();
        }
    }

    protected boolean exists(String table_name, String columnName, int value) {
        return DatabaseUtils.longForQuery(mDb, String.format("SELECT COUNT(*) FROM %s WHERE %s=? LIMIT 1", table_name, columnName), new String[]{String.valueOf(value)}) > 0;
    }

    protected boolean exists(String table_name, String columnName1, String value1, String columnName2, String value2) {
        return DatabaseUtils.longForQuery(mDb, String.format("SELECT COUNT(*) FROM %s WHERE %s=? AND %s=? LIMIT 1", table_name, columnName1, columnName2), new String[]{value1, value2}) > 0;
    }
}
