package com.example.marlonncarvalhosa.academiapersonal.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marlonncarvalhosa.academiapersonal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciciosFragment extends Fragment {


    public ExerciciosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercicios, container, false);

        return view;
    }

}
