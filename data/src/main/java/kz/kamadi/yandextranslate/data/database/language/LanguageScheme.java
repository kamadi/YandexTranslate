package kz.kamadi.yandextranslate.data.database.language;

public interface LanguageScheme {

    String TABLE_NAME = "language";
    String COLUMN_CODE = "code";
    String COLUMN_NAME = "name";

    String LANGUAGE_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME
            + " ("
            + COLUMN_CODE
            + " TEXT NOT NULL, "
            + COLUMN_NAME
            + " TEXT NOT NULL "
            + ")";
}
