package kz.kamadi.yandextranslate.data.database.history;

public interface HistoryScheme {

    String TABLE_NAME = "history";
    String COLUMN_ID = "_id";
    String COLUMN_TEXT = "text";
    String COLUMN_TRANSLATION = "translation";
    String COLUMN_DICTIONARY = "dictionary";
    String COLUMN_FAVOURITE = "favourite";
    String COLUMN_LANGUAGE = "language";
    String COLUMN_STATUS = "status";

    int STATUS_ACTIVE = 0;
    int STATUS_DELETED = 1;
    int NO_FAVOURITE = 0;
    int FAVOURITE = 1;

    String HISTORY_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ("
            + COLUMN_ID
            + " INTEGER PRIMARY KEY, "
            + COLUMN_TEXT
            + " TEXT NOT NULL, "
            + COLUMN_TRANSLATION
            + " TEXT NOT NULL, "
            + COLUMN_DICTIONARY
            + " TEXT NULL, "
            + COLUMN_FAVOURITE
            + " INTEGER, "
            + COLUMN_LANGUAGE
            + " TEXT NOT NULL, "
            + COLUMN_STATUS
            + " INTEGER "
            + ")";
}
