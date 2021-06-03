package com.javigu.todosenuno.Ahorcado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.MainActivity;
import com.javigu.todosenuno.R;

public class ElegirTematica extends AppCompatActivity {
    TextView tvAnimales,tvTecno,tvProfesiones,tvAlimentacion,tvDeportes,tvJuegos;
    ImageButton ibAnimales,ibTecno,ibProfesiones,ibAlimentacion,ibDeportes,ibJuegos;
    String tematica;
    jugarFragment fgJugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ahorcado_elegir_tematica);

        //asignar los texview para cambiar la fuente de las letras
        tvAnimales = findViewById(R.id.tvTAnimales);
        tvTecno = findViewById(R.id.tvTTecnologia);
        tvProfesiones = findViewById(R.id.tvTProfesiones);
        tvJuegos = findViewById(R.id.tvTJuego);
        tvAlimentacion = findViewById(R.id.tvTAlimentacion);
        tvDeportes = findViewById(R.id.tvTDeportes);


        //asginar los botones para elegir la tematica y abrir el juego con ella
        ibAnimales = findViewById(R.id.ibTAnimales);
        ibTecno = findViewById(R.id.ibTTecnologia);
        ibProfesiones = findViewById(R.id.ibTProfesiones);
        ibJuegos = findViewById(R.id.ibTJuegos);
        ibAlimentacion = findViewById(R.id.ibTAlimentacion);
        ibDeportes = findViewById(R.id.ibTDeportes);

        ibAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tematica = (String) tvAnimales.getText();
                Intent i = new Intent(v.getContext(), MainActivity.class);
                i.putExtra("ahorcado_jugarfragment", tematica);
                startActivity(i);
            }
        });

        ibTecno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tematica = (String) tvTecno.getText();
                Intent i = new Intent(v.getContext(), MainActivity.class);
                i.putExtra("ahorcado_jugarfragment", tematica);
                startActivity(i);
            }
        });

        ibProfesiones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tematica = (String) tvProfesiones.getText();
                Intent i = new Intent(v.getContext(), MainActivity.class);
                i.putExtra("ahorcado_jugarfragment", tematica);
                startActivity(i);
            }
        });

        ibJuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tematica = (String) tvJuegos.getText();
                Intent i = new Intent(v.getContext(), MainActivity.class);
                i.putExtra("ahorcado_jugarfragment", tematica);
                startActivity(i);
            }
        });

        ibAlimentacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tematica = (String) tvAlimentacion.getText();
                Intent i = new Intent(v.getContext(), MainActivity.class);
                i.putExtra("ahorcado_jugarfragment", tematica);
                startActivity(i);
            }
        });

        ibDeportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tematica = (String) tvDeportes.getText();
                Intent i = new Intent(v.getContext(), MainActivity.class);
                i.putExtra("ahorcado_jugarfragment", tematica);
                startActivity(i);
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