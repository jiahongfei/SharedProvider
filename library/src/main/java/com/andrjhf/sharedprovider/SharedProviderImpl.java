package com.andrjhf.sharedprovider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jiahongfei on 2017/7/17.
 */

public class SharedProviderImpl implements SharedProvider {

    public static final Uri URI = Uri.parse("content://"
            + PreferencesContentProvider.PreferencesConstant.AUTHORITY
            + "/"
            + PreferencesContentProvider.PreferencesConstant.ALL);


    private ContentResolver mContentResolver;
    private Context mContext;
    private String mSharedProviderName = null;

    public SharedProviderImpl(Context application, String sharedProviderName) {
        mContext = application;
        mContentResolver = application.getContentResolver();
        mSharedProviderName = sharedProviderName;
    }

    @Override
    public Map<String, ?> getAll() {

        Map<String, Object> allMap = new HashMap<>();

        Cursor cursor = mContentResolver.query(
                SharedProviderImpl.URI,
                new String[]{
                        PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME,
                        PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY,
                        PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE,
                        PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE_CLASS,
                },
                PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME + "=?",
                new String[]{mSharedProviderName},
                null);
        if(null == cursor){
            return null;
        }
        while (cursor.moveToNext()) {
//            String nameString = cursor.getString(cursor.getColumnIndex(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME));
            String keyString = cursor.getString(cursor.getColumnIndex(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY));
            String valueString = cursor.getString(cursor.getColumnIndex(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE));
            String clazzString = cursor.getString(cursor.getColumnIndex(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE_CLASS));
            Object tmpObject = valueString;
            try {
                if (String.class.getName().equals(clazzString)) {
                    tmpObject = valueString;
                } else if (Integer.class.getName().equals(clazzString)) {
                    tmpObject = Integer.parseInt(valueString);
                } else if (Long.class.getName().equals(clazzString)) {
                    tmpObject = Long.parseLong(valueString);
                } else if (Float.class.getName().equals(clazzString)) {
                    tmpObject = Float.parseFloat(valueString);
                } else if (Boolean.class.getName().equals(clazzString)) {
                    tmpObject = Boolean.parseBoolean(valueString);
                } else {
                    tmpObject = JSON.parseObject(valueString, Class.forName(clazzString));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            allMap.put(keyString, tmpObject);
        }
        cursor.close();
        return allMap;
    }

    @Override
    public String getString(String key,  String defValue) {
        return getValue(mSharedProviderName, key, defValue, String.class.getName());
    }

    @Override
    public int getInt(String key, int defValue) {
        int resultInt = defValue;
        try {
            resultInt = Integer.parseInt(getValue(mSharedProviderName,key, String.valueOf(defValue), Integer.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultInt;
    }

    @Override
    public long getLong(String key, long defValue) {
        long resultLong = defValue;
        try {
            resultLong = Long.parseLong(getValue(mSharedProviderName,key, String.valueOf(defValue), Long.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultLong;
    }

    @Override
    public float getFloat(String key, float defValue) {
        float resultFloat = defValue;
        try {
            resultFloat = Float.parseFloat(getValue(mSharedProviderName,key, String.valueOf(defValue), Float.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultFloat;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        boolean resultBoolean = defValue;
        try {
            resultBoolean = Boolean.parseBoolean(getValue(mSharedProviderName,key, String.valueOf(defValue), Boolean.class.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultBoolean;
    }

    @Override
    public <T> T getObject(String key, String clazzName) {
        String valueString = getValue(mSharedProviderName,key, "", clazzName);
        T t = null;
        try {
            t = (T) JSON.parseObject(valueString, Class.forName(clazzName));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public <T> List<T> getList(String key) {
        String valueString = getValue(mSharedProviderName,key, "", List.class.getName());
        List<T> t = null;
        try {
            t = (List<T>) JSON.parseObject(valueString, List.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public <K, V> Map<K, V> getMap(String key) {
        String valueString = getValue(mSharedProviderName,key, "", Map.class.getName());
        Map<K,V> m = null;
        try {
            m = (Map<K,V>) JSON.parseObject(valueString, Map.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return m;
    }

    @Override
    public <E> Set<E> getSet(String key) {
        String valueString = getValue(mSharedProviderName,key, "", Set.class.getName());
        Set<E> set = null;
        try {
            set = (Set<E>) JSON.parseObject(valueString, Set.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return set;
    }

    @Override
    public boolean contains(String key) {
        return isKeyExist(mSharedProviderName,key);
    }

    @Override
    public Editor edit() {
        return new EditorImpl(mContext, mSharedProviderName);
    }

    private String getValue(String sharedProviderName, String key, String defValue, String clazz) {
        Cursor cursor = mContentResolver.query(
                SharedProviderImpl.URI,
                new String[]{
                        PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME,
                        PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY,
                        PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE,
                        PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE_CLASS,
                },
                PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_KEY + "=?"
                        + " AND "
                        + PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_NAME + "=?",
                new String[]{key, sharedProviderName},
                null);
        String string = defValue;
        if (null != cursor && cursor.getCount() > 0) {
            String getClazz = null;
            while (cursor.moveToNext()) {
                string = cursor.getString(cursor.getColumnIndex(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE));
                getClazz = cursor.getString(cursor.getColumnIndex(PreferencesSQLiteOpenHelper.SharedProviderColumns.COLUMN_NAME_SHARED_PROVIDER_VALUE_CLASS));
            }
            if (TextUtils.isEmpty(string) || !clazz.equals(getClazz)) {
                cursor.close();
                return defValue;
            }
        }
        if(null != cursor) {
            cursor.close();
        }
        return string;
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
        if(null == cursor){
            return false;
        }
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

}
