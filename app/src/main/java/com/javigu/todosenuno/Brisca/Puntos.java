package com.javigu.todosenuno.Brisca;

import java.util.ArrayList;

//clase para sumar las cartas
public class Puntos {
    final int as = 11;
    final int tres = 10;
    final int rey = 4;
    final int caballo =3;
    final int sota = 2;
    ArrayList<Integer> puntos;

    public Puntos(ArrayList<Integer> puntos) {
        this.puntos = puntos;
    }

    //comprueba de que tipo son las cartas y las suma dependendo del valor.
    public int calcularPuntos(){
        int total=0;
        for (int i=0;i<puntos.size();i++){
            if (puntos.get(i)==1)total+=as;
            if (puntos.get(i)==3)total+=tres;
            if (puntos.get(i)==12)total+=rey;
            if (puntos.get(i)==11)total+=caballo;
            if (puntos.get(i)==10)total+=sota;
        }
        return total;
    }
}
