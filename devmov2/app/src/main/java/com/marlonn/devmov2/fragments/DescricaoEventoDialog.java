package com.marlonn.devmov2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marlonn.devmov2.R;
import com.marlonn.devmov2.model.Evento;

public class DescricaoEventoDialog extends DialogFragment {

    private TextView nomeDoEvento, descricaoDoEvento;
    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;

    public DescricaoEventoDialog() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.descricao_evento_dialog, container, false);

        auth = FirebaseAuth.getInstance();
        nomeDoEvento = view.findViewById(R.id.txt_nome_evento);
        descricaoDoEvento = view.findViewById(R.id.txt_decricao_evento);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        Evento evento = (Evento) getArguments().getSerializable("evento");
        nomeDoEvento.setText(evento.getNomeDoEvento());
        descricaoDoEvento.setText(evento.getDescricaoDoEvento());

        return view;
    }

    public static DescricaoEventoDialog newInstance(Evento evento) {
        DescricaoEventoDialog descricaoEventoDialog = new DescricaoEventoDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("evento", evento);
        descricaoEventoDialog.setArguments(bundle);
        return descricaoEventoDialog;
    }

}
