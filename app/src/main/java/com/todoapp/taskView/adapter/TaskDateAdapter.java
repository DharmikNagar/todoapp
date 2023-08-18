package com.todoapp.taskView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.todoapp.R;
import com.todoapp.taskView.model.DateWiseTaskModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskDateAdapter extends RecyclerView.Adapter<TaskDateAdapter.ViewHolder> {
    Context context;
    OnclickTaskDate onclickTaskDate;
    ArrayList<DateWiseTaskModel> dateWiseTaskModels;
    public TaskDateAdapter(Context context, ArrayList<DateWiseTaskModel> dateWiseTaskModels, OnclickTaskDate onclickTaskDate) {
        this.context = context;
        this.onclickTaskDate = onclickTaskDate;
        this.dateWiseTaskModels = dateWiseTaskModels;
    }

    @NonNull
    @Override
    public TaskDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_head_list, parent, false);
        return new TaskDateAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskDateAdapter.ViewHolder holder, int position) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date today = Calendar.getInstance().getTime();
        String todayDate = df.format(today);

        Date tomorrow = calendar.getTime();
        String tomorrowDate = df.format(tomorrow);

        if(todayDate.equals(dateWiseTaskModels.get(position).getDate())){
            holder.sample_title.setText("Today");
        }else if(tomorrowDate.equals(dateWiseTaskModels.get(position).getDate())){
            holder.sample_title.setText("Tomorrow");
        }else{
            holder.sample_title.setText(dateWiseTaskModels.get(position).getDate());
        }

        holder.add_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickTaskDate.onTaskAddclick(position,dateWiseTaskModels.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateWiseTaskModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView sample_title;
        AppCompatImageView add_header;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sample_title = itemView.findViewById(R.id.sample_title);
            add_header = itemView.findViewById(R.id.add_header);
        }
    }

    public interface OnclickTaskDate{
        void onclick(int position);
        void onTaskAddclick(int position,int id);
    }
}
