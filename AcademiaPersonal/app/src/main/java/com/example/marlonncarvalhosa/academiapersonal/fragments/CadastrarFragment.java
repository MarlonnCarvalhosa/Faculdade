package com.example.marlonncarvalhosa.academiapersonal.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.marlonncarvalhosa.academiapersonal.R;
import com.example.marlonncarvalhosa.academiapersonal.utils.FragmentoUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadastrarFragment extends Fragment {

    private Button irExercicios;


    public CadastrarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastrar, container, false);

        adicionarExercicios(view);

        return view;
    }

    public void adicionarExercicios(View view) {

        irExercicios = (Button) view.findViewById(R.id.irExercicios);
        irExercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentoUtils.replace(getActivity(), new ExerciciosFragment());

            }

        });

    }

}
