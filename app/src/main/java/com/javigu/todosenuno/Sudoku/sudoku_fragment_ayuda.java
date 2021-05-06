package com.javigu.todosenuno.Sudoku;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.javigu.todosenuno.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sudoku_fragment_ayuda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sudoku_fragment_ayuda extends Fragment {
    ImageButton ibSalir;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public sudoku_fragment_ayuda() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sudoku_fragment_ayuda.
     */
    // TODO: Rename and change types and number of parameters
    public static sudoku_fragment_ayuda newInstance(String param1, String param2) {
        sudoku_fragment_ayuda fragment = new sudoku_fragment_ayuda();
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
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.sudoku_ayuda, container, false);
        ibSalir = view.findViewById(R.id.ibCerrarAyudaSudoku);

        //LOS BOTONES DE COMPROBAR Y RESOLVER SIGUEN APARECIENDO CUANDO SE ABRE EL FRAGMENTO DE AYUDA
        //descativar botones del juego principal
        sudoku_jugar.btnR.setVisibility(View.GONE);
        sudoku_jugar.btnC.setVisibility(View.GONE);

        ibSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sudoku_jugar.btnR.setVisibility(View.VISIBLE);
                sudoku_jugar.btnC.setVisibility(View.VISIBLE);

                //cerrar fragment ayuda
                getActivity().getSupportFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.contenedorFragmentSudoku)).commit();
            }
        });

        return view;
    }
}