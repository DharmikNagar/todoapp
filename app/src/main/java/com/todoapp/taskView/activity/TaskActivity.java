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
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.todoapp.R;
import com.todoapp.common.DBHandler;
import com.todoapp.taskView.adapter.TaskAdapter;
import com.todoapp.taskView.adapter.TaskDateAdapter;
import com.todoapp.taskView.model.DateWiseTaskModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskActivity extends AppCompatActivity implements TaskDateAdapter.OnclickTaskDate{
    AppCompatEditText edtDate,edtTitle,edtSubTitle,edtTiming;
    AppCompatButton submit;
    RecyclerView rcyView;
    AppCompatTextView title;
    AppCompatImageView toolPop;
    DBHandler dbHandler;
    static String id = null;

    ArrayList<DateWiseTaskModel> dateWiseTaskModels,newdateWiseTaskModels ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        refreshVoid();
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
        newdateWiseTaskModels = new ArrayList<>();

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("task_name"));

        try{
            if(dbHandler.readDateTask(intent.getStringExtra("task_id")+"").size()>0){
                dateWiseTaskModels.addAll(dbHandler.readDateTask(intent.getStringExtra("task_id")+""));
            }

            if(dateWiseTaskModels.size()>0){
                adapter();
            }
        }catch (Exception e){

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
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (int i = 0; i < dateWiseTaskModels.size(); i++) {
            String dateCheck = dateWiseTaskModels.get(i).getDate();
            try {
                Date d = df.parse(dateCheck);

                if (d.after(calendar.getTime())) {
                    newdateWiseTaskModels.add(dateWiseTaskModels.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        TaskDateAdapter taskDateAdapter = new TaskDateAdapter(TaskActivity.this, newdateWiseTaskModels,this);
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
        Intent intent = getIntent();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.addDateTask(edtDate.getText()+"",Integer.parseInt(intent.getStringExtra("task_id")+""));
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

    @Override
    public void onTaskAddclick(int position, int id, TaskAdapter taskAdapter) {
        dialogBoxTask(id,taskAdapter);
    }

    void dialogBoxTask(int id, TaskAdapter taskAdapter){
        Dialog dialog = new Dialog(TaskActivity.this);
        dialog.setContentView(R.layout.dialog_add_task);
        edtTitle = dialog.findViewById(R.id.edtTitle);
        edtSubTitle = dialog.findViewById(R.id.edtSubTitle);
        edtTiming = dialog.findViewById(R.id.edtTiming);
        submit = dialog.findViewById(R.id.submit);

        edtTiming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(TaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtTiming.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                        // selectedHour + ":" + selectedMinute
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.addTask(edtTitle.getText()+"",edtSubTitle.getText()+"",edtTiming.getText()+"",id);
                dialog.hide();
                refreshVoid();
            }
        });

        dialog.show();
    }

}