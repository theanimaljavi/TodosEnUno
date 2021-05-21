package com.javigu.todosenuno.ParejasPerfectas;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.javigu.todosenuno.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link parejas_fragment_normal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class parejas_fragment_normal extends Fragment {
    private ImageButton ib1,ib2,ib3,ib4,ib5,ib6,ib7,ib8,ib9,ib10,ib11,ib12,ibAyuda;
    private Button btnReinciar, btnElegirDificultad;
    private String[] frutas, animales;
    private TextView tvErorres, tvFinal;
    private String[] url;
    private int ganador;
    private int contador=0, errores;
    private int id1 = 0,id2=0;
    private Handler handler = new Handler();
    private boolean juegofinalizado;
    private int botonRepetido;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public parejas_fragment_normal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment parejas_fragment_normal.
     */
    // TODO: Rename and change types and number of parameters
    public static parejas_fragment_normal newInstance(String param1, String param2) {
        parejas_fragment_normal fragment = new parejas_fragment_normal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.parejas_fragment_normal, container, false);

        ib1 = view.findViewById(R.id.ibNormal1);
        ib2 = view.findViewById(R.id.ibNormal2);
        ib3 = view.findViewById(R.id.ibNormal3);
        ib4 = view.findViewById(R.id.ibNormal4);
        ib5 = view.findViewById(R.id.ibNormal5);
        ib6 = view.findViewById(R.id.ibNormal6);
        ib7 = view.findViewById(R.id.ibNormal7);
        ib8 = view.findViewById(R.id.ibNormal8);
        ib9 = view.findViewById(R.id.ibNormal9);
        ib10 = view.findViewById(R.id.ibNormal10);
        ib11 = view.findViewById(R.id.ibNormal11);
        ib12 = view.findViewById(R.id.ibNormal12);
        ibAyuda = view.findViewById(R.id.ibAyudaParejasFacil);
        btnReinciar = view.findViewById(R.id.btnParejasFacilReiniciar);
        btnReinciar.setVisibility(View.GONE);
        btnElegirDificultad = view.findViewById(R.id.btnParejasFacilElegirDificultad);
        btnElegirDificultad.setVisibility(View.GONE);
        tvFinal = view.findViewById(R.id.tvParejasFacilFinal);
        tvFinal.setVisibility(View.GONE);
        tvErorres = view.findViewById(R.id.tvParejasFacilErrores);
        errores = 0;
        tvErorres.setText("Errores: "+errores+"/5");

        juegofinalizado = false;
        botonRepetido = 0;
        //array para saber si ha ganado el juego
        ganador = 0;
        //array de booleanos para guardar cuando tenemos pulsado un ImageButton
        url = new String[12];
        //array de string con todas las frutas y animales
        frutas = new String[]{  "parejas_naranjas","parejas_fresa","parejas_sandia","parejas_uvas","parejas_kiwi",
                                "parejas_aguacate","parejas_mango","parejas_papaya","parejas_melon","parejas_platano",
                                "parejas_coco","parejas_pinya","parejas_manzana"};
        animales = new String[]{"parejas_ciervo","parejas_conejo","parejas_erizo","parejas_flamenco","parejas_jaguar",
                                "parejas_mariposa","parejas_monkey","parejas_pajaro","parejas_pajaro_maleo","parejas_panda",
                                "parejas_perezoso","parejas_pez_payaso","parejas_zebra"};

        //crear el array de parejas
        // serán 3 frutas repetidas 1 vez, aleatorias entre todas las opciones del array 'frutas'
        String[] parejas = new String[12];
        //array que contendrá las 3 frutas
        String[] fruta = new String[]{"","",""};
        String[] animal = new String[]{"","",""};
        Random r = new Random();
        boolean frutaNoRepetida = false;
        boolean animalNoRepetido = false;
        // 3 interacciones
        // en cada una ellas ponemos repetida la imagen de una fruta en diferente posicion
        for (int i=0; i<3; i++){
            //sacar una fruta de forma aleatoria
            fruta[i] = frutas[r.nextInt(13)];

            //obtener primer posicion
            int posicion = r.nextInt(12);
            //comprobar q la fruta no se repita
            // el bucle hará 3 interaciones
            // si no coincide, se suma 1 al contador
            // si contador es igual a 2 el booleano será true
            // el bucle se repetirá hasta ser true
            while (!frutaNoRepetida) {
                for (int j = 0; j < 3; j++) {
                    if (!fruta[j].equalsIgnoreCase(fruta[i])) {
                        contador++;
                    }
                    //comporbar si contador es igual a 2 en la última interacción
                    if (contador == 2 && j == 2 ) {
                        frutaNoRepetida = true;
                        break;
                    }
                    //añadir otra fruta en la última interacción si se repite la fruta en el array 'fruta'
                    if (j == 2){
                        fruta[i] = frutas[r.nextInt(13)];
                        //resetear contador
                        contador=0;
                    }
                }
            }
            //añadir fruta cuando la posicion sea null
            if (parejas[posicion] == null) {
                //añadir la fruta
                parejas[posicion] = fruta[i];
            }else{
                //si ya tiene una fruta en esa posicion, se buscara una posicion null
                while (parejas[posicion] != null){
                    posicion = r.nextInt(12);
                }
                parejas[posicion] = fruta[i];
            }

            //obtener otra posición
            int segundaPosicion = r.nextInt(12);

            //añadir fruta cuando la segundaPosicion sea null
            if (parejas[segundaPosicion] == null) {
                //añadir la fruta en distinta posición
                parejas[segundaPosicion] = fruta[i];
            }else{
                //si ya tiene una fruta en esa posicion, se buscara una posicion null
                while (parejas[segundaPosicion] != null){
                    segundaPosicion = r.nextInt(12);
                }
                parejas[segundaPosicion] = fruta[i];
            }
            //resetear contador
            contador=0;
            //resetear booleano
            frutaNoRepetida = false;
        }

        // 3 interacciones
        // en cada una ellas ponemos repetida la imagen de un animal en diferente posicion
        for (int i=0; i<3; i++){
            //sacar un animal de forma aleatoria
            animal[i] = animales[r.nextInt(13)];

            //obtener primer posicion
            int posicion = r.nextInt(12);
            //comprobar q el animal no se repita
            // el bucle hará 3 interaciones
            // si no coincide, se suma 1 al contador
            // si contador es igual a 2 el booleano será true
            // el bucle se repetirá hasta ser true
            while (!animalNoRepetido) {
                for (int j = 0; j < 3; j++) {
                    if (!animal[j].equalsIgnoreCase(animal[i])) {
                        contador++;
                    }
                    //comporbar si contador es igual a 2 en la última interacción
                    if (contador == 2 && j == 2 ) {
                        animalNoRepetido = true;
                        break;
                    }
                    //añadir otra fruta en la última interacción si se repite la fruta en el array 'fruta'
                    if (j == 2){
                        animal[i] = animales[r.nextInt(13)];
                        //resetear contador
                        contador=0;
                    }
                }
            }
            //añadir fruta cuando la posicion sea null
            if (parejas[posicion] == null) {
                //añadir la fruta
                parejas[posicion] = animal[i];
            }else{
                //si ya tiene una fruta en esa posicion, se buscara una posicion null
                while (parejas[posicion] != null){
                    posicion = r.nextInt(12);
                }
                parejas[posicion] = animal[i];
            }

            //obtener otra posición
            int segundaPosicion = r.nextInt(12);

            //añadir fruta cuando la segundaPosicion sea null
            if (parejas[segundaPosicion] == null) {
                //añadir la fruta en distinta posición
                parejas[segundaPosicion] = animal[i];
            }else{
                //si ya tiene una fruta en esa posicion, se buscara una posicion null
                while (parejas[segundaPosicion] != null){
                    segundaPosicion = r.nextInt(12);
                }
                parejas[segundaPosicion] = animal[i];
            }
            //resetear contador
            contador=0;
            //resetear booleano
            animalNoRepetido = false;
        }


        //acciones para todos los ImageView
        // cambiar la imgaen cuando se pulsa cualquiera,
        // guardar las url en un array para comprobar en comprobarParejas()
        // comprobar si coinciden y actualizar los errores si hiciera falta
        ib1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[0] = parejas[0];
                //no se puede almacenar una String = 'R.drawable.X' siendo 'X' una imagen previamente guardada
                // lo que se hace es recoger el ID que tiene esa imagen con el getIdentifier de la manera siguiente:
                int resID = getResources().getIdentifier(url[0] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib1);
                if (botonRepetido != 1) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 1;
                }
                comprobarParejas();
            }
        });

        ib2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[1] = parejas[1];
                int resID = getResources().getIdentifier(url[1] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib2);
                if (botonRepetido != 2) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 2;
                }
                comprobarParejas();
            }
        });

        ib3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[2] = parejas[2];
                int resID = getResources().getIdentifier(url[2] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib3);
                if (botonRepetido != 3) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 3;
                }
                comprobarParejas();
            }
        });

        ib4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[3] = parejas[3];
                int resID = getResources().getIdentifier(url[3] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib4);
                if (botonRepetido != 4) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 4;
                }
                comprobarParejas();
            }
        });

        ib5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[4] = parejas[4];
                int resID = getResources().getIdentifier(url[4] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib5);
                if (botonRepetido != 5) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 5;
                }
                comprobarParejas();
            }
        });

        ib6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[5] = parejas[5];
                int resID = getResources().getIdentifier(url[5] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib6);
                if (botonRepetido != 6) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 6;
                }
                comprobarParejas();
            }
        });

        ib7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[6] = parejas[6];
                int resID = getResources().getIdentifier(url[6] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib7);
                if (botonRepetido != 7) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 7;
                }
                comprobarParejas();
            }
        });

        ib8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[7] = parejas[7];
                int resID = getResources().getIdentifier(url[7] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib8);
                if (botonRepetido != 8) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 8;
                }
                comprobarParejas();
            }
        });

        ib9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[8] = parejas[8];
                int resID = getResources().getIdentifier(url[8] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib9);
                if (botonRepetido != 9) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 9;
                }
                comprobarParejas();
            }
        });

        ib10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[9] = parejas[9];
                int resID = getResources().getIdentifier(url[9] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib10);
                if (botonRepetido != 10) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 10;
                }
                comprobarParejas();
            }
        });

        ib11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[10] = parejas[10];
                int resID = getResources().getIdentifier(url[10] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib11);
                if (botonRepetido != 11) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 11;
                }
                comprobarParejas();
            }
        });

        ib12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url[11] = parejas[11];
                int resID = getResources().getIdentifier(url[11] , "drawable", getActivity().getPackageName());
                Picasso.with(getActivity().getApplicationContext())
                        .load(resID).into(ib12);
                if (botonRepetido != 12) {
                    //aumentar el contador si el botón no se repite
                    contador++;
                    botonRepetido = 12;
                }
                comprobarParejas();
            }
        });

        ibAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parejas_fragment_ayuda fgAyuda = new parejas_fragment_ayuda();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentParejasAyuda, fgAyuda).commit();
            }
        });

        //reinciar el juego dificultad Normal de nuevo
        btnReinciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), parejasPerfectas_jugar.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("parejas_dificultad", "normal");
                startActivityForResult(intent,0);
            }
        });

        btnElegirDificultad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), parejasPerfectas_dificultad.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent,0);
            }
        });
        return view;
    }

    //método para comprobar dos ImageButton
    // si los imagebutton coinciden se eliminarán del juego, si no coinciden se añadira un error
    private void comprobarParejas(){
        if (contador == 2){
            //resetear variable
            contador = 0;
            botonRepetido = 0;
            //comprobar las url
            // guardar las 2 url's y sus posiciones
            String imagen1 = null,imagen2 = null;
            //recorrer un bucle para guardar las dos urls y sus botones
            for (int i=0;i<12;i++){
                //si la url no es null guardamos imagen y boton
                if (url[i] != null && contador == 0) {
                    imagen1 = url[i];
                    id1 = i;
                    //resetear variables
                    contador++;
                    url[i] = null;
                } else if (url[i] != null && contador == 1) {
                    imagen2 = url[i];
                    id2 = i;
                    //resetear variables
                    contador = 0;
                    url[i] = null;
                    break;
                }
            }
            //comprobar si las imagenes coinciden
            if (imagen1.equalsIgnoreCase(imagen2)){
                // manejador para crear transición de segundos
                final Runnable rBotonesQuitar = new Runnable() {
                    public void run() {
                        //desactivar los botones que correspondan
                        if (id1 == 0 || id2 == 0) {
                            ib1.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 1 || id2 == 1) {
                            ib2.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 2 || id2 == 2) {
                            ib3.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 3 || id2 == 3) {
                            ib4.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 4 || id2 == 4) {
                            ib5.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 5 || id2 == 5) {
                            ib6.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 6 || id2 == 6) {
                            ib7.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 7 || id2 == 7) {
                            ib8.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 8 || id2 == 8) {
                            ib9.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 9 || id2 == 9) {
                            ib10.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 10 || id2 == 10) {
                            ib11.setVisibility(View.GONE);
                            ganador++;
                        }
                        if (id1 == 11 || id2 == 11) {
                            ib12.setVisibility(View.GONE);
                            ganador++;
                        }
                        //si ganador es igual a 6, significa q ha ganador el juego
                        if (ganador == 12){
                            juegofinalizado = true;
                            tvFinal.setVisibility(View.VISIBLE);
                            tvFinal.setTextColor(getResources().getColor(R.color.dark_green));
                            tvFinal.setText("Has Ganado!");
                            btnReinciar.setVisibility(View.VISIBLE);
                            btnElegirDificultad.setVisibility(View.VISIBLE);
                            //desactivar botones ya que no se puede seguir jugando
                            ib1.setEnabled(false);
                            ib2.setEnabled(false);
                            ib3.setEnabled(false);
                            ib4.setEnabled(false);
                            ib5.setEnabled(false);
                            ib6.setEnabled(false);
                            ib7.setEnabled(false);
                            ib8.setEnabled(false);
                            ib9.setEnabled(false);
                            ib10.setEnabled(false);
                            ib11.setEnabled(false);
                            ib12.setEnabled(false);
                        }
                    }
                };
                //transicion de segundo y medio segundos
                handler.postDelayed(rBotonesQuitar, 1200);
            }else{
                //obtener la imagen de la carta trasera
                int resID = getResources().getIdentifier("parejas_perfectas_carta", "drawable", getActivity().getPackageName());
                // manejador para crear transición de segundos
                final Runnable rBotonesTrasera = new Runnable() {
                    public void run() {
                        //devolver la carta trasera a la pareja
                        if (id1 == 0 || id2 == 0){
                            ib1.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 1 || id2 == 1){
                            ib2.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 2 || id2 == 2){
                            ib3.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 3 || id2 == 3){
                            ib4.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 4 || id2 == 4){
                            ib5.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 5 || id2 == 5){
                            ib6.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 6 || id2 == 6){
                            ib7.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 7 || id2 == 7){
                            ib8.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 8 || id2 == 8){
                            ib9.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 9 || id2 == 9){
                            ib10.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 10 || id2 == 10){
                            ib11.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                        if (id1 == 11 || id2 == 11){
                            ib12.setImageResource(R.drawable.parejas_perfectas_carta);
                        }
                    }
                };
                //transicion de segundo y medio segundos
                handler.postDelayed(rBotonesTrasera, 1200);

                //si no coinciden, se suma error
                errores += 1;
                tvErorres.setText("Errores: "+errores+"/5");
                //si lleva 3 errores, el juego se dará por finalizado
                if (errores == 5){
                    juegofinalizado = true;
                    tvFinal.setVisibility(View.VISIBLE);
                    tvFinal.setText("Has Perdido!");
                    btnReinciar.setVisibility(View.VISIBLE);
                    btnElegirDificultad.setVisibility(View.VISIBLE);
                    //desactivar botones ya que no se puede seguir jugando
                    ib1.setEnabled(false);
                    ib2.setEnabled(false);
                    ib3.setEnabled(false);
                    ib4.setEnabled(false);
                    ib5.setEnabled(false);
                    ib6.setEnabled(false);
                    ib7.setEnabled(false);
                    ib8.setEnabled(false);
                    ib9.setEnabled(false);
                    ib10.setEnabled(false);
                    ib11.setEnabled(false);
                    ib12.setEnabled(false);
                }
            }
            //si el juego no ha terminado se desactivan los botones y se activan pasado un tiempo
            if (!juegofinalizado) {
                //desactivar botones ya que no se puede seguir jugando
                ib1.setEnabled(false);
                ib2.setEnabled(false);
                ib3.setEnabled(false);
                ib4.setEnabled(false);
                ib5.setEnabled(false);
                ib6.setEnabled(false);
                ib7.setEnabled(false);
                ib8.setEnabled(false);
                ib9.setEnabled(false);
                ib10.setEnabled(false);
                ib11.setEnabled(false);
                ib12.setEnabled(false);
                //activar botones de nuevo al final de las transiciones
                final Runnable rBotonesActivar = new Runnable() {
                    public void run() {
                        //desactivar botones ya que no se puede seguir jugando
                        ib1.setEnabled(true);
                        ib2.setEnabled(true);
                        ib3.setEnabled(true);
                        ib4.setEnabled(true);
                        ib5.setEnabled(true);
                        ib6.setEnabled(true);
                        ib7.setEnabled(true);
                        ib8.setEnabled(true);
                        ib9.setEnabled(true);
                        ib10.setEnabled(true);
                        ib11.setEnabled(true);
                        ib12.setEnabled(true);
                    }
                };
                handler.postDelayed(rBotonesActivar, 1700);
            }
            //si contador no es 2, se desactivará el botón que ya esta pulsado
        }else{
            //desactivar los botones que correspondan
            if (botonRepetido == 1) {
                ib1.setEnabled(false);
            }
            if (botonRepetido == 2) {
                ib2.setEnabled(false);
            }
            if (botonRepetido == 3) {
                ib3.setEnabled(false);
            }
            if (botonRepetido == 4) {
                ib4.setEnabled(false);
            }
            if (botonRepetido == 5) {
                ib5.setEnabled(false);
            }
            if (botonRepetido == 6) {
                ib6.setEnabled(false);
            }
            if (botonRepetido == 7) {
                ib7.setEnabled(false);
            }
            if (botonRepetido == 8) {
                ib8.setEnabled(false);
            }
            if (botonRepetido == 9) {
                ib9.setEnabled(false);
            }
            if (botonRepetido == 10) {
                ib10.setEnabled(false);
            }
            if (botonRepetido == 11) {
                ib11.setEnabled(false);
            }
            if (botonRepetido == 12) {
                ib12.setEnabled(false);
            }
        }
    }
}