package mx.unam.battlehack.paquini.objetos;

import android.graphics.Bitmap;

/**
 * Created by jagspage2013 on 27/09/14.
 */
public class Cupon  {

    private int id_cupones;
    private String nombre;
    private String descripcion;
    private Bitmap logo;
    private String empresa;
    private double puntos;

    public Cupon(int id_cupones, String nombre, String descripcion, Bitmap logo, String empresa, double puntos) {
        this.id_cupones = id_cupones;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.logo = logo;
        this.empresa = empresa;
        this.puntos = puntos;
    }

    public int getId_cupones() {
        return id_cupones;
    }

    public void setId_cupones(int id_cupones) {
        this.id_cupones = id_cupones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getLogo() {
        return logo;
    }

    public void setLogo(Bitmap logo) {
        this.logo = logo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public double getPuntos() {
        return puntos;
    }

    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }
}
