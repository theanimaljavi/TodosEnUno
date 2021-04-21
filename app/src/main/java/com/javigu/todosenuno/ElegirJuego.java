package com.javigu.todosenuno;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.javigu.todosenuno.Ahorcado.DosJugadores;
import com.javigu.todosenuno.Ahorcado.ElegirTematica;
import com.javigu.todosenuno.Brisca.brisca_jugar;

public class ElegirJuego extends AppCompatActivity {
    TextView tvAhorcado,tvBrisca;
    ImageButton ibAhorcado,ibBrisca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elegir_juego);

        //asignar las variables con las id's
        tvAhorcado = findViewById(R.id.tvAhorcado);
        tvBrisca = findViewById(R.id.tvBrisca);
        ibAhorcado = findViewById(R.id.ibAhorcado);
        ibBrisca = findViewById(R.id.ibBrisca);


        //navegar entre fragments al selecciona juego
        ibAhorcado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //alert dialog para elegir si jugar solo o de 2 jugadores
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Ahorcado para dos jugadores o solo?")
                        .setCancelable(false)
                        //dos jugadores
                        .setPositiveButton("2 jugadores", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               // ElegirJuego.this.finish();
                                //cargar el fragmento de dos jugadores
                                Intent intent = new Intent(v.getContext(), DosJugadores.class);
                                startActivityForResult(intent, 0);
                            }
                        })
                        //el jugador jugará solo
                        .setNegativeButton("Solo", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //al ser una actividad lo haremos con un intent
                                Intent intent = new Intent (v.getContext(), ElegirTematica.class);
                                startActivityForResult(intent, 0);
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        //seleccionar juego brisca
        ibBrisca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), brisca_jugar.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}