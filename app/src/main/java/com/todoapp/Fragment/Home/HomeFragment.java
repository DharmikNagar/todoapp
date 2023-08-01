package com.todoapp.Fragment.Home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.todoapp.Fragment.Home.adpter.Adpter_TodoList;
import com.todoapp.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements Adpter_TodoList.OnclicktodoListner{
    View view;
    RecyclerView rcyView;
    ArrayList<String> tsk_name;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_homefragment, container, false);
        tsk_name = new ArrayList<>();
        for(int i=0;i<3;i++){
            tsk_name.add("Task "+i);

        }
        findById();
        setAdapter(tsk_name);

        return view;
    }

    void findById(){
        rcyView = view.findViewById(R.id.rcyView);
    }

    void setAdapter(ArrayList<String> tsk_name){
        Adpter_TodoList adpter_todoList = new Adpter_TodoList(getContext(), tsk_name,this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcyView.setLayoutManager(gridLayoutManager);
        rcyView.setAdapter(adpter_todoList);
    }

    @Override
    public void Onclicktodo(int position) {
        Toast.makeText(getContext(), "position"+position, Toast.LENGTH_SHORT).show();
        tsk_name.remove(position);
        setAdapter(tsk_name);
    }
}