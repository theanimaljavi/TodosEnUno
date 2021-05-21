package com.javigu.todosenuno.ParejasPerfectas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.R;

public class parejasPerfectas_dificultad extends AppCompatActivity {
    private Button btnFacil, btnNormal, btnDificil, btnElegirJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parejas_perfectas_dificultad);

        btnFacil = findViewById(R.id.btnParejasFacil);
        btnNormal = findViewById(R.id.btnParejasNormal);
        btnDificil = findViewById(R.id.btnParejasDificil);
        btnElegirJuego = findViewById(R.id.btnParejasDificultadElegirJuego);

        btnFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), parejasPerfectas_jugar.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("parejas_dificultad", "facil");
                startActivityForResult(intent,0);
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), parejasPerfectas_jugar.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("parejas_dificultad", "normal");
                startActivityForResult(intent,0);
            }
        });

        btnDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), parejasPerfectas_jugar.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("parejas_dificultad", "dificil");
                startActivityForResult(intent,0);
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
}