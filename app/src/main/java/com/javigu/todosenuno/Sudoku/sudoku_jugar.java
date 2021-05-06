package com.javigu.todosenuno.Sudoku;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.javigu.todosenuno.Ahorcado.DosJugadores;
import com.javigu.todosenuno.Ahorcado.ElegirTematica;
import com.javigu.todosenuno.Brisca.brisca_fragment_Ayuda;
import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.R;

import static com.javigu.todosenuno.Sudoku.SudokuEstilo.sudoku_jugador_copia;

public class sudoku_jugar extends AppCompatActivity {

    private SudokuEstilo tablero;
    private FilasYColumnas fc;
    Canvas canvas;
    boolean acierto;
    static Button btnC,btnR;
    SudokuEstilo sFC;
    int comprobar=0;
    VideoView vvVictoria;
    ImageButton ibAyudaSudoku;
    static boolean resuelto;

    //**********TRABAJAR********

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sudoku_jugar);
        //cargar un nuevo sudoku cada vez que se inicie el juego sudoku
        FilasYColumnas.sudoku = SudokuGenerator.getInstance().generateGrid();
        //iniciar array
        SudokuEstilo.sudoku_jugador = new int[9][9];


        btnC= findViewById(R.id.btnComprobar);
        btnR= findViewById(R.id.btnResolver);
        ibAyudaSudoku = findViewById(R.id.ibAyudaSudoku);
        vvVictoria = findViewById(R.id.victoria);
        vvVictoria.setVisibility(View.GONE);

        tablero = findViewById(R.id.sudokuEstilo);
        fc = tablero.getFilasYColumnas();
        //desactivar chivato, ya no estará resuelto
        resuelto=false;
        //ver el sudoku internamente
        printSudoku(SudokuEstilo.sFC.getTablero());
        //boton para comprobar si los sudokus coinciden,
        // si coincide saldrá video de victoria, sino, el jugador podrá seguir jugando
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int r=0;r<9;r++){
                    for (int c=0;c<9;c++){
                        //si coinciden los números se sumará 1 al contador, si el contador llega a 81 el jugador habrá ganado
                        if (SudokuEstilo.sFC.getTablero()[r][c] == SudokuEstilo.sudoku_jugador[r][c] ){
                           ++comprobar;
                        }
                    }
                }
                System.out.println("Número comprobar acierto: "+comprobar);
                printSudoku(SudokuEstilo.sudoku_jugador);

                // si es 81, cargar mensaje de victoria.
                if (comprobar==81){
                    System.out.println("Número comprobar acierto: "+comprobar);
                    vvVictoria.setVisibility(View.VISIBLE);
                    //reproducir el video
                    String path = "android.resource://com.javigu.todosenuno/" + R.raw.victoria_sudoku;
                    vvVictoria.setVideoURI(Uri.parse(path));
                    vvVictoria.start();
                    vvVictoria.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            vvVictoria.start();
                        }
                    });
                }

                //reiniciar el contador
                comprobar=0;
            }
        });

        //boton para resolver el sudoku
        // si el jugador acepta, podrá volver a empezar de nuevo el juego
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!btnR.getText().toString().equalsIgnoreCase("Volver a empezar")) {
                    //alert dialog para elegir si jugar solo o de 2 jugadores
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Seguro que quieres resolver el sudoku?")
                            .setCancelable(false)
                            //se resolverá el puzzle
                            .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //cambiar el texto para tener la opción de reiniciar el sudoku
                                    btnR.setText("Volver a empezar");
                                    //activar chivato para resolver
                                    resuelto = true;
                                    //inicar de nuevo el array
                                    SudokuEstilo.sudoku_jugador = new int[9][9];
                                    //volver el array del jugador igual al del juego
                                    for (int r=0;r<9;r++){
                                        for (int c=0;c<9;c++){
                                            //la copia tiene los números iniciales en negro, esas posiciones serán las q tendrá ahora el array del jugador
                                            if (sudoku_jugador_copia[r][c] != 0){
                                                //añadir el número al sudoku del jugador
                                                SudokuEstilo.sudoku_jugador[r][c] = sudoku_jugador_copia[r][c];
                                            }
                                        }
                                    }
                                }
                            })
                            //si respone "No" seguirá todo como estaba
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }else{

                    //cambiar de nuevo el texto a Resolver
                    btnR.setText("Resolver");
                    //cargar de nuevo el sudoku
                    Intent intent = new Intent (v.getContext(), sudoku_jugar.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    //cierra el activity y luego abre de nuevo
                    // *elimina el parpadeo al cargar de nuevo el activity*
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            }
        });

        //boton de ayuda, abrirá el contenedor de ayuda
        ibAyudaSudoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sudoku_fragment_ayuda fgAyuda= new sudoku_fragment_ayuda();
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentSudoku , fgAyuda).commit();
            }
        });
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

    //mètodo para ver internamente el sudoku
    private void printSudoku(int Sudoku[][]) {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                System.out.print(Sudoku[x][y] + "|");
            }
            System.out.println();
        }
    }
}