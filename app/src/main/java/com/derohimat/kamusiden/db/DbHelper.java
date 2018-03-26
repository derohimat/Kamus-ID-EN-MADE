package com.derohimat.kamusiden.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.derohimat.kamusiden.db.DbContract.TABLE_EN;
import static com.derohimat.kamusiden.db.DbContract.TABLE_ID;
import static com.derohimat.kamusiden.db.DbContract.WordColumn.MEANING;
import static com.derohimat.kamusiden.db.DbContract.WordColumn.WORD;

/**
 * Created by denirohimat on 27/03/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_ID = "create table " + TABLE_ID +
            " (" + _ID + " integer primary key autoincrement, " +
            WORD + " text not null, " +
            MEANING + " text not null);";

    public static String CREATE_TABLE_EN = "create table " + TABLE_EN +
            " (" + _ID + " integer primary key autoincrement, " +
            WORD + " text not null, " +
            MEANING + " text not null);";

    private static String DATABASE_NAME = "dbkamusiden";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ID);
        db.execSQL(CREATE_TABLE_EN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ID);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EN);
        onCreate(db);
    }
}
