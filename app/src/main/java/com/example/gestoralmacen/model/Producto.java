package com.example.gestoralmacen.model;


/**
 * Created by ravi on 20/02/18.
 */

public class Producto {
    public static final String TABLE_NAME = "Producto";

    public static final String COLUMN_ID = "idc";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_PRECIO = "precio";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_FOTO = "foto";
    public static final String COLUMN_STOCK = "stock";
    public static final String COLUMN_ESTADO = "estado";
    public static final String COLUMN_CATEGORIA = "categoria";
    public static final String COLUMN_FKCATEGORIA = "fkcategoria";

    private int id;
    private String nombre;
    private String precio;
    private String descripcion;
    private String foto;
    private String stock;
    private String estado;
    private String categoria;
    private String fkcategoria;


    public Producto() {
    }

    public Producto(int id, String nombre, String precio,  String descripcion, String  foto,
    String stock, String estado, String categoria, String fkcategoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.foto = foto;
        this.stock = stock;
        this.estado = estado;
        this.categoria = categoria;
        this.fkcategoria = fkcategoria;
    }

    public void setId(int id) {
    this.id = id;
    }
    public int getId() {
    return id;
    }

    public void setNombre(String nombre) {
    this.nombre = nombre;
    }
    public String getNombre() {
    return nombre;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public String getPrecio() {
        return precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getFoto() {
        return foto;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
    public String getStock() {
        return stock;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getEstado() {
        return estado;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getCategoria() {
        return categoria;
    }

    public void setFkCategoria(String fkcategoria) {
        this.fkcategoria = fkcategoria;
    }
    public String getFkCategoria() {
        return fkcategoria;
    }
}
