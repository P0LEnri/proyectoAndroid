package com.example.proyecto;

import java.util.ArrayList;
import java.util.Date;

public class Tarea {
    public static ArrayList<Tarea> tareaArrayList = new ArrayList<>();
    public static String TAREA_EDIT_EXTRA = "tareaEdit";

    private int id;
    private String nombreContacto;
    private String descripcion;
    private String hora;
    private String fecha;
    private String estado;
    private String recordatorio;
    private String categoria;
    private int notificacion;

    // Constructor completo
    public Tarea(int id, String nombreContacto, String descripcion, String hora, String fecha, String estado, String recordatorio, String categoria, int notificacion) {
        this.id = id;
        this.nombreContacto = nombreContacto;
        this.descripcion = descripcion;
        this.hora = hora;
        this.fecha = fecha;
        this.estado = estado;
        this.recordatorio = recordatorio;
        this.categoria = categoria;
        this.notificacion = notificacion;
    }

    // Constructor sin notificación
    public Tarea(int id, String nombreContacto, String descripcion, String hora, String fecha, String estado, String recordatorio, String categoria) {
        this(id, nombreContacto, descripcion, hora, fecha, estado, recordatorio, categoria, 0);
    }

    public static Tarea getTareaForID(int passedTareaID) {
        for (Tarea tarea : tareaArrayList) {
            if (tarea.getId() == passedTareaID)
                return tarea;
        }
        return null;
    }

    public static ArrayList<Tarea> nonDeletedTareas() {
        ArrayList<Tarea> nonDeleted = new ArrayList<>();
        for (Tarea tarea : tareaArrayList) {
            if ("Pendiente".equals(tarea.getEstado()) || "Realizado".equals(tarea.getEstado()))
                nonDeleted.add(tarea);
        }
        return nonDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(int notificacion) {
        this.notificacion = notificacion;
    }
}
