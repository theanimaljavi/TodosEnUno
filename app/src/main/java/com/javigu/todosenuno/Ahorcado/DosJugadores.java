package com.javigu.todosenuno.Ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.R;

public class DosJugadores extends AppCompatActivity {
    jugarFragment fgJugar;
    TextView tvSolo,tvElegirJuego;
    ImageButton ibSolo,ibElegirJuego;
    Button btnJugar;
    EditText etPalabra;
    String palabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahorcado_dos_jugadores);

        //asignar variables a los campos
        tvSolo = findViewById(R.id.tvJugarSolo);
        tvElegirJuego = findViewById(R.id.tvElegirJuego);
        etPalabra = findViewById(R.id.et2jugadores);
        ibSolo = findViewById(R.id.ibElegirTematica);
        ibElegirJuego = findViewById(R.id.ibElegirJuego);
        btnJugar = findViewById(R.id.btnJugar2jugadores);


        //pasar la palabra al fragmento y jugar ahorcado
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pasar la palabra para los dos jugadores al fragment dos jugadores
                palabra = String.valueOf(etPalabra.getText());
                //ocultar el boton jugar
                btnJugar.setVisibility(View.INVISIBLE);
                fgJugar = jugarFragment.newInstance(palabra,"1");
                getSupportFragmentManager().beginTransaction().replace(R.id.ahorcado_dosjugadores, fgJugar).addToBackStack(null).commit();
            }
        });

        //cambiar al modo 2 jugadores
        ibSolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ElegirTematica.class);
                startActivityForResult(intent, 0);
            }
        });

        //cambiar de juego
        ibElegirJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ElegirJuego.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}