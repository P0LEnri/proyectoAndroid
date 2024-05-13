package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el controlador de navegación y el destino actual
                NavController navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment_content_main);
                NavDestination destination = navController.getCurrentDestination();

                if (destination != null) {
                    int id = destination.getId();
                    // Verificar el ID del destino y realizar diferentes acciones
                    //int id = destination.getId();
                    if (id == R.id.nav_home) {
                        // Acción para Home
                        Snackbar.make(view, "Acción en Home", Snackbar.LENGTH_LONG).show();
                        // Crear un intent para iniciar la actividad CrearTarea
                        Intent intent = new Intent(view.getContext(), CrearTarea.class);
                        view.getContext().startActivity(intent);
                    } else if (id == R.id.nav_gallery) {
                        // Acción para Gallery
                        Snackbar.make(view, "Acción en Gallery", Snackbar.LENGTH_LONG).show();
                    } else if (id == R.id.nav_slideshow) {
                        // Acción para Slideshow
                        Snackbar.make(view, "Acción en Slideshow", Snackbar.LENGTH_LONG).show();
                    } else {
                        // Acción por defecto
                        Snackbar.make(view, "a:"+id, Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}