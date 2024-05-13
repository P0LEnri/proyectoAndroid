package com.example.proyecto;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.CategoriaViewHolder> {

    private List<Categoria> categorias;
    private OnCategoriaClickListener listener;

    // Define una interfaz para manejar los clics en los elementos del RecyclerView
    public interface OnCategoriaClickListener {
        void onCategoriaClick(int position);
    }

    public CategoriaAdapter(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public CategoriaAdapter(List<Categoria> categorias, OnCategoriaClickListener listener) {
        this.categorias = categorias;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_categoria, parent, false);
        return new CategoriaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaViewHolder holder, final int position) {
        Categoria categoria = categorias.get(position);
        holder.bind(categoria);

        // Asigna el OnClickListener al elemento de la vista (nombre de la categoría)
        holder.textViewNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Cuando se haga clic en el nombre de la categoría, llama al método onCategoriaClick del listener
                if (listener != null) {
                    listener.onCategoriaClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;

        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.text_view_nombre);
        }

        public void bind(Categoria categoria) {
            textViewNombre.setText(categoria.getNombre());
        }
    }


}
