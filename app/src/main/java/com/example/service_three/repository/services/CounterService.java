package com.example.service_three.repository.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CounterService extends Service {
    private MutableLiveData<Integer> count;
    private MutableLiveData<Boolean> isCounting;
    private CountWorkManager workManager;
    private Binder binder = new CounterServiceBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class CounterServiceBinder extends Binder
    {
        public CounterService getService()
        {
            return CounterService.this;
        }
    }

    @Override
    public void onCreate() {
        Log.d("TTTS", "onRebind: Service Created");
        super.onCreate();
        workManager = CountWorkManager.getInstance();
        count = new MutableLiveData<>(0);
        isCounting = new MutableLiveData<>(false);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("TTTS", "onRebind: Service Start");
        workManager.start();
        return super.onStartCommand(intent, flags, startId);
//        return START_STICKY;
    }

    public LiveData<Integer> getCount()
    {
        return count;
    }

    public void startCounter()
    {

            workManager.postJob(increment_job);
            isCounting.postValue(true);

    }

    public void stopCounter()
    {
        isCounting.setValue(false);
    }

    public String testConnection()
    {
        return "Connection OK!!";
    }
    Runnable increment_job = ()->{
        while (true)
        {
            Integer current = count.getValue();
            count.postValue(current + 1);
            SystemClock.sleep(2000);

        }

    };

    @Override
    public void onRebind(Intent intent) {
        Log.d("TTTS", "onRebind: Service Rebind");
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TTTS", "onDestroy: Service Destroyed");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d("TTTS", "onUnbind: Service Unbounded");
        return super.onUnbind(intent);

    }
}
