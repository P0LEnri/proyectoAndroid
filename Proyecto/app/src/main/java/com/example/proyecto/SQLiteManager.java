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
    private static final String TABLE_NAME = "Tareas";


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
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME) // Nombre de la tabla
                .append("(")
                .append("ID INTEGER PRIMARY KEY AUTOINCREMENT, ") // Clave primaria autoincrementable
                .append("NombreContacto TEXT, ") // Nombre del contacto
                .append("Descripcion TEXT, ") // Descripción de la tarea
                .append("Hora TEXT, ") // Hora de la tarea, almacenada como texto
                .append("Fecha TEXT, ") // Fecha de la tarea, almacenada como texto
                .append("Estado TEXT, ") // Estado de la tarea
                .append("Prioridad INTEGER, ") // Prioridad de la tarea, opcional
                .append("Notificacion INTEGER)"); // Campo de notificación como entero (0 para no, 1 para sí)

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