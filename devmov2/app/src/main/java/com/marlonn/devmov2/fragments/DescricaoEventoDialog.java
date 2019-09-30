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
    private Evento evento;
    public DescricaoEventoDialog() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.descricao_evento_dialog, container, false);
        nomeDoEvento = view.findViewById(R.id.txt_nome_evento);
        descricaoDoEvento = view.findViewById(R.id.txt_decricao_evento);
        nomeDoEvento.setText(evento.getNomeDoEvento());
        descricaoDoEvento.setText(evento.getDescricaoDoEvento());
        return view;
    }

    public static DescricaoEventoDialog newInstance( ) {
        DescricaoEventoDialog descricaoEventoDialog = new DescricaoEventoDialog();
        return descricaoEventoDialog;
    }

    public DescricaoEventoDialog setarEvento(Evento evento) {
        this.evento = evento;
        return this;

    }




}
