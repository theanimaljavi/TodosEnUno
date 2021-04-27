package com.javigu.todosenuno.Brisca;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.MainActivity;
import com.javigu.todosenuno.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link briscaResultado#newInstance} factory method to
 * create an instance of this fragment.
 */
public class briscaResultado extends Fragment {
    LottieAnimationView lottie_trophy;
    Button btnSeguirJugando, btnElegirJuego;
    TextView tvGanador,tvJugador,tvIA;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;

    public briscaResultado() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment briscaResultado.
     */
    // TODO: Rename and change types and number of parameters
    //param1 será 1 o 0
    //param2 serán los puntos del jugador
    //param3 serán los puntos de la IA
    // 0 ganó jugador, 1 ganó IA
    public static briscaResultado newInstance(String param1, String param2, String param3) {
        briscaResultado fragment = new briscaResultado();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.brisca_fragment_resultado, container, false);
        //asociar botones
        btnSeguirJugando = view.findViewById(R.id.btnSeguirJugando);
        btnElegirJuego = view.findViewById(R.id.btnElegirJuego);
        tvGanador = view.findViewById(R.id.tvGanador);
        tvJugador = view.findViewById(R.id.tvFragmentPuntosJugador);
        tvIA = view.findViewById(R.id.tvFragmentPuntosIA);
        //asociar lottie
        lottie_trophy = view.findViewById(R.id.lottieTrofeo);
        lottie_trophy.setVisibility(View.INVISIBLE);

        //mostrar los puntos del juego
        tvJugador.setText("Puntos: "+mParam2);
        tvIA.setText("Puntos: "+mParam3);

        //parsear las dos String pasadas por el activity de la brisca
        int puntosJugador = Integer.parseInt(mParam2);
        int puntosIA = Integer.parseInt(mParam3);
        // 0 ganó jugador, 1 ganó IA
        if (puntosJugador>puntosIA) {
            lottie_trophy.setVisibility(View.VISIBLE);
            tvGanador.setText("Has ganado, enhorabuena!");
        }else if (puntosIA>puntosJugador){
            lottie_trophy.setVisibility(View.INVISIBLE);
            tvGanador.setText("Has perdido, siga probando");
        }else{
            tvGanador.setText("Empate!");
        }

        //cargar el activity de elegir juego
        btnElegirJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ElegirJuego.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        //empezará de nuevo el juego de la brisca, pero mantiendo los puntos anteriores
        btnSeguirJugando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ElegirJuego.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //puntos jugador
                i.putExtra("brisca_resultadofragmentJugador", String.valueOf(mParam2));
                //puntos IA
                i.putExtra("brisca_resultadofragmentIA", String.valueOf(mParam3));
                startActivity(i);
            }
        });
        return view;
    }
}