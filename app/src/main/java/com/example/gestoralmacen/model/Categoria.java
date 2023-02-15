package com.example.gestoralmacen.model;


/**
 * Created by ravi on 20/02/18.
 */
public class Categoria {
    public static final String TABLE_NAME = "Categoria";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CATEGORIA = "categoria";


    private int id;
    private String categoria;


    public Categoria() {
    }

    public Categoria(int id, String categoria) {
        this.id = id;
        this.categoria = categoria;
    }

    public void setId(int id) {
    this.id = id;
    }
    public int getId() {
    return id;
    }

    public void setCategoria(String categoria) {
    this.categoria = categoria;
    }
    public String getCategoria() {
    return categoria;
    }

}
