package com.example.service_three.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.service_three.MainActivity;
import com.example.service_three.R;
import com.example.service_three.viewmodel.CounterViewModel;

public class DashboardFragment extends Fragment {
    TextView counterText;
    Button startCount;

    MainActivity mainActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dashboard_layout, container, false);
        counterText = view.findViewById(R.id.counter_text);
        startCount = view.findViewById(R.id.start_counter);

        mainActivity.getViewModel().isCounting().observe(mainActivity,isCounting->{
            if(isCounting)
            {
                mainActivity.getViewModel().getCount().observe(mainActivity, count->{
                    counterText.setText(count.toString());
                });
            }else
            {
                startCount.setOnClickListener(startBtnClickListener);
            }
        });
        return (view);
    }

    View.OnClickListener startBtnClickListener = it->{
        mainActivity.getViewModel().startCounter();
    };
}
