package com.todoapp.taskView.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.todoapp.R;
import com.todoapp.taskView.model.TaskModel;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{
    Context context;
    ArrayList<TaskModel> taskModel;
    public TaskAdapter(Context context, ArrayList<TaskModel> taskModel) {
        this.context = context;
        this.taskModel = taskModel;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list, parent, false);
        return new TaskAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        holder.task_title.setText(taskModel.get(position).getTitle());
        holder.task_des.setText(taskModel.get(position).getSUBTitle());
        holder.task_time.setText(taskModel.get(position).getTiming());
    }

    @Override
    public int getItemCount() {
        return taskModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView task_title,task_des,task_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_title = itemView.findViewById(R.id.task_title);
            task_des = itemView.findViewById(R.id.task_des);
            task_time = itemView.findViewById(R.id.task_time);
        }
    }
}
