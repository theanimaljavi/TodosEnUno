package com.javigu.todosenuno;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link inicio#newInstance} factory method to
 * create an instance of this fragment.
 */
public class inicio extends Fragment {
    VideoView vvLogo;
    Button btnInicio;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public inicio() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment inicio.
     */
    // TODO: Rename and change types and number of parameters
    public static inicio newInstance(String param1, String param2) {
        inicio fragment = new inicio();
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
    public void onPause() {
        super.onPause();
        vvLogo.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(vvLogo != null){
            vvLogo.start();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_inicio, container, false);
        //asignar los campos con los id's
        vvLogo = view.findViewById(R.id.vvLogo);
        btnInicio = view.findViewById(R.id.btnInicio);

        //reproducir el video
        String path = "android.resource://com.javigu.todosenuno/" + R.raw.video_logo_teu;
        vvLogo.setVideoURI(Uri.parse(path));
        vvLogo.start();
        vvLogo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                vvLogo.start();
            }
        });

        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cargar actividad elegir juego
                Intent intent = new Intent (v.getContext(), ElegirJuego.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 0);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}