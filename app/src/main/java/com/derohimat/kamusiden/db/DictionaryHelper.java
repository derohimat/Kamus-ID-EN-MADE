package com.derohimat.kamusiden.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.derohimat.kamusiden.model.WordDao;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.derohimat.kamusiden.db.DbContract.WordColumn.MEANING;
import static com.derohimat.kamusiden.db.DbContract.WordColumn.WORD;

/**
 * Created by denirohimat on 27/03/18.
 */

public class DictionaryHelper {
    private Context context;
    private DbHelper dbHelper;
    private SQLiteDatabase db;

    public DictionaryHelper(Context context) {
        this.context = context;
    }

    public DictionaryHelper open() throws SQLException {
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<WordDao> searchWord(String table, String query) {
        Cursor cursor = db.query(table, new String[]{_ID, WORD, MEANING},
                WORD + " like " + "'%" + query + "%'", null, null, null, _ID + " ASC");
        cursor.moveToFirst();
        ArrayList<WordDao> arrayList = new ArrayList<>();
        WordDao wordDao;
        if (cursor.getCount() > 0) {
            do {
                wordDao = new WordDao();
                wordDao.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                wordDao.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                wordDao.setMeaning(cursor.getString(cursor.getColumnIndexOrThrow(MEANING)));

                arrayList.add(wordDao);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(String table, WordDao wordDao) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(WORD, wordDao.getWord());
        initialValues.put(MEANING, wordDao.getMeaning());
        return db.insert(table, null, initialValues);
    }

    public void beginTransaction() {
        db.beginTransaction();
    }

    public void setTransactionSuccess() {
        db.setTransactionSuccessful();
    }

    public void endTransaction() {
        db.endTransaction();
    }

    public void insertTransaction(String table, WordDao wordDao) {
        String sql = "INSERT INTO " + table + " (" + WORD + ", " + MEANING
                + ") VALUES (?, ?)";
        SQLiteStatement sqLiteStatement = db.compileStatement(sql);
        sqLiteStatement.bindString(1, wordDao.getWord());
        sqLiteStatement.bindString(2, wordDao.getMeaning());
        sqLiteStatement.execute();
        sqLiteStatement.clearBindings();
    }

    public int update(String table, WordDao wordDao) {
        ContentValues args = new ContentValues();
        args.put(WORD, wordDao.getWord());
        args.put(MEANING, wordDao.getMeaning());
        return db.update(table, args, _ID + "= '" + wordDao.getId() + "'", null);
    }

    public int deleteId(String table, int id) {
        return db.delete(table, _ID + " = '" + id + "'", null);
    }
}
