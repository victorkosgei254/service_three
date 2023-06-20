package com.example.service_three.repository.services;

import android.os.Handler;
import android.os.Looper;

public class CountWorkManager extends Thread {
    public static CountWorkManager instance;
    Handler handler;

    public static CountWorkManager getInstance()
    {
        if (instance == null)
        {
            instance = new CountWorkManager();
        }

        return instance;
    }
    public void postJob(Runnable job)
    {
        handler.post(job);

    }
    @Override
    public void run() {

        Looper.prepare();
        handler = new Handler();
        Looper.loop();
    }
}
