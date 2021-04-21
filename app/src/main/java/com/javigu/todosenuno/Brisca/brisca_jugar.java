package com.javigu.todosenuno.Brisca;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.javigu.todosenuno.Ahorcado.GestionFotos;
import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class brisca_jugar extends AppCompatActivity {

    ArrayList<Cartas> cartas;
    ArrayList<Cartas> cartasUsadas;
    ArrayList<Cartas> cartasDesordenadas;
    ArrayList<Cartas> cartasJugador = new ArrayList<Cartas>(3);
    ArrayList<Cartas> cartasIA1 = new ArrayList<Cartas>(3);
    ArrayList<Integer> guardarPuntos = new ArrayList<Integer>();
    Puntos puntos;
    ImageButton cartaMano1,cartaMano2,cartaMano3,cartaIA11,cartaIA12,cartaIA13;
    Cartas c,cPalo,cartaElegidaJugador,cartaElegidaIA;
    int contador=39, totalPuntosJugador=0,totalPuntosIA=0,gana=0;
    ImageView ivTrasera,cartaPalo,cartaMesaIA,cartaMesaJugador,cartaBasuraJugador,cartaBasuraIA;
    TextView tvPalo,tvTurno,tvPuntosJugador,tvPuntosIA;
    boolean turno = false, finRonda = false, mandaIA=false,mandaJugador=false;
    String URL;
    static int o=0;
    final Handler handler = new Handler();

    //hacer final de juego miercoles 21/04
    //contador ya es 39
    //cPalo tiene la carta 39 (al contar el 0 son 40 igual)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.brisca_jugar);


        //imagebutton
        cartaMano1 = findViewById(R.id.cartaMano1); cartaMano2 = findViewById(R.id.cartaMano2); cartaMano3 = findViewById(R.id.cartaMano3);
        cartaIA11 = findViewById(R.id.cartaIA1);    cartaIA12 = findViewById(R.id.cartaIA2);    cartaIA13 = findViewById(R.id.cartaIA3);
        //imageview
        cartaPalo = findViewById(R.id.cartaPalo);   cartaMesaIA = findViewById(R.id.cartaMesaIA1); cartaMesaJugador = findViewById(R.id.cartaMesaJugador);
        ivTrasera = findViewById(R.id.ivTrasera);
        cartaBasuraJugador = findViewById(R.id.cartaBasuraJugador); cartaBasuraJugador.setVisibility(View.INVISIBLE);
        cartaBasuraIA = findViewById(R.id.cartaBasuraIA);           cartaBasuraIA.setVisibility(View.INVISIBLE);
        //TextView
        tvPalo = findViewById(R.id.tvPalo); tvTurno = findViewById(R.id.tvTurno); tvPuntosJugador = findViewById(R.id.tvPuntosJugador); tvPuntosIA = findViewById(R.id.tvPuntosIA);

        //extraer las cartas del json
        InputStream raw = getResources().openRawResource(R.raw.baraja);
        Reader rd = new BufferedReader(new InputStreamReader(raw));
        Type listType = new TypeToken<List<Cartas>>() {}.getType();
        Gson gson = new Gson();
        cartas = gson.fromJson(rd, listType);

        //poner de tamaño el array de usadas el mismo que de cartas en el JSON
        cartasUsadas = new ArrayList<Cartas>(cartas.size());

        //barajear las cartas
        barajar();
        //repartir cartas iniciales y seleccionar al azar quien empieza
        try {
            ManoInicial();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //pondrá la primera carta en la mesa
        // comprobará si es final de ronda, si es mi turno, quien gana, y sumará los puntos acorde a sus valores
        cartaMano1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ERROR: SE AÑADEN MUCHAS MAS CARTAS A LOS ARRAYS, LAS CARTAS SE REPITEN

                //jugar
                tvTurno.setVisibility(View.VISIBLE);
                cartaMano1.setVisibility(View.INVISIBLE);
                cartaElegidaJugador = cartasJugador.get(0);
                //imagen que vamos a colocar
                URL = cartaElegidaJugador.getImagen();
                //añadir la imagen en el centro de la mesa
                Picasso.with(v.getContext()).load(URL).into(cartaMesaJugador);
                cartaMesaJugador.setVisibility(View.VISIBLE);
                //comprobar si es final de ronda
                if (finRonda) {
                    //la IA ha comenzado la ronda tirando carta
                    mandaIA = true;
                    int valorJugador = 0, valorIA = 0;
                    gana = 0;
                    //almacenar los valores y pasarlos al objeto puntos
                    valorJugador = Integer.parseInt(cartaElegidaJugador.getNumero());
                    guardarPuntos.add(valorJugador);
                    valorIA = Integer.parseInt(cartaElegidaIA.getNumero());
                    guardarPuntos.add(valorIA);
                    puntos = new Puntos(guardarPuntos);
                    //llamada al método ganador para comprobar quien ha ganado la mano
                    // 0 = gana jugador
                    // 1 = gana IA
                    // 2 = emapte
                    gana = ganador(valorJugador, valorIA);

                    //manejador para que se vean correctamente las transiciones entre las cartas, los puntos y la nueva ronda
                    final Runnable rr = new Runnable() {
                        public void run() {
                            quienGana(gana);

                        }
                    };
                    handler.postDelayed(rr, 1500);
                    //resetear variable
                    mandaIA = false;
                    //el siguiente turno empezara la ronda
                    finRonda = false;
                } else {//si no es final de ronda, se muestra la carta del jugador y
                    // la IA tirará la mejor carta posible entre muchas posibilidades
                    mandaJugador = true;
                    //almacenar la carta que deuvelve el algoritmo
                    cartaElegidaIA = algoritmoIA();
                    //una espera cuando tira carta la IA, solo la parte visual
                    final Runnable rIALanzaCarta = new Runnable() {
                        public void run() {
                            URL = cartaElegidaIA.getImagen();
                            tvTurno.setVisibility(View.INVISIBLE);
                            Picasso.with(v.getContext()).load(URL).into(cartaMesaIA);
                            cartaMesaIA.setVisibility(View.VISIBLE);
                            //ocultar la carta de la IA y quitar del array la carta seleccioanda
                            for (int i = 0; i < cartasIA1.size(); i++) {
                                if (cartaElegidaIA.getId().equalsIgnoreCase(cartasIA1.get(i).getId())) {
                                    if (i == 0) {
                                        cartaIA11.setVisibility(View.INVISIBLE);
                                        break;
                                    }
                                    if (i == 1) {
                                        cartaIA12.setVisibility(View.INVISIBLE);
                                        break;
                                    }
                                    if (i == 2) {
                                        cartaIA13.setVisibility(View.INVISIBLE);
                                        break;
                                    }
                                }
                            }
                        }
                    };
                    handler.postDelayed(rIALanzaCarta, 1000);

                    //se comprueba el ganador
                    int valorJugador = 0, valorIA = 0;
                    gana = 0;
                    //almacenar los valores y pasarlos al objeto puntos
                    valorJugador = Integer.parseInt(cartaElegidaJugador.getNumero());
                    guardarPuntos.add(valorJugador);
                    valorIA = Integer.parseInt(cartaElegidaIA.getNumero());
                    guardarPuntos.add(valorIA);
                    puntos = new Puntos(guardarPuntos);
                    //llamada al método ganador para comprobar quien ha ganado la mano
                    // 0 = gana jugador
                    // 1 = gana IA
                    // 2 = emapte
                    gana = ganador(valorJugador, valorIA);

                    //manejador para que se vean correctamente las transiciones entre las cartas, los puntos y la nueva ronda
                    final Runnable r = new Runnable() {
                        public void run() {
                            quienGana(gana);
                            //resetear variable
                            mandaJugador = false;
                        }
                    };
                    handler.postDelayed(r, 1000);
                }

                //manejador para esperar un tiempo en lo q se sacan nuevas cartas para empezar la ronda
                final Runnable rSacarCartas = new Runnable() {
                    public void run() {
                        //quitar del array de la carta seleccionada
                        cartasJugador.remove(0);
                        //sacar jugador
                        cartasJugador.add(sacarCartas());
                        //CAMBIAR LAS CARTAS DEL ORDEN DEL ARRAY
                        //  -La nueva carta añadida al array, tendra que ser la posición 0
                        //  -Mientras que las otras dos mantendrán su posición (1 y 2)
                        Cartas cNueva = cartasJugador.get(2);
                        Cartas cVieja = cartasJugador.get(0);
                        cartasJugador.add(0, cNueva);
                        cartasJugador.remove(3);
                        cartasJugador.add(2, cVieja);
                        cartasJugador.remove(2);

                        //imagen que vamos a colocar
                        URL = cartasJugador.get(0).getImagen();
                        //añadir la imagen en el centro de la mesa
                        Picasso.with(v.getContext()).load(URL).into(cartaMano1);
                        //hacer visible de nuevo el ImageView con la nueva carta
                        cartaMano1.setVisibility(View.VISIBLE);

                        //ocultar la carta de la IA y quitar del array la carta seleccioanda
                        for (int i = 0; i < cartasIA1.size(); i++) {
                            if (cartaElegidaIA.getId().equalsIgnoreCase(cartasIA1.get(i).getId())) {
                                //eliminamos la carta de la posicion del array
                                cartasIA1.remove(i);
                                //añadimos nueva carta
                                cartasIA1.add(sacarCartas());
                                //CAMBIAR LAS CARTAS DEL ORDEN DEL ARRAY
                                //  -La nueva carta añadida al array, tendra que ser la posición i
                                //  -Mientras que las otras dos se modificaran dependiendo de donde se eliminó la última carta
                                if (i == 0) {
                                    cNueva = cartasIA1.get(2);
                                    cVieja = cartasIA1.get(0);
                                    cartasIA1.add(0, cNueva);
                                    cartasIA1.remove(3);
                                    cartasIA1.add(2, cVieja);
                                    cartasIA1.remove(2);
                                    //imagen que vamos a colocar
                                    URL = cartasIA1.get(0).getImagen();
                                    //añadir la imagen en el centro de la mesa
                                    Picasso.with(v.getContext()).load(URL).into(cartaIA11);
                                    cartaIA11.setVisibility(View.VISIBLE);
                                    break;
                                } else if (i == 1) {
                                    cNueva = cartasIA1.get(2);
                                    cVieja = cartasIA1.get(1);
                                    cartasIA1.add(1, cNueva);
                                    cartasIA1.remove(3);
                                    cartasIA1.add(2, cVieja);
                                    cartasIA1.remove(1);
                                    //imagen que vamos a colocar
                                    URL = cartasIA1.get(1).getImagen();
                                    //añadir la imagen en el centro de la mesa
                                    Picasso.with(v.getContext()).load(URL).into(cartaIA12);
                                    cartaIA12.setVisibility(View.VISIBLE);
                                    break;
                                } else if (i == 2) {
                                    //imagen que vamos a colocar
                                    URL = cartasIA1.get(2).getImagen();
                                    //añadir la imagen en el centro de la mesa
                                    Picasso.with(v.getContext()).load(URL).into(cartaIA13);
                                    cartaIA13.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }
                        }
                        cartaElegidaIA = null;
                        cartaElegidaJugador = null;
                    }
                };
                handler.postDelayed(rSacarCartas, 1500);

                //manejador para que se vea correctamente el inicio de la ronda
                final Runnable rInicioRonda = new Runnable() {
                    public void run() {
                        //empezar de nuevo la ronda
                        empezarRonda(gana);
                        guardarPuntos.removeAll(guardarPuntos);
                    }
                };
                handler.postDelayed(rInicioRonda, 1500);
            }
        });
}

    //comprueba quien gana la mano
    public int ganador(int vJugador, int vIA){
        //devolver integer
        // 0 = gana jugador
        // 1 = gana IA
        int ganador= 2;
        //el AS y el TRES son valores mas altos que: 2,4,5,6 y 7
        int AS =1;
        int TRES=3;
        //boolean que comprueba si alguna carta elegida es del mismo palo
        boolean palo=false;
        //si coincide el palo ganador con el palo de la carta elegida por el jugador pero NO con el de la IA
        if (cPalo.getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo()) && !cPalo.getPalo().equalsIgnoreCase(cartaElegidaIA.getPalo())){
            ganador = 0;

        }else if (cPalo.getPalo().equalsIgnoreCase(cartaElegidaIA.getPalo()) && !cPalo.getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo())){
         //si coincide el palo ganador con el palo de la carta elegida por la IA pero NO con el de el jugador
            ganador = 1;

        }else if (cPalo.getPalo().equalsIgnoreCase(cartaElegidaIA.getPalo()) && cPalo.getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo())){
         //si coincide el palo ganador con el palo de la carta elegida por la IA y TAMBIÉN con la del jugador
            //comprueba si alguien tiene un AS o TRES en la mesa
            if (vJugador==AS || vJugador==TRES || vIA==AS || vIA==TRES) {

                //si el jugador tiene un AS
                if (vJugador == AS && vIA != AS) {
                    ganador = 0;
                } else if (vJugador != AS && vIA == AS) { //si la IA tiene AS
                    ganador = 1;
                }

                //si el jugador tiene un TRES y la IA no tiene el AS
                if (vJugador == TRES && vIA != AS) {
                    ganador = 0;
                } else if (vJugador != AS && vIA == TRES) { //si la IA tiene TRES y el jugador no tiene el AS
                    ganador = 1;
                }
            }else{
                //si nadie tiene el AS o TRES se comprobara solo el numero mas alto
                if (vJugador > vIA) {
                    ganador = 0;
                } else if (vIA > vJugador) {
                    ganador = 1;
                }
            }

        }else{
            //si ninguna de las cartas elegidas es del mismo palo que el palo ganador
            palo=true;
        }

        //si no coinciden cartas con el palo, y ademas coinciden los palos de las dos cartas en la mesa
        if (palo && cartaElegidaIA.getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo())) {
            //comprueba si alguien tiene un AS o TRES en la mesa
            if (vJugador==AS || vJugador==TRES || vIA==AS || vIA==TRES) {

                //si el jugador tiene un AS
                if (vJugador == AS && vIA != AS) {
                    ganador = 0;
                } else if (vJugador != AS && vIA == AS) { //si la IA tiene AS
                    ganador = 1;
                }

                //si el jugador tiene un TRES y la IA no tiene el AS
                if (vJugador == TRES && vIA != AS) {
                    ganador = 0;
                } else if (vJugador != AS && vIA == TRES) { //si la IA tiene TRES y el jugador no tiene el AS
                    ganador = 1;
                }
            }else{
                //si nadie tiene el AS o TRES se comprobara solo el numero mas alto
                if (vJugador > vIA) {
                    ganador = 0;
                } else if (vIA > vJugador) {
                    ganador = 1;
                }
            }
            palo=false;
        }else if (palo && mandaIA){
            // si empezo la IA el turno y ninguna de las cartas en la mesa coincide con el palo,
            // y también el jugador no coincide con el palo de la IA
            ganador=1;
        }else if(palo && mandaJugador){
            // si empezó el jugador el turno y ninguna de las cartas en la mesa coincide con el palo,
            // y también la IA no coincide con el palo del jugador
            ganador=0;
        }
        return ganador;
    }


    //método para saber si gana la IA o el jugador
    public int quienGana(int gana) {
        //quitar las cartas de la mesa
        cartaMesaIA.setVisibility(View.INVISIBLE);
        cartaMesaJugador.setVisibility(View.INVISIBLE);
        if (gana == 0) {
            //calcular si los valores contienen puntos, sumarlos y actualizar los puntos
            totalPuntosJugador += puntos.calcularPuntos();
            tvPuntosJugador.setText("Puntos: " + totalPuntosJugador);
            //quien gana le toca de nuevo sacar
            turno = true;
            cartaBasuraJugador.setVisibility(View.VISIBLE);
            //mano terminada por la IA, se empieza de nuevo, turno de la IA
            finRonda = false;
        } else if (gana == 1) {
            //calcular si los valores contienen puntos, sumarlos y actualizar los puntos
            totalPuntosIA += puntos.calcularPuntos();
            tvPuntosIA.setText("Puntos: " + totalPuntosIA);
            //quien gana le toca de nuevo sacar
            turno = false;
            cartaBasuraIA.setVisibility(View.VISIBLE);
            //mano terminada por el jugador, se empieza de nuevo, turno del jugador
            finRonda = true;
        }
        return gana;
    }

    //metodo para saber quien empieza la ronda
    // jugador 0
    // IA 1
    // si empieza la IA, tirará una carta de forma aleatoria en la mesa
    // MEJORAR: QUE LA IA NO TIRE DE FORMA ALEATORIA, IMPLEMENTAR ALGUNAS POSIBILIDADES
    public void empezarRonda(int gana){
        if (gana==0){
            //si es 0 empieza el jugador con que no será final de ronda
            finRonda=false;
           // turno=true;
            tvTurno.setVisibility(View.VISIBLE);
            cartaMesaIA.setVisibility(View.INVISIBLE);
        }else if (gana==1){
            //empieza IA, será final de ronda
            tvTurno.setVisibility(View.INVISIBLE);
            Random r = new Random();
            int numeroRandomInicialIA = r.nextInt(3);
            // desactivar la imagen que vamos a mostrar
            if (numeroRandomInicialIA == 0) cartaIA11.setVisibility(View.INVISIBLE);
            if (numeroRandomInicialIA == 1) cartaIA12.setVisibility(View.INVISIBLE);
            if (numeroRandomInicialIA == 2) cartaIA13.setVisibility(View.INVISIBLE);
            // coger una carta random y ponerla en la mesa
            cartaElegidaIA = cartasIA1.get(numeroRandomInicialIA);
            URL = cartaElegidaIA.getImagen();
            Picasso.with(getApplicationContext()).load(URL).into(cartaMesaIA);
            cartaMesaIA.setVisibility(View.VISIBLE);
            // el siguiente turno será final de ronda
            finRonda = true;
            tvTurno.setVisibility(View.VISIBLE);
        }
    }

    //metodo para desordenar las cartas
    public void barajar() {
        //inicar array
        cartasDesordenadas = new ArrayList<Cartas>(cartas.size());
        //introducir la primera carta
        int random = (int)(Math.random()*40);
        Cartas c = cartas.get(random);
        cartasDesordenadas.add(c);

        //bucle para introducir las cartas
        for (int i = 0; cartasDesordenadas.size() < 40; i++) {
            //obtener carta random del mazo original
            random = (int) (Math.random() * 40);
            c = cartas.get(random);
            String idCarta = c.getId();
            //bucle para comprobar is ya existe la carta
            for (int j = 0; j < cartasDesordenadas.size(); j++) {
                String idCartasDesordenadas = cartasDesordenadas.get(j).getId();
                //si los id's coinciden, dejamos la carta a null y buscamos otro random
                if (idCarta.equalsIgnoreCase(idCartasDesordenadas)) {
                    c= null;
                    break;
                }
            }
            //añadir la carta si es distinto de null
            if (c!=null){
                cartasDesordenadas.add(c);
            }
        }
    }

    //método para sacar cartas
    public Cartas sacarCartas() {
        if (contador >=0) {
            contador--;
            //cuando no queden cartas, no aparecerá la imagen de la carta trasera en la mesa.
            if (contador==0){
                ivTrasera.setVisibility(View.INVISIBLE);
            }
            return cartasDesordenadas.get(contador);
        }else {
            throw new IllegalArgumentException("No quedan cartas.");
        }
    }

    //muestra las cartas iniciales, elige el palo y quien empieza
    private void ManoInicial() throws InterruptedException {
        //carta que dara cual es el palo que manda sobre el juego
        c = cartasDesordenadas.get(39);
        URL = c.getImagen();
        Picasso.with(this).load(URL).into(cartaPalo);
        //guardar la carta palo para repartirla al final
        cPalo = c;
        tvPalo.setText(cPalo.getPalo().toUpperCase());
        String URL;
        //almacenamos en arrays las cartas iniciales
        //de la IA no tendra imagen, puesto que el jugador no sabrá cuales tiene
        cartasJugador.add(sacarCartas());
        URL = cartasJugador.get(0).getImagen();
        Picasso.with(this).load(URL).into(cartaMano1);

        cartasIA1.add(sacarCartas());
        //comentar: pruebas
        URL = cartasIA1.get(0).getImagen();
        Picasso.with(this).load(URL).into(cartaIA11);

        cartasJugador.add(sacarCartas());
        URL = cartasJugador.get(1).getImagen();
        Picasso.with(this).load(URL).into(cartaMano2);

        cartasIA1.add(sacarCartas());
        //comentar: pruebas
        URL = cartasIA1.get(1).getImagen();
        Picasso.with(this).load(URL).into(cartaIA12);

        cartasJugador.add(sacarCartas());
        URL = cartasJugador.get(2).getImagen();
        Picasso.with(this).load(URL).into(cartaMano3);

        cartasIA1.add(sacarCartas());
        //comentar: pruebas
        URL = cartasIA1.get(2).getImagen();
        Picasso.with(this).load(URL).into(cartaIA13);

        //manejador para que se vean correctamente las transiciones entre las cartas
        final Runnable rr = new Runnable() {
            public void run() {
                //seleccionar quien empieza la partida
                Random r = new Random();
                int i = r.nextInt(2);
                int numeroRandomInicialIA = r.nextInt(3);
                String URL="";
                //Si es 1, sale IA, si es 2, sale el jugador
                if (i==1){
                    // desactivar la imagen que vamos a mostrar
                    if(numeroRandomInicialIA==0)cartaIA11.setVisibility(View.INVISIBLE);
                    if(numeroRandomInicialIA==1)cartaIA12.setVisibility(View.INVISIBLE);
                    if(numeroRandomInicialIA==2)cartaIA13.setVisibility(View.INVISIBLE);
                    // coger una carta random y ponerla en la mesa
                    cartaElegidaIA = cartasIA1.get(numeroRandomInicialIA);
                    URL = cartaElegidaIA.getImagen();
                    Picasso.with(getApplicationContext()).load(URL).into(cartaMesaIA);
                    // el siguiente turno será final de ronda
                    finRonda=true;
                    tvTurno.setVisibility(View.VISIBLE);
                    o=i;
                }
            }
        };
        handler.postDelayed(rr, 1500);
        if (o==0)tvTurno.setVisibility(View.VISIBLE);
       // turno=true;
        }

    //MENU
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

    public Cartas algoritmoIA() {
        //
        //********************************************
        // ALGORITMO IA
        //********************************************
        /* BUGS
            -JUGADOR TIRA 5 DE BASTOS, IA TIENE REY DE BASTOS Y TIRA CARTA DE OTRO PALO QUE NO ES GANADOR // YA DEBERIA ESTAR ARREGLADO ¡COMPROBAR!
            -JUGADOR TIRA 7 DE OROS (NO ES PALO GANADOR), IA TIENE REY DE OROS, PETA
            -POSIBLE BUG: JUGADOR TIRA CARTA PALO, LA IA TIENE LAS 3 CARTA PALO, PETA
        */
        //********************************************
        /*MEJORAR ALGORITMO
            -JUGADOR TIRA CARTA SIN VALOR (7 COPAS) SIN SER PALO GANADOR (ESPADAS), IA NO TIRA PALO GANADOR(TENIA SOTA(10) ESPADAS),
                PERO TIRA LA PEOR DECISION (AS DE BASTOS, TENIA 3 DE OROS )
            -JUGADOR TIRA CARTA DISTINO DEL PALO Y SIN VALOR (5 OROS),
                IA TIRA CARTA MAS ALTA DEL PALO PERO NO LA MEJOR OPCION TIRA: 10 OROS, TENIA 12 OROS. //PASA POR RECORRER LA ULTIMA POSICION SIMPLEMENTE, PORQUE TENIA GUARDADO EL 12
             -M4: JUGADOR TIRA CARTA NO PALO DE VALOR (REY BASTOS), LA IA TIENE PALO GANADOR (3 COPAS) Y
                    TAMBIEN TIENE CARTA DEL PALO DEL JUGADOR (AS BASTOS) SUPERIOR A LA CARTA DEL JUGADOR,
                    IA TIRA 3 DE COPAS YA QUE ES LA PRIMERA QUE ENCUENTRA.
         */
        int numJuga = Integer.parseInt(cartaElegidaJugador.getNumero());

        //carta final
        Cartas cartaIA = null;
        //carta para jugar con el mismo palo del jugador, pero no del palo ganador
        Cartas cartaIApaloJugador=null;
        //carta para que la IA sea palo ganador, pero no del jugador ni la del jugador será del palo
        Cartas cartaIApaloGanadorNoJugador=null;
        //carta para que la IA tenga carta que no sea palo ganador ni palo jugador
        Cartas cartaIAnoPaloNoJugadorPalo=null;
        //booleano para cuando el jugador tire carta palo
        boolean cartaJugadorPalo=false;
        //booleano para cuando el jugador tire carta palo y saber si la IA tiene carta palo o no
        boolean cartaIAPalo=false;

        //bucle para sacar el máximo de posibilidades para luego comprobarlas
        // y que la IA se lo mas eficaz posible
        for (int i = 0; i < cartasIA1.size(); i++) {
            //recorrer las cartas de la IA para buscar carta del mismo palo que la del jugador,
            //siempre y cuando la del jugador no sea palo ganador
            if (cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo()) &&
                    !cartaElegidaJugador.getPalo().equalsIgnoreCase(tvPalo.getText().toString())) {
                cartaIApaloJugador = cartasIA1.get(i);
                //sino, IA saca carta palo ganador, pero jugador no
            }else if (cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString()) &&
                    !cartaElegidaJugador.getPalo().equalsIgnoreCase(tvPalo.getText().toString())){
                cartaIApaloGanadorNoJugador = cartasIA1.get(i);
                //sino, IA saca carta que no sea palo ganador ni palo jugador
            }else if   (!cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString()) &&
                        !cartaElegidaJugador.getPalo().equalsIgnoreCase(tvPalo.getText().toString()) &&
                        !cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo())){
                cartaIAnoPaloNoJugadorPalo = cartasIA1.get(i);
                //sino, IA saca carta palo ganador para ganar a la carta de palo ganador del jugador
            }else if (cartaElegidaJugador.getPalo().equalsIgnoreCase(tvPalo.getText().toString()) &&
                      cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())){
                cartaIAPalo=true;
            }
        }
        //chivato para cuando el jugador tira carta palo
        if (cartaElegidaJugador.getPalo().equalsIgnoreCase(tvPalo.getText().toString())){
            cartaJugadorPalo = true;
        }


        //si el jugador no tira carta palo y tampoco AS,
        // la IA tirará si puede la carta que da puntos del mismo palo del jugador como para ganar la mano
        if (!cartaElegidaJugador.getPalo().equalsIgnoreCase(tvPalo.getText().toString()) && cartaIApaloJugador!=null) {
            if (numJuga != 1) {
                if (numJuga == 3) {
                    //recorrer las cartas de la IA y intentar sacar el AS (1) del mismo palo que del jugador
                    for (int i = 0; i < cartasIA1.size(); i++) {
                        if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 1 &&
                                cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo())) {
                            cartaIA = cartasIA1.get(i);
                            return cartaIA;

                            //si no tiene el as, buscará carta palo, si tampoco tiene, tirará carta del mismo palo que del jugador
                        }else  if (cartaIApaloGanadorNoJugador==null) {
                            //carta igual al palo jugador, intentar tiar carta que no de puntos
                            if (cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo()) &&
                                    Integer.parseInt(cartasIA1.get(i).getNumero()) <= 7) {
                                cartaIA = cartasIA1.get(i);

                                //return directo porque sera la mejor opcion posible
                                return cartaIA;

                                // tirara una superior 7 sino, TRES, sino AS, sino la primera que encuentre
                                //carta distinta del palo, mayor q 7
                            } else if (cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo()) &&
                                    Integer.parseInt(cartasIA1.get(i).getNumero()) > 7) {
                                cartaIA = cartasIA1.get(i);
                            }
                        } else {
                            //IA tira palo ganador para ganar al AS
                            cartaIA = cartaIApaloGanadorNoJugador;
                            return cartaIA;
                        }
                    }
                    return cartaIA;
                } else {
                    //si tira carta de valor y la IA tiene palo ganador, tratará de ganar la ronda
                    //MEJORAR ADVERTENCIA M4
                    if(numJuga > 7 && cartaIApaloGanadorNoJugador!=null) {
                        for (int i = 0; i < cartasIA1.size(); i++) {
                            //buscar carta menor de 7 que no sea el AS o TRES
                            if (Integer.parseInt(cartasIA1.get(i).getNumero()) != 3 &&
                                    Integer.parseInt(cartasIA1.get(i).getNumero()) != 1 &&
                                    Integer.parseInt(cartasIA1.get(i).getNumero()) < 7 &&
                                    cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())
                            ) {
                                cartaIA = cartasIA1.get(i);
                                //salida del algoritmo, ya que es la mejor carta posible
                                return cartaIA;
                                //IA no tiene menor de 7 q no sea 3 ni AS, buscara mayor que 7
                            } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) > 7 &&
                                    cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())) {
                                cartaIA = cartasIA1.get(i);
                                //IA tirara 3
                            } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 3 &&
                                    cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())) {
                                cartaIA = cartasIA1.get(i);
                            } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 1 &&
                                    cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())) {
                                cartaIA = cartasIA1.get(i);
                            }
                        }
                    }else {
                        //si jugador no tiene ni 1, 3 o > 7, IA buscara las mas alta del mismo palo que del jugador incluido 3 o AS
                        for (int i = 0; i < cartasIA1.size(); i++) {
                            //IA comprueba si tiene 1 o 3 del mismo palo q jugador
                            if ((Integer.parseInt(cartasIA1.get(i).getNumero()) == 3 || Integer.parseInt(cartasIA1.get(i).getNumero()) == 1) &&
                                    cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo())) {
                                cartaIA = cartasIA1.get(i);
                                //salida del algoritmo, ya que es la mejor carta posible
                                return cartaIA;
                                //IA no tiene 3 ni AS, buscara mayor que el numero del jugador de su mismo palo
                            } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) > numJuga &&
                                    cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo())) {
                                cartaIA = cartasIA1.get(i);
                                //IA tirara cualquier carta que tenga del palo del jugador (será inferior, perderá mano)
                            } else if (cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo()) &&
                                    Integer.parseInt(cartasIA1.get(i).getNumero()) < numJuga) {
                                cartaIA = cartasIA1.get(i);
                            }
                        }
                    }
                    return cartaIA;
                }
            }
            /*
                si el jugador no tira carta palo pero tira el AS,
                tirará una carta de bajo valor del palo q sea para perder la ronda a menos que tenga palo ganador
             */
            else{
                if (cartaIApaloGanadorNoJugador==null) {
                    for (int i = 0; i < cartasIA1.size(); i++) {
                        //carta distinta del palo, q no sea AS,3 o mayor de 7 a poder ser
                        if (!cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo()) &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) != 3 &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) != 1 &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) <= 7) {
                            cartaIA = cartasIA1.get(i);

                            //return directo porque sera la mejor opcion posible
                            return cartaIA;

                            // tirara una superior 7 sino, TRES, sino AS, sino la primera que encuentre
                            //carta distinta del palo, mayor q 7
                        } else if (!cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo()) &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) > 7) {
                            cartaIA = cartasIA1.get(i);

                            //carta igual a 3
                        } else if (!cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo()) &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) == 3) {
                            cartaIA = cartasIA1.get(i);

                            //carta igual a AS
                        } else if (!cartasIA1.get(i).getPalo().equalsIgnoreCase(cartaElegidaJugador.getPalo()) &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) == 1) {
                            cartaIA = cartasIA1.get(i);
                        }
                    }
                    return cartaIA;
                }else{
                    //IA tira palo ganador para ganar al AS
                    cartaIA=cartaIApaloGanadorNoJugador;
                    return cartaIA;
                }
            }
        }

        // SI JUGADOR USA CARTA QUE DA PUNTOS, PERO NO ES DEL PALO Y LA IA TIENE PALO GANADOR,
        // TIRARA CARTA PALO PARA GANAR LA RONDA
        if (!cartaElegidaJugador.getPalo().equalsIgnoreCase(tvPalo.getText().toString()) && cartaIApaloGanadorNoJugador!=null) {
            if (numJuga > 7 || numJuga==3 || numJuga==1) {
                for (int i = 0; i < cartasIA1.size(); i++) {
                    //q no sea AS,3 o mayor de 7 a poder ser, del palo ganador
                    if (Integer.parseInt(cartasIA1.get(i).getNumero()) <= 7 &&
                            Integer.parseInt(cartasIA1.get(i).getNumero())!=1 &&
                            Integer.parseInt(cartasIA1.get(i).getNumero())!=3 &&
                            cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString()) &&
                            cartaIApaloGanadorNoJugador!=null) {
                        cartaIA = cartasIA1.get(i);
                        return cartaIA;
                        //mayor que 7
                    }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) > 7 && cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString()) && cartaIApaloGanadorNoJugador!=null){
                        cartaIA = cartasIA1.get(i);

                        //igual a 3
                    }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 3 && cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString()) && cartaIApaloGanadorNoJugador!=null){
                        cartaIA = cartasIA1.get(i);

                        //igual a AS
                    }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 1 && cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString()) && cartaIApaloGanadorNoJugador!=null){
                        cartaIA = cartasIA1.get(i);

                    }
                }
                //si la carta no da puntos, pero IA tiene palo ganador, IA tirará una carta sin valor que no sea palo ganador
            }else if (!cartaElegidaJugador.getPalo().equalsIgnoreCase(tvPalo.getText().toString()) && cartaIApaloGanadorNoJugador!=null){
                for (int i = 0; i < cartasIA1.size(); i++) {
                    //q no sea AS,3 o mayor de 7 a poder ser, de cualquier palo menos ganador
                    if (Integer.parseInt(cartasIA1.get(i).getNumero()) <= 7 &&
                            Integer.parseInt(cartasIA1.get(i).getNumero())!=1 &&
                            Integer.parseInt(cartasIA1.get(i).getNumero())!=3 &&
                            !cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())) {
                        cartaIA = cartasIA1.get(i);

                        //sale del método devolviendo la mejor opción posible para esta situación
                        return cartaIA;
                        //mayor que 7
                    }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) > 7 && !cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())){
                        cartaIA = cartasIA1.get(i);

                        //igual a 3
                    }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 3 && !cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())){
                        cartaIA = cartasIA1.get(i);

                        //igual a AS
                    }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 1 && !cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())){
                        cartaIA = cartasIA1.get(i);

                    }
                }
            }
            //si jugador tira carta que no es palo ganador, IA no tiene palo ganador, ni tampoco tiene cartas del mismo palo
            // IA tira carta de bajo valor a ser posible de otro palo (IA pierde la mano)
        }else if (!cartaElegidaJugador.getPalo().equalsIgnoreCase(tvPalo.getText().toString()) && cartaIApaloGanadorNoJugador==null && cartaIAnoPaloNoJugadorPalo!=null){
            for (int i = 0; i < cartasIA1.size(); i++) {
                //q no sea AS,3 o mayor de 7 a poder ser
                if (Integer.parseInt(cartasIA1.get(i).getNumero()) <= 7 &&
                        Integer.parseInt(cartasIA1.get(i).getNumero())!=1 &&
                        Integer.parseInt(cartasIA1.get(i).getNumero())!=3) {
                    cartaIA = cartasIA1.get(i);

                    //sale del método devolviendo la mejor opción posible para esta situación
                    return cartaIA;
                    //mayor que 7
                }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) > 7){
                    cartaIA = cartasIA1.get(i);

                    //igual a 3
                }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 3){
                    cartaIA = cartasIA1.get(i);

                    //igual a AS
                }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 1){
                    cartaIA = cartasIA1.get(i);
                }
            }

            //si el jugador tira carta palo de bajo valor,
            // la IA tratará de tirar su peor carta que no sea palo ganador (IA pierde la mano)
        }else if (cartaJugadorPalo && !cartaIAPalo){
            for (int i = 0; i < cartasIA1.size(); i++) {
                //q no sea AS,3 o mayor de 7 a poder ser
                if (Integer.parseInt(cartasIA1.get(i).getNumero()) <= 7 &&
                        Integer.parseInt(cartasIA1.get(i).getNumero())!=1 &&
                        Integer.parseInt(cartasIA1.get(i).getNumero())!=3) {
                    cartaIA = cartasIA1.get(i);

                    //sale del método devolviendo la mejor opción posible para esta situación
                    return cartaIA;
                    //mayor que 7
                }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) > 7){
                    cartaIA = cartasIA1.get(i);

                    //igual a 3
                }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 3){
                    cartaIA = cartasIA1.get(i);

                    //igual a AS
                }else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 1){
                    cartaIA = cartasIA1.get(i);
                }
            }

            // si jugador tira carta palo de valor, la IA intentará sacar carta palo ganador para llevarse la mano
        }else if (cartaJugadorPalo && cartaIAPalo){
            if (numJuga==3||numJuga>7){
                for (int i=0; i <cartasIA1.size(); i++){
                    if (Integer.parseInt(cartasIA1.get(i).getNumero())>numJuga || Integer.parseInt(cartasIA1.get(i).getNumero())==1){
                        cartaIA = cartasIA1.get(i);
                        return cartaIA;
                    }
                }
                //si jugador no tira carta palo de valor, la IA tirará carta sin valor a poder ser de otro palo
            }else{
                for (int i = 0; i < cartasIA1.size(); i++) {
                    if (!cartasIA1.get(i).getPalo().equalsIgnoreCase(tvPalo.getText().toString())) {
                        //q no sea AS,3 o mayor de 7 a poder ser
                        if (Integer.parseInt(cartasIA1.get(i).getNumero()) <= 7 &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) != 1 &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) != 3) {
                            cartaIA = cartasIA1.get(i);

                            //sale del método devolviendo la mejor opción posible para esta situación
                            return cartaIA;
                            //mayor que 7
                        } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) > 7) {
                            cartaIA = cartasIA1.get(i);
                            //salir en caso de ser la última interacción
                            if (i==cartasIA1.size()-1){
                                return cartaIA;
                            }
                            //igual a 3
                        } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 3) {
                            cartaIA = cartasIA1.get(i);
                            //salir en caso de ser la última interacción
                            if (i==cartasIA1.size()-1){
                                return cartaIA;
                            }
                            //igual a AS
                        } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 1) {
                            cartaIA = cartasIA1.get(i);
                            //salir en caso de ser la última interacción
                            if (i==cartasIA1.size()-1){
                                return cartaIA;
                            }
                        }
                        //la IA tiene las 3 cartas palo, así que tirará la peor carta posible
                    }else{
                        //q no sea AS,3 o mayor de 7 a poder ser
                        if (Integer.parseInt(cartasIA1.get(i).getNumero()) <= 7 &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) != 1 &&
                                Integer.parseInt(cartasIA1.get(i).getNumero()) != 3) {
                            cartaIA = cartasIA1.get(i);

                            //sale del método devolviendo la mejor opción posible para esta situación
                            return cartaIA;
                            //mayor que 7
                        } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) > 7) {
                            cartaIA = cartasIA1.get(i);
                            //salir en caso de ser la última interacción
                            if (i==cartasIA1.size()-1){
                                return cartaIA;
                            }
                            //igual a 3
                        } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 3) {
                            cartaIA = cartasIA1.get(i);
                            //salir en caso de ser la última interacción
                            if (i==cartasIA1.size()-1){
                                return cartaIA;
                            }
                            //igual a AS
                        } else if (Integer.parseInt(cartasIA1.get(i).getNumero()) == 1) {
                            cartaIA = cartasIA1.get(i);
                            //salir en caso de ser la última interacción
                            if (i==cartasIA1.size()-1){
                                return cartaIA;
                            }
                        }
                    }
                }
            }
        }
        return cartaIA;
    }
}