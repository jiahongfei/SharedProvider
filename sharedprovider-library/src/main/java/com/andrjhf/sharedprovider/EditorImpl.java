package com.andrjhf.sharedprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.alibaba.fastjson.JSON;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jiahongfei on 2017/7/17.
 */

class EditorImpl implements SharedProvider.Editor {

    private ContentResolver mContentResolver;
    private String mSharedProviderName = null;

    public EditorImpl(Context application, String sharedProviderName) {
        mContentResolver = application.getContentResolver();
        mSharedProviderName = sharedProviderName;
    }

    @Override
    public SharedProvider.Editor putString(String key, String value) {
        ContentValues contentValues = getContentValues(mSharedProviderName, key, value, String.class.getName());
        putValue(mSharedProviderName,key, contentValues);
        return this;
    }

    @Override
    public SharedProvider.Editor putInt(String key, int value) {
        ContentValues contentValues = getContentValues(mSharedProviderName,key, String.valueOf(value), Integer.class.getName());
        putValue(mSharedProviderName,key, contentValues);
        return this;
    }

    @Override
    public SharedProvider.Editor putLong(String key, long value) {
        ContentValues contentValues = getContentValues(mSharedProviderName,key, String.valueOf(value), Long.class.getName());
        putValue(mSharedProviderName,key, contentValues);
        return this;
    }

    @Override
    public SharedProvider.Editor putFloat(String key, float value) {
        ContentValues contentValues = getContentValues(mSharedProviderName,key, String.valueOf(value), Float.class.getName());
        putValue(mSharedProviderName,key, contentValues);
        return this;

    }

    @Override
    public SharedProvider.Editor putBoolean(String key, boolean value) {
        ContentValues contentValues = getContentValues(mSharedProviderName,key, String.valueOf(value), Boolean.class.getName());
        putValue(mSharedProviderName,key, contentValues);
        return this;
    }

    @Override
    public <T> SharedProvider.Editor putObject(String key, T t, String clazzName) {
        ContentValues contentValues = getContentValues(mSharedProviderName,key, JSON.toJSONString(t),clazzName);
        putValue(mSharedProviderName,key,contentValues);
        return this;
    }

    @Override
    public <T> SharedProvider.Editor putList(String key, List<T> t) {
        ContentValues contentValues = getContentValues(mSharedProviderName,key, JSON.toJSONString(t), List.class.getName());
        putValue(mSharedProviderName,key,contentValues);
        return this;
    }

    @Override
    public <K, V> SharedProvider.Editor putMap(String key, Map<K, V> map) {
        ContentValues contentValues = getContentValues(mSharedProviderName,key, JSON.toJSONString(map), Map.class.getName());
        putValue(mSharedProviderName,key,contentValues);
        return this;
    }

    @Override
    public <E> SharedProvider.Editor putSet(String key, Set<E> set) {
        ContentValues contentValues = getContentValues(mSharedProviderName,key, JSON.toJSONString(set), Set.class.getName());
        putValue(mSharedProviderName,key,contentValues);
        return this;
    }

    @Override
    public SharedProvider.Editor remove(String key) {
        mContentResolver.delete(
                SharedProviderImpl.URI,
                PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY + "=?"
                        + " AND "
                        + PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME + "=?",
                new String[]{key, mSharedProviderName});
        return this;
    }

    @Override
    public SharedProvider.Editor clear() {
        mContentResolver.delete(SharedProviderImpl.URI, PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME + "=?",
                new String[]{mSharedProviderName});
        return this;
    }

    private ContentValues getContentValues(String sharedProviderName, String key, String value, String clazz) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME,sharedProviderName);
        contentValues.put(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY, key);
        contentValues.put(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE, value);
        contentValues.put(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE_CLASS, clazz);
        return contentValues;
    }

    private void putValue(String sharedProviderName,String key, ContentValues contentValues) {
        if (isKeyExist(sharedProviderName, key)) {
            mContentResolver.update(
                    SharedProviderImpl.URI,
                    contentValues,
                    PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY + "=?"
                            + " AND "
                            + PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME + "=?",
                    new String[]{key, sharedProviderName});
        } else {
            mContentResolver.insert(SharedProviderImpl.URI, contentValues);
        }
    }

    private boolean isKeyExist(String sharedProviderName, String key) {
        Cursor cursor = mContentResolver.query(
                SharedProviderImpl.URI,
                new String[]{PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY},
                PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY + "=?"
                        + " AND "
                        + PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME + "=?",
                new String[]{key,sharedProviderName },
                null);
        if (null != cursor && cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        if(null != cursor) {
            cursor.close();
        }
        return false;
    }

}
