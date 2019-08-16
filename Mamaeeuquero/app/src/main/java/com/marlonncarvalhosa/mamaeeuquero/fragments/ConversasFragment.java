package com.marlonncarvalhosa.mamaeeuquero.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.marlonncarvalhosa.mamaeeuquero.DAO.DataBaseDAO;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.adapter.AdapterConversas;
import com.marlonncarvalhosa.mamaeeuquero.model.Conversa;
import com.marlonncarvalhosa.mamaeeuquero.model.Usuario;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConversasFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterConversas adapter;
    private List<Conversa> conversas;
    private Query databaseConversas;
    private FirebaseAuth auth;
    private String idusuario;
    private FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser() ;


    public ConversasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            idusuario=user.getUid();

        }

        initView(view);

        return view;

    }

    private void initView(View view) {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        conversas  = new ArrayList<>();
        recyclerView= view.findViewById(R.id.recyclerConversas);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration mDivider = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        recyclerView.addItemDecoration(mDivider);
        Log.v("IDIDIDID", currentFirebaseUser.getUid()+"");
        databaseConversas = DataBaseDAO.getConversas(currentFirebaseUser.getUid());
        databaseConversas.keepSynced(true);

        databaseConversas.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    conversas.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Conversa conversa = snapshot.getValue(Conversa.class);
                        conversas.add(conversa);
                        Log.v("CONVVV", conversas.size()+"");
                    }

                    adapter.atualiza(conversas);

                } catch (Exception e) {
                    e.printStackTrace();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        adapter = new AdapterConversas(getActivity(),conversas) ;
        recyclerView.setAdapter(adapter);


    }

}
