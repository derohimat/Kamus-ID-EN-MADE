package com.derohimat.kamusiden.db;

import android.provider.BaseColumns;

/**
 * Created by denirohimat on 27/03/18.
 */

public class DbContract {

    public static String TABLE_ID = "table_id";
    public static String TABLE_EN = "table_en";

    static final class WordColumn implements BaseColumns {

        static String WORD = "word";
        static String MEANING = "meaning";

    }
}
