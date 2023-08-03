package com.todoapp.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.todoapp.Fragment.Calendar.CalendarFragment;
import com.todoapp.Fragment.Home.HomeFragment;
import com.todoapp.R;
import com.todoapp.Fragment.Setting.SettingFragment;
import com.todoapp.common.DBHandler;

public class MainActivity extends AppCompatActivity {

    AppCompatImageView imageView;
    BottomNavigationView bottom_menu;
    FrameLayout fameLayout;
    Fragment HomeFragment,CalendarFragment,SettingFragment;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById();
        init();
    }

    void init(){
        dbHandler = new DBHandler(MainActivity.this);

        dbHandler.addTodoTask("Dharmik");

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
                openPop();
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

    void openPop(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,imageView);

        popupMenu.getMenuInflater().inflate(R.menu.toolbar_popup, popupMenu.getMenu());

        popupMenu.show();
    }


}