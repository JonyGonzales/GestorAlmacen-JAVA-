package com.example.gestoralmacen.model;


/**
 * Created by ravi on 20/02/18.
 */
public class Tipo {
    public static final String TABLE_NAME = "Tipo";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIPO = "tipo";


    private int id;
    private String tipo;


    public Tipo() {
    }

    public Tipo(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public void setId(int id) {
    this.id = id;
    }
    public int getId() {
    return id;
    }

    public void setTipo(String tipo) {
    this.tipo = tipo;
    }
    public String getTipo() {
    return tipo;
    }

}
