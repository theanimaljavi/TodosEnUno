package com.javigu.todosenuno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.javigu.todosenuno.Ahorcado.GanadorFragment;
import com.javigu.todosenuno.Ahorcado.PerdedorFragment;
import com.javigu.todosenuno.Ahorcado.jugarFragment;

public class MainActivity extends AppCompatActivity {

    Button btnMenuSalir;
    Button btnMenuElegirJuego;
    Button btnMenuAdmin;
    EditText etMenuAlertDialog;
    String admin = "olacaracola";
    String adminAlert;

    //fragmentos del ahorcado
    jugarFragment fgJugar;
    GanadorFragment fgAhorcadoGanador;
    PerdedorFragment fgAhorcadoPerdedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //asignar botones al menu
        etMenuAlertDialog = findViewById(R.id.etMenuAlertDialog);
        //nada mas se inicia la aplicación cargará el fragmento de inicio
        inicio fgIinicio = new inicio();
        getSupportFragmentManager().beginTransaction().replace(R.id.main, fgIinicio).commit();

        //al main se le redigiran varios fragmentos para cargarlos desde aquí
        //con el Bundle podemos recoger la key y obtener algun valor con getString()
        Bundle ahorcado_extras = getIntent().getExtras();
        if (ahorcado_extras != null) {
            String valor;
            valor = ahorcado_extras.getString("ahorcado_jugarfragment");
            if (valor!=null && valor.equalsIgnoreCase("ganador")) {
                //cargar el fragmento de ganador del ahorcado
                fgAhorcadoGanador = new GanadorFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main, fgAhorcadoGanador).addToBackStack(null).commit();
            } else if (valor!=null && valor.equalsIgnoreCase("perdedor")) {
                //cargar el fragmento de perdedor del ahorcado
                fgAhorcadoPerdedor = new PerdedorFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main, fgAhorcadoPerdedor).addToBackStack(null).commit();
            }else if (valor!=null){
                //cargar el fragmento de jugar del ahorcado en la version "solo"
                fgJugar = jugarFragment.newInstance(valor,"0");
                getSupportFragmentManager().beginTransaction().replace(R.id.main, fgJugar).addToBackStack(null).commit();
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
        switch (item.getItemId()){

            //ADMINISTRADOR
            case R.id.admin:
                //poner un editText en un alert dialog para verificar permisos
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Escriba la contraseña de administrador.");

                // Set up the input
                final EditText input = new EditText(this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adminAlert = input.getText().toString();
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