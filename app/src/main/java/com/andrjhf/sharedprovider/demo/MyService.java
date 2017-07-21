package com.andrjhf.sharedprovider.demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.andrjhf.sharedprovider.SharedProvider;
import com.andrjhf.sharedprovider.SharedProviderImpl;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    public static final String TAG = "MyService";

    private static final String KEY_1 = "key1";
    private static final String KEY_2 = "key2";
    private static final String KEY_3 = "key3";
    private static final String KEY_4 = "key4";
    private static final String KEY_5 = "key5";
    private static final String KEY_6 = "key6";
    private static final String KEY_7 = "key7";
    private static final String KEY_8 = "key8";

    private SharedProvider sharedProvider = null;

    private int count = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedProvider = new SharedProviderImpl(getApplicationContext(),MainActivity.SHARED_NAME);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sharedProvider.edit().putInt(KEY_7, count++);

//                    int keyInt7 = sharedProvider.getInt(KEY_7, -1);
//                    Log.e(TAG, KEY_7 + " : " + keyInt7);
                }
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
