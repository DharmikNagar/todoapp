package com.todoapp.Fragment.Home;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.todoapp.Fragment.Home.adpter.Adpter_TodoList;
import com.todoapp.Fragment.Home.model.todolist_model;
import com.todoapp.MainActivity.MainActivity;
import com.todoapp.R;
import com.todoapp.common.DBHandler;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements Adpter_TodoList.OnclicktodoListner{
    View view;
    RecyclerView rcyView;
    AppCompatImageView addTodo;
    DBHandler dbHandler;

    ArrayList<todolist_model> todolistModels;
    ArrayList<String> tsk_name;
    AppCompatEditText edtName;
    AppCompatButton submit;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homefragment, container, false);

        configDatabase();
        findById();
        setAdapter(todolistModels);

        return view;
    }
    void configDatabase(){
        todolistModels = new ArrayList<>();
        dbHandler = new DBHandler(getContext());
        todolistModels = dbHandler.readTodo();
    }

    void findById(){
        rcyView = view.findViewById(R.id.rcyView);
        addTodo = view.findViewById(R.id.addTodo);
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    void openDialog(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_todo);
        edtName = dialog.findViewById(R.id.edtName);
        submit = dialog.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.addTodoTask(edtName.getText()+"");
                configDatabase();
                setAdapter(todolistModels);
                dialog.hide();
            }
        });

        dialog.show();
    }

    void setAdapter(ArrayList<todolist_model> todolistModels){
        Adpter_TodoList adpter_todoList = new Adpter_TodoList(getContext(), todolistModels,this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcyView.setLayoutManager(gridLayoutManager);
        rcyView.setAdapter(adpter_todoList);
    }

    @Override
    public void Onclicktodo(int position) {
        Toast.makeText(getContext(), "position"+position, Toast.LENGTH_SHORT).show();
        todolistModels.remove(position);
        setAdapter(todolistModels);
    }
}