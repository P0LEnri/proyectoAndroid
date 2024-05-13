package com.example.proyecto;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class ModificarCategoriaFragment extends Fragment {

    private RecyclerView recyclerViewCategorias;
    private CategoriaAdapter categoriaAdapter;
    private List<Categoria> categorias = new ArrayList<>();

    // Este método se llama al crear la vista del fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View rootView = inflater.inflate(R.layout.fragment_modificar_categoria, container, false);

        // Referenciar el RecyclerView y configurar el adaptador
        recyclerViewCategorias = rootView.findViewById(R.id.recycler_view_categorias);

        categoriaAdapter = new CategoriaAdapter(obtenerCategorias(), new CategoriaAdapter.OnCategoriaClickListener() {
            @Override
            public void onCategoriaClick(int position) {
                // Lógica para manejar el clic en una categoría
                editarNombreCategoria(position);
            }
        });
        recyclerViewCategorias.setAdapter(categoriaAdapter);
        recyclerViewCategorias.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Configurar el botón para agregar una nueva categoría
        Button botonAgregarCategoria = rootView.findViewById(R.id.boton_agregar_categoria);
        botonAgregarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lógica para agregar una nueva categoría
                agregarNuevaCategoria();
            }
        });

        categoriaAdapter = new CategoriaAdapter(obtenerCategorias(), new CategoriaAdapter.OnCategoriaClickListener() {
            @Override
            public void onCategoriaClick(int position) {
                // Lógica para manejar el clic en una categoría
                editarNombreCategoria(position);
            }
        });

        return rootView;
    }

    // Método para obtener las categorías (simulado)
    private List<Categoria> obtenerCategorias() {
        // Aquí podrías obtener las categorías de una base de datos u otra fuente de datos
        // Por ahora, simplemente devolvemos una lista simulada de categorías

        categorias.add(new Categoria("No tan importante"));
        categorias.add(new Categoria("Urgente"));
        categorias.add(new Categoria("Importante"));
        return categorias;
    }

    private void agregarNuevaCategoria() {
        // Aquí puedes abrir un cuadro de diálogo, una actividad o un fragmento para que el usuario ingrese los detalles de la nueva categoría

        // Por ejemplo, puedes abrir un cuadro de diálogo para que el usuario ingrese el nombre de la nueva categoría
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Agregar Nueva Categoría");

        // Definir un EditText en el cuadro de diálogo para que el usuario ingrese el nombre de la categoría
        final EditText editTextNombreCategoria = new EditText(getActivity());
        builder.setView(editTextNombreCategoria);

        // Definir botones "Aceptar" y "Cancelar" en el cuadro de diálogo
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtener el nombre de la categoría ingresado por el usuario
                String nombreCategoria = editTextNombreCategoria.getText().toString();

                // Aquí puedes guardar la nueva categoría en tu base de datos o donde prefieras almacenarla

                // Por ejemplo, agregar la nueva categoría a tu lista de categorías existente y notificar al adaptador que se ha agregado un nuevo elemento
                Categoria nuevaCategoria = new Categoria(nombreCategoria);
                categorias.add(nuevaCategoria);
                categoriaAdapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); // Cerrar el cuadro de diálogo si el usuario hace clic en "Cancelar"
            }
        });

        // Mostrar el cuadro de diálogo
        builder.show();
    }
    // Método para editar el nombre de una categoría
    private void editarNombreCategoria(int position) {
        // Obtener la categoría seleccionada
        final Categoria categoriaSeleccionada = categorias.get(position);

        // Crear un cuadro de diálogo de edición de nombre de categoría
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Editar Nombre de Categoría");

        // Crear un EditText en el cuadro de diálogo para que el usuario ingrese el nuevo nombre de la categoría
        final EditText editTextNuevoNombre = new EditText(getActivity());
        editTextNuevoNombre.setText(categoriaSeleccionada.getNombre()); // Establecer el nombre actual de la categoría como texto inicial
        builder.setView(editTextNuevoNombre);

        // Definir el botón "Aceptar" en el cuadro de diálogo
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Obtener el nuevo nombre de la categoría ingresado por el usuario
                String nuevoNombre = editTextNuevoNombre.getText().toString();

                // Actualizar el nombre de la categoría en tus datos (por ejemplo, en tu lista de categorías)
                categoriaSeleccionada.setNombre(nuevoNombre);

                // Notificar al adaptador que se ha producido un cambio en los datos
                categoriaAdapter.notifyItemChanged(position);
            }
        });

        // Definir el botón "Cancelar" en el cuadro de diálogo
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cerrar el cuadro de diálogo si el usuario hace clic en "Cancelar"
                dialog.cancel();
            }
        });

        // Mostrar el cuadro de diálogo
        builder.show();
    }
}