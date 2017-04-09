package kz.kamadi.yandextranslate.data.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kz.kamadi.yandextranslate.data.database.history.HistoryScheme;
import kz.kamadi.yandextranslate.data.database.language.LanguageScheme;

public class TestDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;

    public TestDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        resetDatabase();
    }

    private void resetDatabase() {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL("DROP TABLE IF EXISTS " + LanguageScheme.TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + HistoryScheme.TABLE_NAME);
        database.execSQL(LanguageScheme.LANGUAGE_TABLE_CREATE);
        database.execSQL(HistoryScheme.HISTORY_TABLE_CREATE);
        database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
