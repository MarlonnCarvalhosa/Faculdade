package com.marlonncarvalhosa.mamaeeuquero.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriasFragment extends Fragment {

    private CardView acessorios;
    private CardView brinquedos;
    private CardView calcados;
    private CardView enxoval;
    private CardView escolar;
    private CardView moveis;
    private CardView roupas;



    public CategoriasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_categorias, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        idCamppo(view);

        clickAcessorios(view);
        clickBrinquedo(view);
        clickCalcados(view);
        clickEnxoval(view);
        clickEscolar(view);
        clickMoveis(view);
        clickRoupas(view);

        return view;

    }

    private void idCamppo(View view) {

        acessorios = (CardView) view.findViewById(R.id.acessorios);
        brinquedos = (CardView) view.findViewById(R.id.brinquedos);
        calcados = (CardView) view.findViewById(R.id.calcados);
        enxoval = (CardView) view.findViewById(R.id.enxoval);
        escolar = (CardView) view.findViewById(R.id.escolar);
        moveis = (CardView) view.findViewById(R.id.moveis);
        roupas = (CardView) view.findViewById(R.id.roupas);

    }

    public void clickAcessorios(View view) {

        acessorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewText("Acessórios");




            }
        });

    }

    public void clickBrinquedo(View view) {

        brinquedos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewText("Brinquedos");


            }
        });

    }

    public void clickCalcados(View view) {

        calcados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewText("Calçados");


            }
        });

    }

    public void clickEnxoval(View view) {

        enxoval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewText("Enxoval");


            }
        });

    }

    public void clickEscolar(View view) {

        escolar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewText("Escolar");


            }
        });

    }

    public void clickMoveis(View view) {

        moveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewText("Móveis");


            }
        });

    }

    public void clickRoupas(View view) {

        roupas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextViewText("Roupas");


            }
        });

    }
    public void setTextViewText(String text){
        Bundle args = new Bundle();
        args.putString("key",text);

        ProdutoCategoriaFragment produtoCategoriaFragment= new ProdutoCategoriaFragment();
        produtoCategoriaFragment.setArguments(args);
        FragmentoUtils.replace(getActivity(),produtoCategoriaFragment);

    }
}
