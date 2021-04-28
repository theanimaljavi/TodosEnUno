package com.javigu.todosenuno.Sudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.javigu.todosenuno.R;

//Clase que dará forma al estilo de nuestro sudoku
public class SudokuEstilo extends View {
    private final int colorTablero;
    private final Paint colorTableroPaint = new Paint();
    private int cellSize;

    //constructor para asignar nuestro xml de estilo
    public SudokuEstilo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuEstilo,
                0,0);

        try {
            colorTablero = a.getInteger(R.styleable.SudokuEstilo_colorTablero,0);
        }finally {
            a.recycle();
        }
    }

    //método para tomar las medidas del tablero
    protected void onMeasure(int w, int h){
        super.onMeasure(w,h);

        int dimension = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        cellSize = dimension / 9;
        setMeasuredDimension(dimension,dimension);
    }

    //método para dibujar las líneas con el canvas
    protected void onDraw(Canvas canvas){
        colorTableroPaint.setStyle(Paint.Style.STROKE);
        colorTableroPaint.setStrokeWidth(16);
        colorTableroPaint.setColor(colorTablero);
        colorTableroPaint.setAntiAlias(true);

        canvas.drawRect(0,0,getWidth(),getHeight(), colorTableroPaint);
        drawBoard(canvas);
    }

    //método para dibujar las líneas gruegas entre los diferentes bloques de números
    private void drawThickLines(){
        colorTableroPaint.setStyle(Paint.Style.STROKE);
        colorTableroPaint.setStrokeWidth(10);
        colorTableroPaint.setColor(colorTablero);

    }

    //método para dibujar las líneas entre los números de un bloque
    private void drawThinkLines(){
        colorTableroPaint.setStyle(Paint.Style.STROKE);
        colorTableroPaint.setStrokeWidth(4);
        colorTableroPaint.setColor(colorTablero);

    }

    //método para dibujar todo el tablero
    private void drawBoard(Canvas canvas){
        //dibujar 9 celdas
        for (int c=0; c<10;c++){
            if (c%3==0){
                drawThickLines();
            }else{
                drawThinkLines();
            }
            canvas.drawLine(cellSize * c, 0, cellSize * c, getWidth(), colorTableroPaint);
        }

        //dibujar 9 filas
        for (int r=0; r<10;r++){
            if (r%3==0){
                drawThickLines();
            }else{
                drawThinkLines();
            }
            canvas.drawLine(0, cellSize * r, getWidth(), cellSize * r,  colorTableroPaint);

        }
    }
}
