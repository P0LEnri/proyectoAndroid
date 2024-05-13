package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrearTarea extends AppCompatActivity {
    EditText editTextDescripcion;
    EditText editTextCategoria;
    CalendarView calendarViewFecha;
    TimePicker timePickerHora;
    RadioGroup radioGroupRecordatorio;
    Button buttonGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);

        // Inicializar las referencias a los componentes
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextCategoria = findViewById(R.id.editTextCategoria);
        calendarViewFecha = findViewById(R.id.calendarViewFecha);
        timePickerHora = findViewById(R.id.timePickerHora);
        radioGroupRecordatorio = findViewById(R.id.radioGroupRecordatorio);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        // Configurar el listener para el botón Guardar
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Recolectar datos de los controles de entrada
                String descripcion = editTextDescripcion.getText().toString();
                String categoria = editTextCategoria.getText().toString();
                String hora = String.format("%02d:%02d", timePickerHora.getCurrentHour(), timePickerHora.getCurrentMinute());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String fecha = sdf.format(new Date(calendarViewFecha.getDate()));
                RadioButton selectedRadioButton = findViewById(radioGroupRecordatorio.getCheckedRadioButtonId());
                String recordatorio = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "sin_recordatorio";
                String nombreContacto = "Contacto"; // Suponer que esto se obtendrá de alguna manera
                String estado = "Pendiente"; // Suponer que esto se seleccionará de alguna manera
                int notificacion = 1; // Supongamos que siempre quieres notificación, ajusta según tu lógica

                // Llamar al método para agregar la tarea a la base de datos
                //SQLiteManager.instanceOfDatabase(getApplicationContext()).addTarea(nombreContacto, descripcion, hora, fecha, estado, recordatorio, categoria, notificacion);
                int id = Tarea.tareaArrayList.size();
                Tarea  newTask = new Tarea(id,nombreContacto,descripcion,hora,fecha,estado,recordatorio,categoria,notificacion);
                Tarea.tareaArrayList.add(newTask);

                // Mostrar un mensaje de confirmación o cerrar la actividad
                Toast.makeText(getApplicationContext(), descripcion+categoria+hora+fecha+recordatorio+nombreContacto+estado+notificacion, Toast.LENGTH_SHORT).show();
                finish(); // Opcional: cerrar la actividad
            }
        });
    }


}