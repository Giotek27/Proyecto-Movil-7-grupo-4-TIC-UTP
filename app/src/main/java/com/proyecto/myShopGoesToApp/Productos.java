package com.proyecto.myShopGoesToApp;

public class Productos {
    private String nomproduc;
    private String cantidad;
    private String precio;
    private String catproducto;
    private String descproduc;
    private String garanproduc;
    private String imagen;


    public Productos(){
        this.nomproduc="";
        this.cantidad="";
        this.precio="";
        this.catproducto="";
        this.descproduc="";
        this.garanproduc="";
        this.imagen="";
    }

    public Productos(String nomproduc, String cantidad, String precio, String catproducto, String  descproduc, String garanproduc, String imagen){
        this.nomproduc=nomproduc;
        this.cantidad=cantidad;
        this.precio=precio;
        this.catproducto=catproducto;
        this.descproduc=descproduc;
        this.garanproduc=garanproduc;
        this.imagen=imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNomproduc() {
        return nomproduc;
    }

    public void setNomproduc(String nomproduc) {
        this.nomproduc = nomproduc;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCatproducto() {
        return catproducto;
    }

    public void setCatproducto(String catproducto) {
        this.catproducto = catproducto;
    }

    public String getDescproduc() {
        return descproduc;
    }

    public void setDescproduc(String descproduc) {
        this.descproduc = descproduc;
    }

    public String getGaranproduc() {
        return garanproduc;
    }

    public void setGaranproduc(String garanproduc) {
        this.garanproduc = garanproduc;
    }
}
