package com.example.proyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
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
    Tarea selectedNote;

    String fechaSeleccionada;

    Button buttonSeleccionarContacto; // Botón para seleccionar el contacto
    EditText editTextNombreContacto; // Agregar EditText para mostrar el nombre del contacto seleccionado
    EditText editTextEstado;
    private static final int REQUEST_SELECT_CONTACT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea);



        initWidgets();

        // Configurar el listener para la selección de fecha en el CalendarView
        calendarViewFecha.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // Actualizar la fecha seleccionada cuando el usuario selecciona una nueva fecha
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                fechaSeleccionada = sdf.format(new Date(year - 1900, month, dayOfMonth));
            }
        });

        // Configurar el listener para el botón Seleccionar Contacto
        buttonSeleccionarContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_SELECT_CONTACT);
            }
        });

        checkForEditTask();




        // Configurar el listener para el botón Guardar
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteManager sqLiteManager = SQLiteManager.instanceOfDatabase(CrearTarea.this);



                // Recolectar datos de los controles de entrada
                String descripcion = editTextDescripcion.getText().toString();
                String categoria = editTextCategoria.getText().toString();

                String hora = String.format("%02d:%02d", timePickerHora.getCurrentHour(), timePickerHora.getCurrentMinute());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                //String fecha = sdf.format(new Date(calendarViewFecha.getDate()));
                RadioButton selectedRadioButton = findViewById(radioGroupRecordatorio.getCheckedRadioButtonId());
                String recordatorio = selectedRadioButton != null ? selectedRadioButton.getText().toString() : "sin_recordatorio";
                String nombreContacto = editTextNombreContacto.getText().toString();//"Contacto"; // Suponer que esto se obtendrá de alguna manera
                String estado = editTextEstado.getText().toString();//"Pendiente"; // Suponer que esto se seleccionará de alguna manera
                int notificacion = 1; // Supongamos que siempre quieres notificación, ajusta según tu lógica

                // Usar la fecha seleccionada o la fecha actual si no se seleccionó ninguna
                String fecha = fechaSeleccionada != null ? fechaSeleccionada : new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(calendarViewFecha.getDate()));


                if (selectedNote == null)
                {
                    int id = Tarea.tareaArrayList.size();
                    Tarea  newTask = new Tarea(id,nombreContacto,descripcion,hora,fecha,estado,recordatorio,categoria,notificacion);
                    Tarea.tareaArrayList.add(newTask);
                    sqLiteManager.addTarea(newTask);
                    // Mostrar un mensaje de confirmación o cerrar la actividad
                    Toast.makeText(getApplicationContext(), descripcion+categoria+hora+fecha+recordatorio+nombreContacto+estado+notificacion, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    selectedNote.setCategoria(categoria);
                    selectedNote.setDescripcion(descripcion);
                    selectedNote.setHora(hora);
                    selectedNote.setFecha(fecha);
                    selectedNote.setNombreContacto(nombreContacto);
                    selectedNote.setEstado(estado);
                    selectedNote.setNotificacion(notificacion);
                    selectedNote.setRecordatorio(recordatorio);
                    sqLiteManager.updateTareaInDB(selectedNote);
                }
                finish(); // Opcional: cerrar la actividad
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK && data != null) {
            Uri contactUri = data.getData();
            String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

            Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String contactName = cursor.getString(nameIndex);
                editTextNombreContacto.setText(contactName);
                cursor.close();
            }
        }
    }

    private void checkForEditTask() {

        Intent previousIntent = getIntent();

        int passedNoteID = previousIntent.getIntExtra(Tarea.TAREA_EDIT_EXTRA, -1);
        selectedNote = Tarea.getNoteForID(passedNoteID);

        if (selectedNote != null)
        {
            // Establecer la descripción y la categoría
            editTextDescripcion.setText(selectedNote.getDescripcion());
            editTextCategoria.setText(selectedNote.getCategoria());
            editTextNombreContacto.setText(selectedNote.getNombreContacto());
            editTextEstado.setText(selectedNote.getEstado());


            // Establecer la fecha en el CalendarView
            String fechaString = selectedNote.getFecha();
            Toast.makeText(getApplicationContext(), "fecha"+fechaString, Toast.LENGTH_LONG).show();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            try {
                Date date = sdf.parse(fechaString);
                //long dateInMillis = date.getTime();
                calendarViewFecha.setDate(date.getTime(), true, true);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error fecha jeje", Toast.LENGTH_SHORT).show();

            }


            // Establecer la hora en el TimePicker
            // Suponiendo que tienes la hora como un string en formato "HH:mm"
            String[] time = selectedNote.getHora().split(":");
            int hour = Integer.parseInt(time[0]);
            int minute = Integer.parseInt(time[1]);
            timePickerHora.setHour(hour);
            timePickerHora.setMinute(minute);


            // Establecer el recordatorio en el RadioGroup
            // Suponiendo que tienes un método para obtener el ID del RadioButton correspondiente
            int radioButtonId = getRadioButtonIdForRecordatorio(selectedNote.getRecordatorio());
            radioGroupRecordatorio.check(radioButtonId);
        }
        else
        {


            //deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    private int getRadioButtonIdForRecordatorio(String recordatorio) {

        // Implementar este método según cómo tienes configurados tus RadioButtons
        // Por ejemplo, si tienes RadioButtons con textos "None", "5 minutes before", etc.
        switch (recordatorio) {
            case "sin_recordatorio":
                return R.id.radioButtonSin;
            case "diez_min_antes":
                return R.id.radioButton10Min;
            case "un_dia_antes":
                return R.id.radioButton1Dia;
            // Añadir más casos según sea necesario
            default:
                return -1; // O algún ID por defecto
        }
    }

    private void initWidgets() {
        // Inicializar las referencias a los componentes
        editTextDescripcion = findViewById(R.id.editTextDescripcion);
        editTextCategoria = findViewById(R.id.editTextCategoria);
        calendarViewFecha = findViewById(R.id.calendarViewFecha);
        timePickerHora = findViewById(R.id.timePickerHora);
        radioGroupRecordatorio = findViewById(R.id.radioGroupRecordatorio);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        editTextNombreContacto = findViewById(R.id.editTextNombreContacto); // Inicializar el EditText para el nombre del contacto
        buttonSeleccionarContacto = findViewById(R.id.buttonSeleccionarContacto); // Inicializar el botón para seleccionar contacto
        editTextEstado = findViewById(R.id.editTextEstado);

    }


}