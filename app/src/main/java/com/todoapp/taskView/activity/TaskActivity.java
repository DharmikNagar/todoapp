package com.todoapp.taskView.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.todoapp.R;
import com.todoapp.common.DBHandler;
import com.todoapp.taskView.adapter.TaskDateAdapter;
import com.todoapp.taskView.model.DateWiseTaskModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TaskActivity extends AppCompatActivity implements TaskDateAdapter.OnclickTaskDate {
    AppCompatEditText edtDate;
    AppCompatButton submit;
    RecyclerView rcyView;
    AppCompatTextView title;
    AppCompatImageView toolPop;
    DBHandler dbHandler;
    ArrayList<DateWiseTaskModel> dateWiseTaskModels ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        refreshVoid();

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("task_name"));
    }

    void refreshVoid(){
        findViewById();
        onclick();
    }

    void findViewById(){
        dbHandler = new DBHandler(TaskActivity.this);
        title = findViewById(R.id.title);
        toolPop = findViewById(R.id.toolPop);
        rcyView = findViewById(R.id.rcyView);

        dateWiseTaskModels = new ArrayList<>();

        if(dbHandler.readDateTask().size()>0){
            dateWiseTaskModels.addAll(dbHandler.readDateTask());
        }

        if(dateWiseTaskModels.size()>0){
            adapter();
        }

    }

    void onclick(){
        toolPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup();
            }
        });
    }

    void adapter(){
        TaskDateAdapter taskDateAdapter = new TaskDateAdapter(TaskActivity.this, dateWiseTaskModels,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TaskActivity.this, RecyclerView.VERTICAL, false);
        rcyView.setLayoutManager(linearLayoutManager);
        rcyView.setAdapter(taskDateAdapter);
    }

    void openDialog(){
        Dialog dialog = new Dialog(TaskActivity.this);
        dialog.setContentView(R.layout.dialog_add_datewisetask);
        edtDate = dialog.findViewById(R.id.edtDate);
        submit = dialog.findViewById(R.id.submit);
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker =
                        new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(final DatePicker view, final int year, final int month,
                                                  final int dayOfMonth) {

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                calendar.set(year, month, dayOfMonth);
                                String dateString = sdf.format(calendar.getTime());
                                edtDate.setText(dateString);
                            }
                        }, year, month, day);

                datePicker.show();

                datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(final DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.addDateTask(edtDate.getText()+"");
                dialog.hide();
                refreshVoid();

            }
        });
        dialog.show();
    }

    void popup(){
        PopupMenu popupMenu = new PopupMenu(TaskActivity.this,toolPop);

        popupMenu.getMenuInflater().inflate(R.menu.datewisepopup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.add){
                    openDialog();
                }else{

                }
                return true;
            }
        });
        popupMenu.show();
    }

    @Override
    public void onclick(int position) {

    }
}