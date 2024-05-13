package com.example.proyecto.ui.home;
import com.example.proyecto.Tarea;
import com.example.proyecto.TaskAdapter;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyecto.databinding.FragmentHomeBinding;

public class    HomeFragment extends Fragment {
    ListView taskListView;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initWidgets();
        setTaskAdapter();
        return root;
    }


    private void initWidgets()
    {
        taskListView = binding.taskListView;
    }
    private void setTaskAdapter()
    {
        TaskAdapter taskAdapter = new TaskAdapter(getContext(), Tarea.tareaArrayList);
        taskListView.setAdapter(taskAdapter);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}