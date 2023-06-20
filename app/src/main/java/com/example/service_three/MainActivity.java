package com.example.service_three;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.service_three.viewmodel.CounterViewModel;
import com.example.service_three.views.DashboardFragment;
import com.example.service_three.views.HelpFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    CounterViewModel viewModel;
    Fragment fragmentContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.main_toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setTitle("Service Counter");
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(navigationItemSelectedListener);
        viewModel = new ViewModelProvider(this).get(CounterViewModel.class);

        viewModel.isServiceConnected().observe(this, isOnline->{
            if (!isOnline)
            {
                viewModel.startService();
            }
        });

    }


    public CounterViewModel getViewModel()
    {
        return viewModel;
    }

    NavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = it->{

        if (it.getItemId() == R.id.menu_home)
        {
            viewInflater(new DashboardFragment(), "counter");
            drawerLayout.closeDrawer(GravityCompat.START);
            return (true);
        }
        else if (it.getItemId() == R.id.menu_help)
        {
            viewInflater(new HelpFragment(), "help");
            drawerLayout.closeDrawer(GravityCompat.START);
            return (true);
        }
        else return false;
    } ;

    void viewInflater(Fragment fragment, String id)
    {
        fragmentContainer = getSupportFragmentManager().findFragmentByTag(id);
        if (fragmentContainer != null)
        {
            Toast.makeText(this, "Fragment re-inflated",Toast.LENGTH_SHORT).show();
            getSupportFragmentManager().beginTransaction()
                    .show(fragmentContainer)
                    .commit();


        }else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frag_container, fragment,id)
                    .addToBackStack("mainactivity"+id)
                    .commit();
        }

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LIFECYCLE", "onPause: On pause called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("LIFECYCLE", "onDestroy: On Destroy called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LIFECYCLE", "onRestart: On Restart called");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LIFECYCLE", "onStart: On start called");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LIFECYCLE", "onStop: On stop called");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Integer count = getSupportFragmentManager().getBackStackEntryCount();
        Toast.makeText(this, "Back pressed "+count, Toast.LENGTH_SHORT).show();
    }
}