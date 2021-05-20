package com.javigu.todosenuno.TRESenRaya;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.javigu.todosenuno.R;

import static android.graphics.Paint.Style.STROKE;

public class tresEnRaya_tablero extends View {

    private final int colorTablero;
    private final int colorX;
    private final int colorO;
    private final int colorGanador;

    private final Paint paint = new Paint();

    private boolean lineaGanadora;

    private final tresEnRaya_Logica tablero;
    private final tresEnRaya_Nombres turno;
    private int []array_copia;

    private int cellSize = getWidth()/3;
    private int row;
    private int col;

    public static boolean unaVezTurno;
    private boolean dibujar;
    private boolean X1;
    private boolean O1;

    //MEJORAR?:
    // el toast  cuando reinicia el juego si nunca hubo movimiento cambia de turno y no actualiza bien

    public tresEnRaya_tablero(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //obtener tablero por posiciones [3][3]
        tablero = new tresEnRaya_Logica();
        //obtener el turno
        turno = new tresEnRaya_Nombres();
        dibujar=false;
        unaVezTurno=false;
        X1=false;
        O1=false;
        lineaGanadora = false;
        array_copia = new int[2];

        //asignar el archivo xml de estilos
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.tresEnRaya_tablero, 0,0);

        try {
            //asignar los color del xml de estilos
            colorTablero = a.getInteger(R.styleable.tresEnRaya_tablero_colorTableroTRES,0);
            colorX = a.getInteger(R.styleable.tresEnRaya_tablero_colorX,0);
            colorO = a.getInteger(R.styleable.tresEnRaya_tablero_colorO,0);
            colorGanador = a.getInteger(R.styleable.tresEnRaya_tablero_colorGanador,0);
        }finally{
            a.recycle();
        }
    }

    //método de la vista, sirve para marcar el tamañao que vamos a usar
    @Override
    protected void onMeasure(int width, int height) {
        super.onMeasure(width, height);

        int dimension = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = dimension/3;
        // cuadrado de 3*3
        setMeasuredDimension(dimension, dimension);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        dibujarTablero(canvas);
        if (dibujar){
            dibujarPorTurnos(canvas);
        }

        if (lineaGanadora){
            paint.setColor(colorGanador);
            dibujarGanador(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            row = (int) Math.ceil(y/cellSize);
            col = (int) Math.ceil(x/cellSize);

            if (!lineaGanadora){
                if (tablero.actualizarTablero(row, col)){
                    invalidate();

                    //comprobar si hay ganador
                    if (tablero.ganador()){
                        lineaGanadora = true;
                        tresEnRaya_jugar.btnElegirJuego.setVisibility(VISIBLE);
                        invalidate();
                    }

                    //actualizar el turno del jugador
                    //si es primer turno se comprueba el jugador
                    if (!unaVezTurno) {
                        //guardar referencia del primer turno en un array de Integers
                        array_copia[0] = row-1;
                        array_copia[1] = col-1;
                        //buscar que jugador empieza el juego
                        // jugador == 1 'X' , jugador == 2 'O'
                        if (tresEnRaya_Nombres.turno == 1) {
                            tablero.setJugador(1);
                        } else {
                            tablero.setJugador(2);
                        }
                        //actualiza tablero
                        tablero.actualizarTablero(row, col);
                        invalidate();
                        dibujar=true;
                    }else{
                        int a = tablero.getJugador();
                        // jugador == 1 'X' , jugador == 2 'O'
                        if (tablero.getJugador() == 2){
                            tablero.setJugador(1);
                        }else{
                            tablero.setJugador(2);
                        }
                        a = tablero.getJugador();
                    }
                }
            }

            invalidate();
            return true;
        }

        return false;
    }

    //dibujar 'X' o 'O' dependiendo del turno
    private void dibujarPorTurnos(Canvas canvas) {
        boolean b  = false;
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                //si es el primer turno se comprueba manual el turno
                if (!unaVezTurno) {
                    if (tablero.getTablero()[r][c] != 0) {
                        //ponemos en true dependiendo de quien empieza
                        // 'X' == 2 , 'O' == 1
                        if (tablero.getJugador() == 2) {
                            O1=true;
                        } else if (tablero.getJugador() == 1) {
                            X1=true;
                        }
                        //volver a cambiar el turno
                        // jugador == 1 'X' , jugador == 2 'O'
                        if (tablero.getJugador()==2) {
                            tablero.setJugador(1);
                        } else {
                            tablero.setJugador(2);
                        }
                        unaVezTurno = true;
                    }
                }
                // si es distinto de 0, hay q dibujar
                if (tablero.getTablero()[r][c] != 0) {
                    // comprobación de la referencia a la primera interacción,
                    // comprueba la columna y fila que almacenamos en onTouchEvent
                    // para ello hacemos un if que compare la 'r' y la 'c' y actualizamos booleano
                    if (r == array_copia[0] && c == array_copia[1]) {
                        b = true;
                    } else {
                        b = false;
                    }
                    if (!b) {
                        //dibujar X cuando sea 1
                        if (tablero.getTablero()[r][c] == 1) {
                            dibujarX(canvas, r, c);
                        } else {
                            // dibujar 'O' si es distinto de 1
                            dibujarO(canvas, r, c);
                        }
                    }else{
                        //dibujará 'X' o la 'O' de la primera interacción
                        if (X1){
                            dibujarX(canvas, r, c);
                        }else{
                            dibujarO(canvas, r, c);
                        }
                    }
                }
            }
        }
    }

    //método para dibujar las líneas del tablero
    private void dibujarTablero(Canvas canvas){
        paint.setColor(colorTablero);
        paint.setStrokeWidth(16);

        for (int c =1; c<3; c++){
            canvas.drawLine(cellSize*c, 0, cellSize*c, canvas.getWidth(), paint);
        }
        for (int r =1; r<3; r++){
            canvas.drawLine(0, cellSize*r, canvas.getHeight(), cellSize*r, paint);
        }
    }

    //dibujar la X en el tablero
    private void dibujarX(Canvas canvas, int row, int col){
        paint .setColor(colorX);

        canvas.drawLine((float) ((col+1)*cellSize - cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) (col*cellSize + cellSize*0.2),
                        (float) ((row+1)*cellSize - cellSize*0.2),
                        paint);

        canvas.drawLine((float)(col*cellSize + cellSize*0.2),
                (float) (row*cellSize + cellSize*0.2),
                (float) ((col+1)*cellSize - cellSize*0.2),
                (float) ((row+1)*cellSize - cellSize*0.2),
                paint);
    }

    //dibujar la O en el tablero
    private void dibujarO(Canvas canvas, int row, int col){
        paint .setColor(colorO);

        canvas.drawOval((float)(col*cellSize + cellSize*0.2),
                        (float) (row*cellSize + cellSize*0.2),
                        (float) ((col*cellSize + cellSize) - cellSize*0.2),
                        (float) ((row*cellSize + cellSize) - cellSize*0.2),
                        paint);
    }

    //dibujar una línea horizontal cuando haya un ganador
    private void dibujarHori(Canvas canvas , int row, int col){
        canvas.drawLine(col, row*cellSize + (float)cellSize/2,
                 cellSize*3, row*cellSize + (float)cellSize/2,
                        paint);
    }

    //dibujar una línea vertical cuando haya un ganador
    private void dibujarVerti(Canvas canvas , int row, int col){
        canvas.drawLine(col*cellSize + (float)cellSize/2, row,
                    col*cellSize + (float)cellSize/2,
                    cellSize*3,
                           paint);
    }

    //dibujar una línea diagonal positivo (empenzando arriba derecha, acaba abajo izquierda)
    // cuando haya un ganador
    private void dibujarDiagPos(Canvas canvas){
        canvas.drawLine(0, cellSize*3,
                cellSize*3,0,
                        paint);
    }

    //dibujar una línea diagonal negativo (empenzando arriba izquierda, acaba abajo derecha)
    // cuando haya un ganador
    private void dibujarDiagNeg(Canvas canvas){
        canvas.drawLine(0, 0,
                cellSize*3,cellSize*3,
                        paint);
    }

    //dibujar linea dependiendo de las posiciones del ganador
    private void dibujarGanador(Canvas canvas){
        int row = tablero.getGanador()[0];
        int col = tablero.getGanador()[1];
        if (tablero.getGanador()[2]==1){
            dibujarHori(canvas, row , col);
        }else if (tablero.getGanador()[2]==2){
            dibujarVerti(canvas, row, col);
        }else if (tablero.getGanador()[2]==3){
            dibujarDiagNeg(canvas);
        }else{
            dibujarDiagPos(canvas);
        }
    }


    //método para organizar la configuración del juego
    public void configurarJuego(Button btnPlay, TextView jugador, String[] nombres){
        tablero.setBtnPlay(btnPlay);
        tablero.setTurno(jugador);
        tablero.setNombres(nombres);
    }

    //método para empezar de nuevo
    public void resetearJuego(){
        tablero.empezarJuego();
    }
}
