package com.javigu.todosenuno.Sudoku;

import java.util.ArrayList;

public class FilasYColumnas {
    ArrayList<ArrayList<Object>> cajaVacia;
    int selected_row;
    int selected_column;

    static int [][] sudoku;

    //constructor, fija las columnas y filas del array y genera el sudoku desde la calse SudokuGenerator
    FilasYColumnas(){
        selected_row = -1;
        selected_column = -1;
        cajaVacia = new ArrayList<>();
    }

    //método para cambiar una nueva posición en el tablero
    // comprobara si se encuentra en el array para pintar la letra de verde en caso de apectar o rojo si es un error
    public void setNumberPos(int num){
        int r =this.selected_row;
        int c =this.selected_column;
        // si tenemos selecionada una casilla
        if ( r != -1 && c != -1){
            //comprobar que no este en el sudoku del jugador
            //  ya que las casillas de sudoku jugador no se podrán modificar
            if (SudokuEstilo.sFC.getTablero()[r-1][c-1] != SudokuEstilo.sudoku_jugador_copia[r-1][c-1]){
                System.out.println("Número Sudoku acertar: "+SudokuEstilo.sFC.getTablero()[r-1][c-1]);
                SudokuEstilo.sudoku_jugador[r-1][c-1]=num;
            }
        }
    }

    public int[][] getTablero(){
        return this.sudoku;
    }

    public ArrayList<ArrayList<Object>> getCajaVacia(){
        return this.cajaVacia;
    }

    public int getSelected_row(){
        return selected_row;
    }

    public int getSelected_column(){
        return selected_column;
    }

    public void setSelected_row(int r){
        selected_row = r;
    }

    public void setSelected_column(int c){
        selected_column = c;
    }
}
