package com.andrjhf.sharedprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.HashMap;

public class PreferencesContentProvider extends ContentProvider {

    public static class PreferencesConstant {
        public static final String AUTHORITY = "com.andrjhf.sharedprovider";
        public static final int ALL = 0;
        /*MIME*/
        public static final String CONTENT_TYPE = "vnd.android.cursor.sharedprovider/vnd.shared.provider";

    }

    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PreferencesConstant.AUTHORITY, "#", PreferencesConstant.ALL);
    }

    private static final HashMap<String, String> articleProjectionMap;

    static {
        articleProjectionMap = new HashMap<String, String>();
        articleProjectionMap.put(PreferencesSQLiteOpenHelper.SharedProviderColumns._ID,
                PreferencesSQLiteOpenHelper.SharedProviderColumns._ID);
        articleProjectionMap.put(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY,
                PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY);
        articleProjectionMap.put(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE,
                PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE);
    }


    private ContentResolver resolver = null;
    private SQLiteOpenHelper mOpenHelper = null;
    private SQLiteDatabase mReadableDatabase = null;
    private SQLiteDatabase mWritableDatabase = null;

    public PreferencesContentProvider() {
    }

    @Override
    public boolean onCreate() {
        boolean isSuccessfully = false;
        try {
            resolver = getContext().getContentResolver();
            mOpenHelper = new PreferencesSQLiteOpenHelper(getContext());
            mReadableDatabase = mOpenHelper.getReadableDatabase();
            mWritableDatabase = mOpenHelper.getWritableDatabase();
            isSuccessfully = true;
        } catch (Exception e) {
            isSuccessfully = false;
            e.printStackTrace();
        }

        return isSuccessfully;
    }

    @Override
    public Cursor query(Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {

        SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
        String limit = null;

        switch (uriMatcher.match(uri)) {
            case PreferencesConstant.ALL: {
                sqlBuilder.setTables(PreferencesSQLiteOpenHelper.SharedProviderColumns.TABLE_NAME);
                sqlBuilder.setProjectionMap(articleProjectionMap);
                break;
            }
            default:
                throw new IllegalArgumentException("Error Uri: " + uri);
        }

        Cursor cursor = mReadableDatabase.query(
                PreferencesSQLiteOpenHelper.SharedProviderColumns.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        cursor.setNotificationUri(resolver, uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case PreferencesConstant.ALL:
                return PreferencesConstant.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Error Uri: " + uri);
        }
    }

    @Override
    public Uri insert( Uri uri, ContentValues values) {
        if (uriMatcher.match(uri) != PreferencesConstant.ALL) {
            throw new IllegalArgumentException("Error Uri: " + uri);
        }

        long id = mWritableDatabase.insert(
                PreferencesSQLiteOpenHelper.SharedProviderColumns.TABLE_NAME,
                PreferencesSQLiteOpenHelper.SharedProviderColumns._ID,
                values);
        if (id < 0) {
            throw new SQLiteException("Unable to insert " + values + " for " + uri);
        }

        Uri newUri = ContentUris.withAppendedId(uri, id);
        resolver.notifyChange(newUri, null);

        return newUri;
    }

    @Override
    public int delete( Uri uri,
                       String selection,
                       String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case PreferencesConstant.ALL: {
                count = mWritableDatabase.delete(PreferencesSQLiteOpenHelper.SharedProviderColumns.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new IllegalArgumentException("Error Uri: " + uri);
        }

        resolver.notifyChange(uri, null);

        return count;
    }

    @Override
    public int update( Uri uri,
                       ContentValues values,
                       String selection,
                       String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case PreferencesConstant.ALL: {
                count = mWritableDatabase.update(
                        PreferencesSQLiteOpenHelper.SharedProviderColumns.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            }
            default:
                throw new IllegalArgumentException("Error Uri: " + uri);
        }

        resolver.notifyChange(uri, null);

        return count;
    }


}
