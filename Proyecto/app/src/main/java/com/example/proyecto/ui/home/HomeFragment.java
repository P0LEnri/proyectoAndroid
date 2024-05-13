package com.example.proyecto.ui.home;
import com.example.proyecto.CrearTarea;
import com.example.proyecto.SQLiteManager;
import com.example.proyecto.Tarea;
import com.example.proyecto.TaskAdapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        loadFromDBToMemory();
        setTaskAdapter();
        setOnClickListener();
        return root;
    }

    private void setOnClickListener()
    {
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Tarea selectedTask = (Tarea) taskListView.getItemAtPosition(position);
                Intent editTaskIntent = new Intent(getContext(), CrearTarea.class);
                editTaskIntent.putExtra(Tarea.TAREA_EDIT_EXTRA, selectedTask.getId());
                startActivity(editTaskIntent);
            }
        });
    }

    private void loadFromDBToMemory()
    {
        Context context = getContext(); // Obtener el contexto del fragmento
        if (context != null) {
            SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(context);
            Tarea.tareaArrayList.clear(); // Limpiar la lista antes de agregar nuevas tareas
            sqLiteManager.populateTareaListArray();
        }
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