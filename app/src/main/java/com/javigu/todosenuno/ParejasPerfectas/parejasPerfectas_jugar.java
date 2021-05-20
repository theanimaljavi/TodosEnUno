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

import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.R;

public class parejasPerfectas_jugar extends AppCompatActivity {
    private String dificultad;
    private parejas_fragment_facil fgFacil;
    private parejas_fragment_normal fgNormal;
    private parejas_fragment_dificil fgDificil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parejas_perfectas_jugar);

        //instanciar los fragment
        fgFacil = new parejas_fragment_facil();
        fgNormal = new parejas_fragment_normal();
        fgDificil = new parejas_fragment_dificil();

        //obtener la dificultad elegida por el jugador
        Bundle parejas_extras = getIntent().getExtras();
        if (parejas_extras != null) {
            String valor;
            valor = parejas_extras.getString("parejas_dificultad");
            if (valor != null && valor.equalsIgnoreCase("facil")) {
                dificultad = "facil";
            }else if(valor != null && valor.equalsIgnoreCase("normal")){
                dificultad = "normal";
            }else{
                dificultad = "dificil";
            }
        }

        //si hay elegida una dificultad
        if (!dificultad.equalsIgnoreCase("")) {
            //redireccionar al fragment correspondiente a la dificultad
            if (dificultad.equalsIgnoreCase("facil")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorParejas, fgFacil).commit();
            } else if (dificultad.equalsIgnoreCase("normal")) {
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorParejas, fgNormal).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.contenedorParejas, fgDificil).commit();
            }
        }
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