package com.example.marlonncarvalhosa.academiapersonal.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marlonncarvalhosa.academiapersonal.R;
import com.example.marlonncarvalhosa.academiapersonal.adapter.AdapterDias;
import com.example.marlonncarvalhosa.academiapersonal.model.Dias;
import com.example.marlonncarvalhosa.academiapersonal.utils.ConfiguraçõesFirebase;
import com.example.marlonncarvalhosa.academiapersonal.views.AdicionarDiasDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiasFragment extends Fragment {

    private FloatingActionButton addDia;
    private static final int PICK_IMAGE_REQUEST = 71;
    private RecyclerView recyclerView;
    private List<Dias> dias;
    private Query databaseProdutos;
    private AdapterDias adapter;


    public DiasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dias, container, false);

        idCampo(view);
        preencherLista(view);
        AdicionarDia(view);

        return view;

    }

    private void preencherLista(View view) {
        dias = new ArrayList<>();
        databaseProdutos = ConfiguraçõesFirebase.getDias().orderByValue();
        databaseProdutos.keepSynced(true);
        databaseProdutos.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{

                    dias.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Dias dia = snapshot.getValue(Dias.class);
                        dias.add(dia);


                    }
                    adapter.atualiza(dias);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        adapter = new AdapterDias(getActivity(),dias);
        recyclerView.setAdapter(adapter);

    }

    public void idCampo(View view) {

        addDia = view.findViewById(R.id.bntAddDia);
        recyclerView = view.findViewById(R.id.recyclerDias);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    public void AdicionarDia(View view) {
        addDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirDialogo();

            }
        });
    }

    private void abrirDialogo() {

        AdicionarDiasDialog adicionarDiasDialog = new AdicionarDiasDialog();
        adicionarDiasDialog.show(getFragmentManager(), " Dias");

    }

}
