package com.javigu.todosenuno.Ahorcado;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.javigu.todosenuno.ElegirJuego;
import com.javigu.todosenuno.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerdedorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerdedorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerdedorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerdedorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerdedorFragment newInstance(String param1, String param2) {
        PerdedorFragment fragment = new PerdedorFragment();
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
        return inflater.inflate(R.layout.ahorcado_fragment_perdedor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnHome = view.findViewById(R.id.btnPerdedorHome);
        MediaPlayer reproductor = MediaPlayer.create(getContext(),R.raw.derrota);
        reproductor.start();

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reproductor.isPlaying()){
                    reproductor.stop();
                }
                //cargar actividad elegir juego
                Intent intent = new Intent (v.getContext(), ElegirJuego.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}