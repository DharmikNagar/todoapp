package com.todoapp.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.todoapp.Del_activity;
import com.todoapp.Fragment.Calendar.CalendarFragment;
import com.todoapp.Fragment.Home.HomeFragment;
import com.todoapp.R;
import com.todoapp.Fragment.Setting.SettingFragment;

public class MainActivity extends AppCompatActivity {

    AppCompatImageView imageView;
    BottomNavigationView bottom_menu;
    FrameLayout fameLayout;
    Fragment HomeFragment,CalendarFragment,SettingFragment;
    String txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        init();
    }

    void init(){


        txt = "Main Activity";

        HomeFragment = new HomeFragment();
        CalendarFragment = new CalendarFragment();
        SettingFragment = new SettingFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fameLayout,HomeFragment)
                .commit();
    }

    void findViewById(){
        imageView = findViewById(R.id.toolPop);
        bottom_menu = findViewById(R.id.bottom_menu);
        fameLayout = findViewById(R.id.fameLayout);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Del_activity.class);
                intent.putExtra("txt",txt);
                startActivity(intent);
            }
        });
        bottom_menu.setSelectedItemId(R.id.task);
        bottom_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(R.id.task == item.getItemId()){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fameLayout,HomeFragment)
                            .commit();
                    return true;
                }else if(R.id.calendar == item.getItemId()){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fameLayout,CalendarFragment)
                            .commit();
                    return true;
                }else if(R.id.setting == item.getItemId()){
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fameLayout,SettingFragment)
                            .commit();
                    return true;
                }
//                handleNavigationItemSelected(item,MainActivity.this);
                return false;
            }
        });
    }

}