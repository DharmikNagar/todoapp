package com.todoapp.Fragment.Home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.todoapp.Fragment.Home.adpter.Adpter_TodoList;
import com.todoapp.Fragment.Home.model.todolist_model;
import com.todoapp.MainActivity.MainActivity;
import com.todoapp.R;
import com.todoapp.common.DBHandler;
import com.todoapp.taskView.activity.TaskActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements Adpter_TodoList.OnclicktodoListner{
    View view;
    RecyclerView rcyView;
    AppCompatTextView title;
    AppCompatImageView addTodo,toolPop,editIcon,delIcon;
    DBHandler dbHandler;

    ArrayList<todolist_model> todolistModels;
    AppCompatEditText edtName;
    AppCompatButton submit;
    RelativeLayout toolbar;
    public HomeFragment() {
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
        toolPop = getActivity().findViewById(R.id.toolPop);
        editIcon = getActivity().findViewById(R.id.editIcon);
        delIcon = getActivity().findViewById(R.id.delIcon);

        toolbar = getActivity().findViewById(R.id.toolbar);
        title = getActivity().findViewById(R.id.title);
        toolPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPop();
            }
        });
        rcyView = view.findViewById(R.id.rcyView);
        addTodo = view.findViewById(R.id.addTodo);
//        addTodo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDialog();
//            }
//        });
    }

    void openDialog(String title_,String id){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_todo);
        edtName = dialog.findViewById(R.id.edtName);
        submit = dialog.findViewById(R.id.submit);

        if(!id.isEmpty()){
            edtName.setText(title_);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!id.isEmpty()){
                    dbHandler.updateTodo(edtName.getText()+"",id);
                    configDatabase();
                    setAdapter(todolistModels);
                    dialog.hide();

                    title.setText("My Lists");
                    toolPop.setVisibility(View.VISIBLE);
                    editIcon.setVisibility(View.GONE);
                    delIcon.setVisibility(View.GONE);
                }else{
                    dbHandler.addTodoTask(edtName.getText()+"");
                    configDatabase();
                    setAdapter(todolistModels);
                    dialog.hide();
                }
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
        title.setText("Edit List");
        toolPop.setVisibility(View.GONE);
        editIcon.setVisibility(View.VISIBLE);
        delIcon.setVisibility(View.VISIBLE);
        delIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title.setText("My Lists");
                toolPop.setVisibility(View.VISIBLE);
                editIcon.setVisibility(View.GONE);
                delIcon.setVisibility(View.GONE);


                dbHandler.deleteTodo(""+todolistModels.get(position).getId());
//                todolistModels.remove(position);
                configDatabase();
                setAdapter(todolistModels);

            }
        });

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(""+todolistModels.get(position).getTitle(),""+todolistModels.get(position).getId());

            }
        });
    }

    @Override
    public void OnclickVisible(int position) {
        title.setText("My Lists");
        toolPop.setVisibility(View.VISIBLE);
        editIcon.setVisibility(View.GONE);
        delIcon.setVisibility(View.GONE);

        Intent intent = new Intent(getActivity(), TaskActivity.class);
        intent.putExtra("task_name",""+todolistModels.get(position).getTitle());
        startActivity(intent);
    }

    void openPop(){
        PopupMenu popupMenu = new PopupMenu(getContext(),toolPop);

        popupMenu.getMenuInflater().inflate(R.menu.toolbar_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                openDialog("","");
                return true;
            }
        });
        popupMenu.show();
    }
}