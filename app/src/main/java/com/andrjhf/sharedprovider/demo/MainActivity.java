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
    private SharedProvider sharedProvider2;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ArticleK> articleKList = new ArrayList<>();
        Map<ArticleK, ArticleV> kArticleVMap = new HashMap<>();
        Set<ArticleK> articleKSet = new HashSet<>();

        ArticleK articleK = new ArticleK();
        articleK.setTitle("标题");
        articleK.setDesc("我是一个简介");
        articleK.setImg("http://www.baidu.com/");
        articleK.setUrl("http://www.google.com/");
        articleKList.add(articleK);

        articleK = new ArticleK();
        articleK.setTitle("标题11111");
        articleK.setDesc("我是一个简介11111");
        articleK.setImg("http://www.baidu.com/11111");
        articleK.setUrl("http://www.google.com/11111");
        articleKList.add(articleK);

        ArticleV articleV = new ArticleV();
        articleK.setTitle("标题vvvvv");
        articleK.setDesc("我是一个简介vvvvv");
        articleK.setImg("http://www.baidu.com/vvvvv");
        articleK.setUrl("http://www.google.com/vvvvv");

        kArticleVMap.put(articleK ,articleV);

        articleKSet.add(articleK);

        sharedProvider = new SharedProviderImpl(getApplicationContext(), SHARED_NAME);
        sharedProvider.edit().putString(KEY_1, "value1");
        sharedProvider.edit().putInt(KEY_2, 100);
        sharedProvider.edit().putLong(KEY_3, 100000000L);
        sharedProvider.edit().putFloat(KEY_4, 66.66F);
        sharedProvider.edit().putBoolean(KEY_5, false);
        sharedProvider.edit().putObject(KEY_6, articleK, ArticleK.class.getName());
        sharedProvider.edit().putList(KEY_7, articleKList);
        sharedProvider.edit().putMap(KEY_8, kArticleVMap);
        sharedProvider.edit().putSet(KEY_9, articleKSet);
        sharedProvider.edit().putString(KEY_10, "value10");

        sharedProvider.edit().remove(KEY_10);

//        sharedProvider.edit().clear();


        sharedProvider2 = new SharedProviderImpl(getApplicationContext(), SHARED_NAME2);
        sharedProvider2.edit().putString(KEY_1, "value1");
        sharedProvider2.edit().putInt(KEY_2, 100);
        sharedProvider2.edit().putLong(KEY_3, 100000000L);
        sharedProvider2.edit().putFloat(KEY_4, 66.66F);
        sharedProvider2.edit().putBoolean(KEY_5, true);
        sharedProvider2.edit().putObject(KEY_6, articleK, ArticleK.class.getName());
        sharedProvider2.edit().putList(KEY_7, articleKList);
        sharedProvider2.edit().putMap(KEY_8, kArticleVMap);
        sharedProvider2.edit().putSet(KEY_9, articleKSet);
        sharedProvider2.edit().putString(KEY_10, "value10");

        Log.e(TAG, KEY_1 + " : " + sharedProvider2.getString(KEY_1,"default_key_1"));
        Log.e(TAG, KEY_2 + " : " + sharedProvider2.getInt(KEY_2,-100));
        Log.e(TAG, KEY_3 + " : " + sharedProvider2.getLong(KEY_3,-1000L));
        Log.e(TAG, KEY_4 + " : " + sharedProvider2.getFloat(KEY_4,-55.55F));
        Log.e(TAG, KEY_5 + " : " + sharedProvider2.getBoolean(KEY_5,false));
        Log.e(TAG, KEY_6 + " : " + sharedProvider2.getObject(KEY_6, ArticleK.class.getName()));
        Log.e(TAG, KEY_7 + " : " + sharedProvider2.getList(KEY_7));
        Log.e(TAG, KEY_8 + " : " + sharedProvider2.getMap(KEY_8));
        Log.e(TAG, KEY_9 + " : " + sharedProvider2.getSet(KEY_9));
        Log.e(TAG, KEY_10 + " : " + sharedProvider2.getString(KEY_10,"default10"));
        Log.e(TAG, "ALL" + " : " + sharedProvider2.getAll());


        //如果获取类型错误会返回默认值，没有类型转换
        Log.e(TAG, "Default " + KEY_2 + " : " + sharedProvider2.getLong(KEY_2,-1000L));
        Log.e(TAG, "Default " + KEY_3 + " : " + sharedProvider2.getInt(KEY_3,-100));

        sharedProvider2.contains(KEY_1);

//        Intent intent = new Intent(MainActivity.this, MyService.class);
//        startService(intent);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        TimeUnit.MILLISECONDS.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    int keyInt7 = sharedProvider.getInt(KEY_7, -1);
//                    Log.e(TAG, KEY_7 + " : " + keyInt7);
////                    sharedProvider.edit().putInt(KEY_7, count++);
//                }
//            }
//        }).start();
    }

    public void onClick(View view) {
        sharedProvider.edit().putString(KEY_8, "sdfsdfsdfsdf");
        Toast.makeText(this, KEY_8, Toast.LENGTH_SHORT).show();
    }
}
