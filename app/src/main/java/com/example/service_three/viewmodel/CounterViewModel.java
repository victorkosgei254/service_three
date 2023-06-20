package com.example.service_three.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.service_three.repository.CounterRepository;

public class CounterViewModel extends AndroidViewModel {
    CounterRepository repository;
    public CounterViewModel(@NonNull Application application) {
        super(application);

        repository = CounterRepository.getInstance(application.getApplicationContext());
    }

    public void startCounter()
    {
            repository.startCounter();
    }

    public LiveData<Integer> getCount()
    {
        return repository.getCount();
    }

    public void startService()
    {
        repository.startService();
    }

    public LiveData<Boolean> isServiceConnected()
    {
        return repository.isServiceBound();
    }

    public LiveData<Boolean> isCounting()
    {
        return repository.getCounterStatus();
    }
}
