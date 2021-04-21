package com.javigu.todosenuno.Ahorcado;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.MainActivity;
import com.javigu.todosenuno.R;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link jugarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class jugarFragment extends Fragment {
    //variables botones
    ImageButton btnAtras, btnReintentar, btnAyuda;
    static Button btnEnviar;

    TextView tvTematica;
    String palabraGanadora = "", palabra = "",  guiones2 = "", tematicaElegida;
    ImageView img;
    GestionFotos fotos;
    String[] guiones;
    TextView tvPalabra, tvAcierto, tvError;
    EditText etLetra;
    int dosJugadores = 0,f = 0,imagenes = 0,errores = 0, aciertos = 0;
    //Array que almacena las palabras de las tematicas
    String[] palabras;
    //fragmentos
    PerdedorFragment fgPerdedor;
    GanadorFragment fgGanador;

    //Random
    static Random random = new Random();

    // TODO: Rename parameter arguments, choose names that match
    // este fragmento tendrá 2 parámetros que será la temática elegida por el usuario o si son dos jugadores
    //en cualquier caso un parametro tendra que ser null/0
    private static final String ARG_PARAM1 = "tematica";
    private static final String ARG_PARAM2 = "jugadores";

    // TODO: Rename and change types of parameters
    private String tematica;
    private String jugadores;

    public jugarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment jugarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static jugarFragment newInstance(String tematica, String jugadores) {
        jugarFragment fragment = new jugarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, tematica);
        args.putString(ARG_PARAM2, jugadores);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tematica = getArguments().getString(ARG_PARAM1);
            jugadores = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //colocar iamgen base
        ViewGroup vistaFotos = (ViewGroup) inflater.inflate(R.layout.ahorcado_fragment_jugar, container, false);

        //orientacion fijada en portrait
        getActivity().setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvPalabra = vistaFotos.findViewById(R.id.tvPalabra);
        img = vistaFotos.findViewById(R.id.ivAhorcado);
        etLetra = vistaFotos.findViewById(R.id.etLetra);
        tvTematica = vistaFotos.findViewById(R.id.tvTematica);
        // Inflate the layout for this fragment
        //cargar las fotos
        fotos = new GestionFotos();
        fotos.cargarFotos(getContext());

        //guardar variable dos jugadores
        if (jugadores!=null) {
            dosJugadores = Integer.parseInt(jugadores);
        }
        //url image
        String URL = GestionFotos.FOTOS.get(0).getUrl();
        //Loading image from below url into imageView
        Picasso.with(getContext())
                .load(URL)
                .placeholder(R.drawable.loading)
                .into(img);

        //si hay dos jugadores obviaremos el tener que elegir tematica
        if (dosJugadores<=0 ) {
            //tematica elegida
            tematicaElegida = tematica;
            //almacenar el array de la temática escogida y extraer una palabra random de ella
            if (tematicaElegida.equalsIgnoreCase("animales")) {
                palabras = getResources().getStringArray(R.array.arrayAnimales);
            } else if (tematicaElegida.equalsIgnoreCase("alimentacion")) {
                palabras = getResources().getStringArray(R.array.arrayAlimentacion);
            } else if (tematicaElegida.equalsIgnoreCase("tecnologia")) {
                palabras = getResources().getStringArray(R.array.arrayTecnologia);
            } else if (tematicaElegida.equalsIgnoreCase("juegos")) {
                palabras = getResources().getStringArray(R.array.arrayjuegos);
            } else if (tematicaElegida.equalsIgnoreCase("Deportes")) {
                palabras = getResources().getStringArray(R.array.arrayDeportes);
            } else if (tematicaElegida.equalsIgnoreCase("Profesiones")) {
                palabras = getResources().getStringArray(R.array.arrayProfesiones);
            }
            //rellenar la palabra de forma aleatoria
            palabra = palabras[random.nextInt(palabras.length)];
            //cambiar el banner de arriba por la temática
            tvTematica.setText("Temática: "+tematica);
        }else{
            //extraer la palabra elegida por el otro jugador
            palabra = tematica;
        }


        /* comprobamos que no se repite la palabra, cambiar la palabra de no ser así
        while(palabra.equals(palabraGanadora)){
            palabra = palabras[random.nextInt(palabras.length)];
            palabraGanadora = palabra;
        }
         */

        //añadir la nueva palabra
        palabraGanadora = palabra;
        //tamaño de guiones igual de la palabra
        guiones = new String[palabraGanadora.length()];
        // poner guiones respecto lo q tenga la palabra
        for (int i = 0; i < palabraGanadora.length(); i++) {
            if (i == guiones.length) {
                guiones[i] = "_";
                guiones2 += "_";
            } else {
                guiones[i] = "_ ";
                guiones2 += "_ ";
            }
        }
        //setear los guiones
        tvPalabra.setText(guiones2);
        return vistaFotos;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //imagen del ahorcado
        ImageView ivAhorcado = view.findViewById(R.id.ivAhorcado);

        btnAtras = view.findViewById(R.id.btnAtras);
        btnReintentar = view.findViewById(R.id.btnReintentar);
        btnEnviar = view.findViewById(R.id.btnEnviar);
        btnAyuda = view.findViewById(R.id.ibAyuda);

        //var texto
        TextView tvUsadas = view.findViewById(R.id.tvUsadas);
        tvAcierto = view.findViewById(R.id.tvAciertos);
        tvError = view.findViewById(R.id.tvErrores);

        //ventana de ayuda
        btnAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //abrir la ayuda del juego con un nuevo fragment
                AyudaFragment fgAyuda = new AyudaFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contenedorAyudaJugar, fgAyuda.newInstance(palabraGanadora,String.valueOf(dosJugadores))).commit();
            }
        });

        //elegir juego
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), ElegirJuego.class);
                startActivityForResult(intent, 0);
            }
        });

        //elegir tematica o seleccionar otra palabra (solo opcion dos jugadores)
        btnReintentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dosJugadores<=0) {
                    Intent intent = new Intent(v.getContext(), ElegirTematica.class);
                    startActivityForResult(intent, 0);
                }else{
                    btnEnviar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(v.getContext(), DosJugadores.class);
                    startActivityForResult(intent, 0);
                }
            }
        });

        // boton q comprobara cualquier accion con la letra introducida
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //letras usadas
                String caracter = etLetra.getText().toString().toUpperCase();
                boolean bUsada = true;
                boolean siCaracter = true;
                String siUsada = tvUsadas.getText().toString();
                //si no hay ninguna letra, no podra comprobar
                if (etLetra.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "No has introducido ninguna letra.", Toast.LENGTH_LONG).show();
                } else {
                    //comporbar que no se pueda repetir el mismo carácter
                    for (int u = 0; u < siUsada.length(); u++) {
                        if (caracter.equalsIgnoreCase(String.valueOf(siUsada.charAt(u)))) {
                            Toast.makeText(getContext(), "La letra introducida está repetida, por favor, utilice otra", Toast.LENGTH_LONG).show();

                            bUsada = false;
                        }
                    }

                    if (bUsada) {
                        //colocar letras usadas
                        tvUsadas.setText(tvUsadas.getText().toString() + caracter);
                    }

                    //cambiar los guiones por los caracteres
                    for (int i = 0; i < palabraGanadora.length(); i++) {
                        if (caracter.equalsIgnoreCase(String.valueOf(palabraGanadora.charAt(i)))) {
                            //si ya tenemos un acierto y la ultima letra usada coincide con la que comprobamos y es correcta, no sumara acierto.
                           /*if (aciertos>0){
                                f = tvUsadas.getText().toString().length();

                                if (!String.valueOf(tvUsadas.getText().toString().charAt(f-1)).equalsIgnoreCase(etLetra.getText().toString())){
                                    aciertos++;
                                }
                            }else {
                            }*/

                            guiones[i] = String.valueOf(palabraGanadora.charAt(i));
                            guiones2 = "";
                            for (int j = 0; j < palabraGanadora.length(); j++) {
                                guiones2 += guiones[j] + " ";
                            }
                            tvPalabra.setText(guiones2.toUpperCase());
                            //quitar espacios a la palabra final
                            palabra = guiones2.replaceAll(" ", "");
                            //si la palabra es correta, pantalla de win
                            if (palabra.equalsIgnoreCase(palabraGanadora)) {
                                btnEnviar.setVisibility(View.INVISIBLE);
                                //cargar fragmento ganador
                                fgGanador = new GanadorFragment();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.jugarFragment, fgGanador).commit();

                            }
                            siCaracter = false;
                        }
                    }


                    // si falla cambia la imagen y añade un fallo
                    if (bUsada && siCaracter) {
                        imagenes++;
                        //url image
                        String URL = GestionFotos.FOTOS.get(imagenes).getUrl();
                        //Loading image from below url into imageView
                        Picasso.with(getContext())
                                .load(URL)
                                .placeholder(R.drawable.loading)
                                .into(img);
                        errores++;
                        String totalAhorcado = String.valueOf(GestionFotos.FOTOS.size() - 1);
                        tvError.setText(String.valueOf(errores + "/" + totalAhorcado));
                        //Transiciones de imágenes
                        Animation transicion = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                        ivAhorcado.startAnimation(transicion);
                        //si ya estan todas las imagenes, el usuario pierde
                        if (imagenes == 7) {
                            btnEnviar.setVisibility(View.INVISIBLE);
                            //cargar fragmento perdedor
                            fgPerdedor = new PerdedorFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.jugarFragment, fgPerdedor).commit();

                        }
                    } else if (bUsada && !siCaracter) {
                        aciertos++;
                        tvAcierto.setText(String.valueOf(aciertos));
                    }
                }
                //limpiar campo de la letra que se ha comprabado
                etLetra.setText("");
            }

        });
    }

}
