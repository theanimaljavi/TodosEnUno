package com.javigu.todosenuno.ParejasPerfectas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflador del menú
        MenuInflater infladorMenu = getMenuInflater();
        infladorMenu.inflate(R.menu.menu_contextual, menu);
        //Asociar el menu al menu_busqueda.xml
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (item.getItemId()){
            //ELEGIRJUEGO
            case R.id.elegirjuego:
                Intent intent = new Intent (this, ElegirJuego.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.salir:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("¿Desea salir de la aplicación?");

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                //mostrar alert dialog
                builder.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}