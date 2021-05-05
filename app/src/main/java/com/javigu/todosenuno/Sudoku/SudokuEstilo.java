package com.javigu.todosenuno.Sudoku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.javigu.todosenuno.R;

//Clase que dará forma al estilo de nuestro sudoku
public class SudokuEstilo extends View {
    private final int colorTablero;
    private final int celdaSeleccionadaColor;
    private final int celdaResalte;

    private final int colorLetra;
    private final int colorAcierto;
    private final int colorError;


    private final Paint colorTableroPaint = new Paint();
    private final Paint celdaSeleccionadaColorPaint = new Paint();
    private final Paint celdaResaltePaint = new Paint();

    public static final Paint colorLetraPaint = new Paint();
    public static final Rect colorLetraLimite  = new Rect();

    public static int cellSize;
    boolean colorNegro=false;

    static FilasYColumnas sFC = new FilasYColumnas();
    static int [][] sudoku_jugador = new int[9][9];
    static int [][] sudoku_jugador_copia = new int[9][9];

    //constructor para asignar nuestro xml de estilo
    public SudokuEstilo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuEstilo,
                0,0);

        try {
            //poner los estilos dentro del array
            colorTablero = a.getInteger(R.styleable.SudokuEstilo_colorTablero,0);
            celdaSeleccionadaColor = a.getInteger(R.styleable.SudokuEstilo_celdaSeleccionadaColor,0);
            celdaResalte = a.getInteger(R.styleable.SudokuEstilo_celdasResalte,0);
            colorLetra = a.getInteger(R.styleable.SudokuEstilo_colorLetra,0);
            colorAcierto = a.getInteger(R.styleable.SudokuEstilo_colorAcierto,0);
            colorError = a.getInteger(R.styleable.SudokuEstilo_colorError,0);
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

    //método para dibujar las líneas y aplicar colores a celdas y letras con el canvas
    protected void onDraw(Canvas canvas){
        colorTableroPaint.setStyle(Paint.Style.STROKE);
        colorTableroPaint.setStrokeWidth(16);
        colorTableroPaint.setColor(colorTablero);
        colorTableroPaint.setAntiAlias(true);

        celdaSeleccionadaColorPaint.setStyle(Paint.Style.FILL);
        celdaSeleccionadaColorPaint.setAntiAlias(true);
        celdaSeleccionadaColorPaint.setColor(celdaSeleccionadaColor);

        celdaResaltePaint.setStyle(Paint.Style.FILL);
        celdaResaltePaint.setAntiAlias(true);
        celdaResaltePaint.setColor(celdaResalte);

        colorLetraPaint.setStyle(Paint.Style.FILL);
        colorLetraPaint.setAntiAlias(true);
        colorLetraPaint.setColor(colorLetra);


        colorCell(canvas, sFC.getSelected_row(), sFC.getSelected_column() );
        canvas.drawRect(0,0,getWidth(),getHeight(), colorTableroPaint);
        drawBoard(canvas);
        drawNumbersInicio(canvas);
    }


    //método para comprobar si estamos presionando una celda o no
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean valido;
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN){
            sFC.setSelected_row((int) Math.ceil(y/cellSize));
            sFC.setSelected_column((int) Math.ceil(x/cellSize));
            valido = true;
        }else{
            valido=false;
        }
        return valido;
    }

    //método para dibujar los números
    // dibujará númmeros del array cuando el contador sea igual a 3
    // guardar en el sudoku del jugador los mismos números que mostramos
    private void drawNumbersInicio(Canvas canvas){
        int contador=0;

        //guardar los numeros iniciales que se mostraran en el tablero en el array del jugador
        for (int r=0;r<9;r++){
            for (int c=0;c<9;c++){
                if (sFC.getTablero()[r][c] != 0 && contador==3){
                    //añadir el número al sudoku del jugador
                    sudoku_jugador[r][c] = sFC.getTablero()[r][c];
                    //añadiremos otra copia más del array solo para cambiar el color de los números
                    // cuando estos sean introducidos por el usuario
                    sudoku_jugador_copia[r][c] = sFC.getTablero()[r][c];

                }
                contador++;
                if (contador==4) {
                    contador = 0;
                }
            }
        }

        colorLetraPaint.setTextSize(cellSize);
        //mostrar los numeros del array del jugador
        for (int r=0;r<9;r++) {
            for (int c = 0; c < 9; c++) {
                //si el chivato de resuelto esta activado significa q se resolverá el sudoku
                if (!sudoku_jugar.resuelto) {
                    if (sudoku_jugador[r][c] != 0) {
                        String texto = Integer.toString(sudoku_jugador[r][c]);
                        float width, height;

                        colorLetraPaint.getTextBounds(texto, 0, texto.length(), colorLetraLimite);
                        width = colorLetraPaint.measureText(texto);
                        height = colorLetraLimite.height();
                        if (sudoku_jugador[r][c] != sudoku_jugador_copia[r][c]) {
                            colorLetraPaint.setColor(colorError);
                            canvas.drawText(texto, (c * cellSize) + ((cellSize - width) / 2),
                                    (r * cellSize + cellSize) - ((cellSize - height) / 2),
                                    colorLetraPaint);
                        } else {
                            colorLetraPaint.setColor(colorLetra);
                            canvas.drawText(texto, (c * cellSize) + ((cellSize - width) / 2),
                                    (r * cellSize + cellSize) - ((cellSize - height) / 2),
                                    colorLetraPaint);
                        }
                    }
                    // para ello se rellenará en negro todas las celdas
                }else{
                    String texto = Integer.toString(sFC.getTablero()[r][c]);
                    float width, height;
                    colorLetraPaint.getTextBounds(texto, 0, texto.length(), colorLetraLimite);
                    width = colorLetraPaint.measureText(texto);
                    height = colorLetraLimite.height();
                    colorLetraPaint.setColor(colorLetra);
                    canvas.drawText(texto, (c * cellSize) + ((cellSize - width) / 2),
                            (r * cellSize + cellSize) - ((cellSize - height) / 2),
                            colorLetraPaint);
                }
            }
        }
    }

    //colores para las celdas
    private void colorCell(Canvas canvas, int r, int c){
        if (sFC.getSelected_column() != -1 && sFC.getSelected_row() != -1){
            //color celdas verticales cuando presionamos
            canvas.drawRect((c-1)*cellSize, 0, c*cellSize, cellSize*9,celdaResaltePaint);
            //color celdas horizontales cuando presionamos
            canvas.drawRect(0, (r-1)*cellSize, cellSize*9, r*cellSize,celdaResaltePaint);
            //color celda seleccionada
            canvas.drawRect((c-1)*cellSize, (r-1)*cellSize, c*cellSize, r*cellSize,celdaSeleccionadaColorPaint);
        }
        invalidate();
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

    //método para dibujar todo el tablero de lineas
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

    public FilasYColumnas getFilasYColumnas(){
        return sFC;
    }
}
