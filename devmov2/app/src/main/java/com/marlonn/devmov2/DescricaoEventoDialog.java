package com.marlonn.devmov2;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.marlonn.devmov2.model.Evento;
import com.marlonn.devmov2.utils.ConfiguraçõesFirebase;
import com.marlonn.devmov2.utils.ConstantsUtils;

import java.util.ArrayList;
import java.util.List;

public class DescricaoEventoDialog extends AppCompatDialogFragment {

    private Bundle bundle;
    private ImageView imageView;
    private TextView nomeDoEvento, descricaoDoEvento;
    private FirebaseAuth auth;
    private Query queryEventos;
    private List<Evento> eventos = new ArrayList<>();
    private Evento evento = new Evento();

    private FirebaseUser firebaseUser;



    public DescricaoEventoDialog() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.descricao_evento_dialog, container, false);

        auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        idCampo(view);
        //initView(view);
        //preencherLista();

        return view;
    }

    private void idCampo(View view) {
        nomeDoEvento = view.findViewById(R.id.txt_nome_evento);
        descricaoDoEvento = view.findViewById(R.id.txt_decricao_evento);

    }

    private void initView(final View view) {
        bundle = getArguments();
        if (bundle != null) {
            evento = (Evento) bundle.getSerializable(ConstantsUtils.BANCO_EVENTO);

            nomeDoEvento.setText(evento.getNomeDoEvento());
            descricaoDoEvento.setText(evento.getDescricaoDoEvento());

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if (getView() == null) {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    //FragmentoUtils.replace(getActivity(), new InicioFragment());
                    return true;
                }

                return false;

            }

        });

    }

    private void preencherLista() {
        eventos = new ArrayList<>();
        queryEventos = ConfiguraçõesFirebase.getEventos().orderByValue();
        queryEventos.keepSynced(true);
        queryEventos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                try{

                    eventos.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Evento evento = snapshot.getValue(Evento.class);

                        nomeDoEvento.setText(evento.getNomeDoEvento());
                        descricaoDoEvento.setText(evento.getDescricaoDoEvento());



                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
