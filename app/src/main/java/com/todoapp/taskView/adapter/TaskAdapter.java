package com.todoapp.taskView.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
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
        if (taskModel.get(position).getStatus() == 0) {
            holder.task_time.setTextColor(Color.RED);
        }else{
            holder.task_time.setBackgroundColor(Color.parseColor("#00FF00"));
        }
        holder.crdView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                holder.task_done.setVisibility(View.VISIBLE);
                return true;
            }
        });

        holder.crdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.task_done.setVisibility(View.GONE);
            }
        });

        holder.task_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return taskModel.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView task_title,task_des,task_time,task_done;
        CardView crdView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_title = itemView.findViewById(R.id.task_title);
            task_des = itemView.findViewById(R.id.task_des);
            task_time = itemView.findViewById(R.id.task_time);
            task_done = itemView.findViewById(R.id.task_done);
            crdView = itemView.findViewById(R.id.crdView);
        }
    }

    public interface setOnTaskListner{
        void clickListner(int position);
    }
}
