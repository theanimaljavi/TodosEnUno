package com.javigu.todosenuno.Brisca;

public class Cartas {


    //variables inmutables y de la clase
    public String id;
    public String numero;
    public String palo;
    public String imagen;

    public Cartas(String id, String numero, String palo, String imagen) {
        this.id=id;
        this.numero = numero;
        this.palo = palo;
        this.imagen = imagen;
    }

    public String getNumero() {
        return numero;
    }
    public String getId() {
        return id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPalo() {
        return palo;
    }

    public void setPalo(String palo) {
        this.palo = palo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
