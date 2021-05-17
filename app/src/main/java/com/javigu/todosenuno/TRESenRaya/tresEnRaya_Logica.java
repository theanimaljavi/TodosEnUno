package com.javigu.todosenuno.TRESenRaya;

import android.widget.Button;
import android.widget.TextView;

//clase para implemtnar la lógica del tablero,
// actualizar el turno y resetear el juego
// en un cuadro de 3*3
public class tresEnRaya_Logica {
    //tablero de 3*3
    private int [][] tablero;

    //array de los nombres con los jugadores
    private String[] nombres;
    private Button btnPlay;
    private TextView turno;
    private int jugador;

    //array para averiguar donde empieza, que tipo y donde finaliza el ganador
    // {fila, tipo(vertical, horizonal, diagPos, diaNeg), columna}
    private int[] ganador = {-1, -1, -1};

    public tresEnRaya_Logica() {
        tablero = new int[3][3];;
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++) {
                tablero[r][c] = 0;
            }
        }
        jugador = 0;
    }

    //actualizar el tablero
    public boolean actualizarTablero(int row, int col){
        if (tablero[row-1][col-1] == 0){
            // jugador == 1 'X' , jugador == 2 'O'
            tablero[row-1][col-1] = jugador;
            //actualizar el TextView del turno dependiendo de quien empiece
            if (tresEnRaya_Nombres.turno == 1){
                turno.setText("Turno: "+nombres[1]);
                --tresEnRaya_Nombres.turno;
            }else{
                turno.setText("Turno: "+nombres[0]);
                ++tresEnRaya_Nombres.turno;
            }
            return true;
        }else{
            return false;
        }
    }

    //método que devuelve booleano para saber si hubo ganador
    public boolean ganador(){
        boolean esGanador = false;
        //comprobar horizontal (tipo ganador 1)
        for (int r=0; r<3;r++){
            if (tablero[r][0] == tablero[r][1] && tablero[r][0] == tablero[r][2] &&
                    tablero[r][0] != 0){
                ganador = new int[] {r, 0, 1};
                esGanador=true;
            }
        }

        //comprobar vertical (tipo ganador 2)
        for (int c=0; c<3;c++){
            if (tablero[0][c]== tablero[1][c] && tablero[2][c] == tablero[0][c] &&
                    tablero[0][c] != 0){
                ganador = new int[] {0, c, 2};
                esGanador=true;
            }
        }

        //comprobar diagonal (tipo ganador 3)
        if (tablero[0][0] == tablero[1][1] && tablero[0][0] == tablero[2][2] &&
                tablero[0][0] != 0){
            ganador = new int[] {0, 2, 3};
            esGanador=true;
        }

        //comprobar diagonal (tipo ganador 4)
        if (tablero[2][0] == tablero[1][1] && tablero[2][0] == tablero[0][2] &&
                tablero[2][0] != 0){
            ganador = new int[] {2, 2, 4};
            esGanador=true;
        }

        //comprobar si se ha rellenado todo el tablero para saber si hay ganador o no
        int tableroLleno = 0;

        for (int r=0; r<3;r++){
            for (int c=0; c<3;c++){
                if (tablero[r][c] !=0){
                    tableroLleno += 1;
                }
            }
        }

        //actualizar el texto de ganador
        if (esGanador){
            if (tresEnRaya_Nombres.turno == 1){
                turno.setText("Ganador: "+nombres[1]);
            }else{
                turno.setText("Ganador: "+nombres[0]);
            }
            return true;
        }else if (tableroLleno == 9){
            turno.setText("Juego en Tablas!");
            return true;
        }else{
            return false;
        }
    }

    //empezar de nuevo
    public void empezarJuego(){
        for (int r=0; r<3; r++){
            for (int c=0; c<3; c++) {
                tablero[r][c] = 0;
            }
        }
    }

    //Getters & Setters
    public void setBtnPlay(Button btnPlay) {
        this.btnPlay = btnPlay;
    }

    public void setTurno(TextView turno) {
        this.turno = turno;
    }

    public void setNombres(String[] nombres) {
        this.nombres = nombres;
    }

    public int [][] getTablero(){
        return tablero;
    }

    public int getJugador() {
        return jugador;
    }

    public void setJugador(int jugador) {
        this.jugador = jugador;
    }

    public int[] getGanador(){
        return ganador;
    }
}
