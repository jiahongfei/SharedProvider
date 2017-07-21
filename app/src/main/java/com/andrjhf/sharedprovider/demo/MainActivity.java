package com.andrjhf.sharedprovider.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.andrjhf.sharedprovider.SharedProvider;
import com.andrjhf.sharedprovider.SharedProviderImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    public static final String TAG = "MainActivity";

    public static final String SHARED_NAME = "sharedName1";
    public static final String SHARED_NAME2 = "sharedName2";

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";
    private static final String KEY_3 = "key3";
    private static final String KEY_4 = "key4";
    private static final String KEY_5 = "key5";
    private static final String KEY_6 = "key6";
    private static final String KEY_7 = "key7";
    private static final String KEY_8 = "key8";
    private static final String KEY_9 = "key9";
    private static final String KEY_10 = "key10";
    private static final String KEY_11 = "key11";
    private static final String KEY_12 = "key12";
    private static final String KEY_13 = "key13";

    private SharedProvider sharedProvider;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedProvider = new SharedProviderImpl(getApplicationContext(), SHARED_NAME);
        sharedProvider.edit().putString(KEY_1,"jiahongfei_value00000");
        sharedProvider.edit().putString(KEY_2,"jiahongfei_value111111");
        sharedProvider.edit().putInt(KEY_3,100);
        sharedProvider.edit().putBoolean(KEY_4,false);
        sharedProvider.edit().putLong(KEY_5,3000000L);
        sharedProvider.edit().putFloat(KEY_6,66.88f);
        sharedProvider.edit().putString(KEY_6,"keylalalalla");
        List<TestObject> testObjectList = new ArrayList<>();
        TestObject testObject = new TestObject();
        testObject.setTitle("标题");
        testObject.setDesc("我是一个简介");
        testObject.setImg("http://www.baidu.com/");
        testObject.setUrl("http://www.google.com/");
        testObjectList.add(testObject);
        testObject = new TestObject();
        testObject.setTitle("标题1");
        testObject.setDesc("我是一个简介1");
        testObject.setImg("http://www.baidu.com/1");
        testObject.setUrl("http://www.google.com/1");
        testObjectList.add(testObject);
        sharedProvider.edit().putObject(KEY_9,testObject, TestObject.class.getName());
//        sharedProvider.edit().putObject(KEY_10, testObjectList, List.class.getName());
        sharedProvider.edit().putList(KEY_11,testObjectList);
        TestObject tmpTestObject = sharedProvider.getObject(KEY_9,TestObject.class.getName());
        List<TestObject> testObjectList1 = sharedProvider.getList(KEY_11);
//        List<TestObject> tmpTestObjects = sharedProvider.getObject(KEY_10,List.class.getName());
//        Log.e(TAG,tmpTestObjects.toString());

        Map<TestObject,TestObjectV> mapObject = new HashMap<>();
        TestObjectV testObjectV = new TestObjectV();
        testObjectV.setUrl("urlurl");
        testObjectV.setImg("img");
        testObjectV.setDesc("desc");
        testObjectV.setTitle("title");
        mapObject.put(testObject,testObjectV);

        SharedProvider sharedProvider2 = new SharedProviderImpl(getApplicationContext(), SHARED_NAME2);
        sharedProvider2.edit().putString(KEY_1, "shared2222222222");
        sharedProvider2.edit().putInt(KEY_2, 300);
        sharedProvider2.edit().putFloat(KEY_3, 2.33f);
        testObject = new TestObject();
        testObject.setTitle("标题");
        testObject.setDesc("我是一个简介");
        testObject.setImg("http://www.baidu.com/");
        testObject.setUrl("http://www.google.com/");
        sharedProvider2.edit().putObject(KEY_4, testObject,TestObject.class.getName());

        sharedProvider2.edit().putMap(KEY_12,mapObject);

        sharedProvider2.getMap(KEY_12);

        Set<TestObjectV> set = new HashSet<>();
        set.add(testObjectV);
        sharedProvider2.edit().putSet(KEY_13, set);

        sharedProvider2.getSet(KEY_13);

//        sharedProvider.edit().remove(KEY_1);

        Map<String, ?> map = sharedProvider.getAll();
        Map<String, ?> map1 = sharedProvider2.getAll();

//        sharedProvider.edit().clear();

        Intent intent = new Intent(MainActivity.this, MyService.class);
        startService(intent);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    int keyInt7 = sharedProvider.getInt(KEY_7, -1);
                    Log.e(TAG, KEY_7 + " : " + keyInt7);
//                    sharedProvider.edit().putInt(KEY_7, count++);
                }
            }
        }).start();
    }

    public void onClick(View view) {
        sharedProvider.edit().putString(KEY_8, "sdfsdfsdfsdf");
        Toast.makeText(this, KEY_8, Toast.LENGTH_SHORT).show();
    }
}
