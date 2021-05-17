package com.javigu.todosenuno.TRESenRaya;

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
import android.widget.TextView;
import android.widget.Toast;

import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.R;

public class tresEnRaya_jugar extends AppCompatActivity {

    tresEnRaya_tablero objectTablero;
    Button btnSeguirJugando;
    TextView tvTurno;
    String[] jugadores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tres_en_raya_jugar);

        objectTablero = findViewById(R.id.tresEnRaya_tablero);
        btnSeguirJugando = findViewById(R.id.btn3enRayaSeguirJugando);
        tvTurno = findViewById(R.id.tv3enRayaTurno);

        //obtener el array de jugadores de los nombres
        jugadores = tresEnRaya_Nombres.jugadores;

        //actualizar el tvTurno la primera vez
        if (tresEnRaya_Nombres.turno==1){
            tvTurno.setText("Turno: "+jugadores[0]);
            --tresEnRaya_Nombres.turno;
        }else{
            tvTurno.setText("Turno: "+jugadores[1]);
            ++tresEnRaya_Nombres.turno;
        }
        objectTablero.configurarJuego(btnSeguirJugando, tvTurno, jugadores);

        //empezar de nuevo el juego
        btnSeguirJugando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // objectTablero.resetearJuego();
                Intent intent = new Intent (v.getContext(), tresEnRaya_jugar.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 0);
                //actualizar el tvTurno cuando empieza el juego de nuevo
                if (tvTurno.getText().toString().equalsIgnoreCase("Turno: "+jugadores[0])){
                    Toast.makeText(tresEnRaya_jugar.this, "Último movimiento de "+jugadores[1]+", empieza "+jugadores[0], Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(tresEnRaya_jugar.this, "Último movimiento de "+jugadores[0]+", empieza "+jugadores[1], Toast.LENGTH_SHORT).show();
                }
                objectTablero.invalidate();
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