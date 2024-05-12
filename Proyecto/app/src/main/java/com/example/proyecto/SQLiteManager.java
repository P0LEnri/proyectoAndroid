package com.example.proyecto;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SQLiteManager extends SQLiteOpenHelper
{
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "Proyecto";
    private static final int DATABASE_VERSION = 1;
    // Nombre de la tabla
    private static final String TABLE_NAME = "Tareas";

    // Nombres de las columnas
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NOMBRE_CONTACTO = "NombreContacto";
    private static final String COLUMN_DESCRIPCION = "Descripcion";
    private static final String COLUMN_HORA = "Hora";
    private static final String COLUMN_FECHA = "Fecha";
    private static final String COLUMN_ESTADO = "Estado";
    private static final String COLUMN_RECORDATORIO = "Recordatorio";
    private static final String COLUMN_CATEGORIA = "Categoria";
    private static final String COLUMN_NOTIFICACION = "Notificacion";


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        // Creación de la sentencia SQL para la tabla
        StringBuilder sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)  // Nombre de la tabla
                .append("(")
                .append(COLUMN_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ") // Clave primaria autoincrementable
                .append(COLUMN_NOMBRE_CONTACTO)
                .append(" TEXT, ") // Nombre del contacto
                .append(COLUMN_DESCRIPCION)
                .append(" TEXT, ") // Descripción de la tarea
                .append(COLUMN_HORA)
                .append(" TEXT, ") // Hora de la tarea
                .append(COLUMN_FECHA)
                .append(" TEXT, ") // Fecha de la tarea
                .append(COLUMN_ESTADO)
                .append(" TEXT, ") // Estado de la tarea (Pendiente, Realizado, Aplazado)
                .append(COLUMN_RECORDATORIO)
                .append(" TEXT, ") // Información del recordatorio
                .append(COLUMN_CATEGORIA)
                .append(" TEXT, ") // Categoría de la tarea (Personal, Trabajo, Familia, etc.)
                .append(COLUMN_NOTIFICACION)
                .append(" INTEGER)"); // Indicador de notificación (0 para no, 1 para sí)

        // Ejecutar la sentencia SQL para crear la tabla
        sqLiteDatabase.execSQL(sql.toString());



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
//        switch (oldVersion)
//        {
//            case 1:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//            case 2:
//                sqLiteDatabase.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + NEW_COLUMN + " TEXT");
//        }
    }

    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }
}