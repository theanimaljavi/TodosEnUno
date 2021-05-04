package com.javigu.todosenuno.Sudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;

import com.javigu.todosenuno.R;

public class sudoku_jugar extends AppCompatActivity {

    private SudokuEstilo tablero;
    private FilasYColumnas fc;
    Canvas canvas;
    boolean acierto;

    //**********TRABAJAR********


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sudoku_jugar);

        tablero = findViewById(R.id.sudokuEstilo);
        fc = tablero.getFilasYColumnas();
    }


    public void btn1(View view){
        fc.setNumberPos(1);
        tablero.invalidate();
    }
    public void btn2(View view){
        fc.setNumberPos(2);
        tablero.invalidate();
    }
    public void btn3(View view){
        fc.setNumberPos(3);
        tablero.invalidate();
    }
    public void btn4(View view){
        fc.setNumberPos(4);
        tablero.invalidate();
    }
    public void btn5(View view){
        fc.setNumberPos(5);
        tablero.invalidate();
    }
    public void btn6(View view){
        fc.setNumberPos(6);
        tablero.invalidate();
    }
    public void btn7(View view){
        fc.setNumberPos(7);
        tablero.invalidate();
    }
    public void btn8(View view){
        fc.setNumberPos(8);
        tablero.invalidate();
    }
    public void btn9(View view){
        fc.setNumberPos(9);
        tablero.invalidate();
    }
}