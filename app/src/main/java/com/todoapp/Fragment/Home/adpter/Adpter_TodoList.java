package com.todoapp.Fragment.Home.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.todoapp.Fragment.Home.model.todolist_model;
import com.todoapp.R;

import java.util.ArrayList;

public class Adpter_TodoList extends RecyclerView.Adapter<Adpter_TodoList.ViewHolder> {
    Context context;
    ArrayList<todolist_model> todolistModels;
    OnclicktodoListner onclicktodoListner;
    public Adpter_TodoList(Context context, ArrayList<todolist_model> todolistModels, OnclicktodoListner onclicktodoListner) {
        this.context = context;
        this.todolistModels = todolistModels;
        this.onclicktodoListner = onclicktodoListner;
    }

    @NonNull
    @Override
    public Adpter_TodoList.ViewHolder onCreateViewHolder(@NonNull ViewGroup par, int viewType) {
        View view = LayoutInflater.from(par.getContext()).inflate(R.layout.res_todo_front, par, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adpter_TodoList.ViewHolder holder, int position) {
        holder.task_name.setText(todolistModels.get(position).getTitle());
        holder.lnLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclicktodoListner.Onclicktodo(position);
                Toast.makeText(context, "click"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return todolistModels.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView task_name;
        LinearLayoutCompat lnLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_name = itemView.findViewById(R.id.task_name);
            lnLayout = itemView.findViewById(R.id.lnLayout);
        }
    }

    public interface OnclicktodoListner{
        void Onclicktodo(int position);
    }
}
