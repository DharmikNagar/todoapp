package com.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Del_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del);

        Intent intent = getIntent();
        Toast.makeText(this, ""+intent.getStringExtra("txt"), Toast.LENGTH_SHORT).show();

    }
}