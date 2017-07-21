package com.andrjhf.sharedprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jiahongfei on 2017/7/17.
 */

class PreferencesSQLiteOpenHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sharedProvider.db";

    private static final String SQL_CREATE_TEST_TABLE = "CREATE TABLE " + SharedProviderColumns.TABLE_NAME +
            " (" +
            SharedProviderColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

            SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME + " TEXT ," +

            SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY + " TEXT , " +

            SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE + " TEXT , " +

            SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE_CLASS + " TEXT " +
            " )";

    private static final String SQL_DELETE_TEST_TABLE = "DROP TABLE IF EXISTS " +
            SharedProviderColumns.TABLE_NAME;

    public PreferencesSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TEST_TABLE);
        onCreate(db);
    }

    public  static class SharedProviderColumns {

        public static final String TABLE_NAME = "shared_preferences";

        public static final String _ID = "_id";

        public static final String COLUMN_NAME_SHARED_PROVIDER_NAME = "column_name_shared_provider_name";

        public static final String COLUMN_NAME_SHARED_PROVIDER_KEY = "column_name_shared_provider_key";

        public static final String COLUMN_NAME_SHARED_PROVIDER_VALUE = "column_name_shared_provider_value";

        public static final String COLUMN_NAME_SHARED_PROVIDER_VALUE_CLASS = "column_name_shared_provider_value_class";

//        public static final String COLUMN_NAME_VALUE_INTEGER = "column_name_value_integer";
//        public static final String COLUMN_NAME_VALUE_LONG = "column_name_value_long";
//        public static final String COLUMN_NAME_VALUE_FLOAT = "column_name_value_float";
//        public static final String COLUMN_NAME_VALUE_BOOLEAN = "column_name_value_boolean";
    }
}
