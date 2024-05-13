package com.example.proyecto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends ArrayAdapter<Tarea> {
    private Context context;
    private List<Tarea> tareas;

    public TaskAdapter(Context context, List<Tarea> tareas) {
        super(context, 0, tareas);
        this.context = context;
        this.tareas = tareas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Tarea tarea = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvSummary = convertView.findViewById(R.id.tvSummary);
        // Populate the data into the template view using the data object

        tvName.setText(tarea.getCategoria());
        tvSummary.setText(tarea.getDescripcion());
        // Return the completed view to render on screen
        return convertView;
    }
}
