package com.javigu.todosenuno.TRESenRaya;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.javigu.todosenuno.Ahorcado.DosJugadores;
import com.javigu.todosenuno.Ahorcado.ElegirTematica;
import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.MainActivity;
import com.javigu.todosenuno.R;
import com.javigu.todosenuno.Sudoku.sudoku_jugar;

import java.util.Random;

public class tresEnRaya_Nombres extends AppCompatActivity {

    VideoView vv3enRaya;
    EditText etJ1, etJ2;
    Button btnJugar, btnElegirJuego;
    private static String jugador1 = "";
    private static String jugador2 = "";
    static String[] jugadores = new String[2];
    //usaremos esta variable para asignar el jugador que empieza el juego
    static int turno ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tres_en_raya_nombres);

        //asgignar referencia
        vv3enRaya = findViewById(R.id.vv3EnRaya);
        etJ1 = findViewById(R.id.et3jugador1);
        etJ2 = findViewById(R.id.et3jugador2);
        btnJugar = findViewById(R.id.btn3enRayaJugar);
        btnElegirJuego = findViewById(R.id.btn3enRayaElegirJuego);

        //ruta el video
        String path = "android.resource://com.javigu.todosenuno/" + R.raw.tres_en_raya;
        vv3enRaya.setVideoURI(Uri.parse(path));
        //emepzar vídeo
        vv3enRaya.start();
        //reproducir en bucle si finaliza
        vv3enRaya.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                vv3enRaya.start();
            }
        });

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugador1 = etJ1.getText().toString();
                jugador2 = etJ2.getText().toString();
                jugadores[0] = jugador1;
                jugadores[1] = jugador2;
                //iniciar juego si al menos los dos campos contienen algo
                if (!jugador1.equalsIgnoreCase("") || !jugador2.equalsIgnoreCase("")) {
                    //alert dialog para elegir que jugador empieza, o aleatorio
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Quién comienza el juego?")
                            .setCancelable(false)
                            //empieza jugador 1 'X'
                            .setPositiveButton(jugador1, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    turno = 1;
                                    Intent i = new Intent(v.getContext(), tresEnRaya_jugar.class);
                                    startActivity(i);
                                }
                            })
                            //empieza jugador 2 'O'
                            .setNegativeButton(jugador2, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    turno = 0;
                                    Intent i = new Intent(v.getContext(), tresEnRaya_jugar.class);
                                    startActivity(i);
                                }
                            })
                        .setNeutralButton("Aleatorio", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Random r = new Random();
                                int a= r.nextInt(2);
                                turno = a;
                                Intent i = new Intent(v.getContext(), tresEnRaya_jugar.class);
                                startActivity(i);
                            }
                        });

                    AlertDialog alert = builder.create();
                    alert.show();

                }else{
                    Toast.makeText(tresEnRaya_Nombres.this, "Rellena los dos campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnElegirJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ElegirJuego.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (vv3enRaya!=null){
            vv3enRaya.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        vv3enRaya.pause();
    }
}