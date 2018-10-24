package com.example.marlonncarvalhosa.academiapersonal.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marlonncarvalhosa.academiapersonal.R;
import com.example.marlonncarvalhosa.academiapersonal.adapter.AdapterAlunos;
import com.example.marlonncarvalhosa.academiapersonal.model.Usuario;
import com.example.marlonncarvalhosa.academiapersonal.utils.ConfiguraçõesFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlunosFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 71;
    private RecyclerView recyclerView;
    private List<Usuario> usuarios;
    private Query databaseProdutos;
    private AdapterAlunos adapter;

    public AlunosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alunos, container, false);

        idCampo(view);
        preencherLista(view);

        return view;
    }

    private void preencherLista(View view) {
        usuarios = new ArrayList<>();
        databaseProdutos = ConfiguraçõesFirebase.getUsuario().orderByValue();
        databaseProdutos.keepSynced(true);
        databaseProdutos.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try{

                    usuarios.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Usuario usuario = snapshot.getValue(Usuario.class);
                        usuarios.add(usuario);


                    }
                    adapter.atualiza(usuarios);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        adapter = new AdapterAlunos(getActivity(),usuarios);
        recyclerView.setAdapter(adapter);

    }

    public void idCampo(View view) {

        recyclerView = view.findViewById(R.id.recyclerAlunos);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

}
