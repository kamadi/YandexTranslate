package kz.kamadi.yandextranslate.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;
import javax.inject.Singleton;

import kz.kamadi.yandextranslate.data.database.history.HistoryScheme;
import kz.kamadi.yandextranslate.data.database.language.LanguageScheme;

@Singleton
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "prosports.db";
    private static final int DATABASE_VERSION = 1;

    @Inject
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HistoryScheme.HISTORY_TABLE_CREATE);
        db.execSQL(LanguageScheme.LANGUAGE_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
