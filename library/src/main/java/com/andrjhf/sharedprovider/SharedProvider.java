package com.andrjhf.sharedprovider;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jiahongfei on 2017/7/17.
 */

public interface SharedProvider {


    public interface Editor {

        SharedProvider.Editor putString(String key, String value);

        SharedProvider.Editor putInt(String key, int value);

        SharedProvider.Editor putLong(String key, long value);

        SharedProvider.Editor putFloat(String key, float value);

        SharedProvider.Editor putBoolean(String key, boolean value);

        <T> SharedProvider.Editor putObject(String key, T t, String clazzName);

        <T> SharedProvider.Editor putList(String key, List<T> t);

        <K,V> SharedProvider.Editor putMap(String key, Map<K, V> map);

        <E> SharedProvider.Editor putSet(String key, Set<E> set);

        SharedProvider.Editor remove(String key);

        SharedProvider.Editor clear();

    }

    Map<String, ?> getAll();

    String getString(String key, String defValue);

    int getInt(String key, int defValue);

    long getLong(String key, long defValue);

    float getFloat(String key, float defValue);

    boolean getBoolean(String key, boolean defValue);


    <T> T getObject(String key, String clazzName);

    <T> List<T> getList(String key);

    <K,V> Map<K, V> getMap(String key);

    <E> Set<E> getSet(String key);

    boolean contains(String key);

    SharedProvider.Editor edit();

}
