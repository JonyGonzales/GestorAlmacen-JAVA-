package com.example.gestoralmacen.model;


/**
 * Created by ravi on 20/02/18.
 */

public class Entrada {
    public static final String TABLE_NAME = "Entrada";

    public static final String COLUMN_ID = "idc";
    public static final String COLUMN_CANTIDAD = "cantidad";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_IDPRODUCTO = "idProducto";
    public static final String COLUMN_FOTO = "foto";
    public static final String COLUMN_NOMBRE = "nombre";

    public static final String COLUMN_IDTIPO = "idTipo";
    public static final String COLUMN_TIPO = "tipo";


    private int id;
    private String cantidad;
    private String fecha;
    private String idProducto;
    private String foto;
    private String nombre;
    private String idTipo;
    private String tipo;

    public Entrada() {
    }

    public Entrada(int id, String cantidad, String fecha, String idProducto, String foto, String nombre, String idTipo, String tipo) {
        this.id = id;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.idProducto = idProducto;
        this.foto = foto;
        this.nombre = nombre;
        this.idTipo = idTipo;
        this.tipo = tipo;
    }

    public void setId(int id) {
    this.id = id;
    }
    public int getId() {
    return id;
    }

    public void setCantidad(String cantidad) {
    this.cantidad = cantidad;
    }
    public String getCantidad() {
    return cantidad;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getFecha() {
        return fecha;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
    public String getIdProducto() {
        return idProducto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getFoto() {
        return foto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }
    public String getIdTipo() {
        return idTipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTipo() {
        return tipo;
    }
}
