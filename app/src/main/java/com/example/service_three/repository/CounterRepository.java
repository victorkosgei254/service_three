package com.example.service_three.repository;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.service_three.repository.services.CounterService;

public class CounterRepository implements ServiceConnection {
    Context context;
    CounterService counterService;
    private static CounterRepository instance;
    CounterService.CounterServiceBinder binder;
    Intent serviceIntent;
    MutableLiveData<Integer> count;
    private MutableLiveData<Boolean> isBound;
    private MutableLiveData<Boolean> isCounting;

    private CounterRepository(Context ctx)
    {
        context = ctx;
        isBound = new MutableLiveData<>(false);
        isCounting = new MutableLiveData<>(false);

    }
    public static CounterRepository getInstance(Context ctx)
    {
        if (instance == null)
        {
            instance = new CounterRepository(ctx);
        }

        return instance;
    }

    public void startService()
    {

        serviceIntent = new Intent(context, CounterService.class);

        Log.d("TTTS", "startService: Service is was NOT FOUND, new created");
        context.startService(serviceIntent);
        
        bindService();

    }

    public void bindService()
    {
        context.bindService(serviceIntent, this, Context.BIND_AUTO_CREATE);
        isBound.setValue(true);
        Log.d("TTTS", "bindService: Service is Bound");
    }

    public LiveData<Boolean> isServiceBound()
    {
        if (counterService == null)
        {
            isBound.setValue(false);
        }

        return isBound;
    }

    public void startCounter()
    {
        binder.getService().startCounter();
        isCounting.setValue(true);
    }

    public LiveData<Integer> getCount()
    {
        return counterService.getCount();
    }

    public LiveData<Boolean> getCounterStatus()
    {
        return isCounting;
    }


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        binder = (CounterService.CounterServiceBinder) service;
        counterService = binder.getService();
        isBound.setValue(true);


    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
