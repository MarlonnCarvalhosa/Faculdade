package com.marlonn.devmov2.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.marlonn.devmov2.R;
import com.marlonn.devmov2.model.Evento;
import com.squareup.picasso.Picasso;

public class DescricaoEventoDialog extends DialogFragment {

    private TextView nomeDoEvento, dataInicioEvento, horaInicioEvento, descricaoDoEvento, nomeCriadorEvento;
    private ImageView imagemEvento;
    private String url;
    private Evento evento;
    private DatabaseReference mDatabase;

    public DescricaoEventoDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.descricao_evento_dialog, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        nomeDoEvento = view.findViewById(R.id.txt_nomeEvento);
        dataInicioEvento = view.findViewById(R.id.txt_dataInicioEvento);
        nomeCriadorEvento = view.findViewById(R.id.txt_nomeCriadorEvento);
        descricaoDoEvento = view.findViewById(R.id.txt_decricaoEvento);
        imagemEvento = view.findViewById(R.id.img_eventoImagem);

        nomeDoEvento.setText(evento.getNomeDoEvento());
        nomeCriadorEvento.setText(evento.getNomeCriadorEvento());
        dataInicioEvento.setText(evento.getInicioDoEvento());
        descricaoDoEvento.setText(evento.getDescricaoDoEvento());

        getMarkers();

        return view;
    }

    private void getMarkers(){
        mDatabase.child("eventos").child(evento.getId()).child("imagem").child("imageurl").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null) {
                            url = String.valueOf(dataSnapshot.getValue());
                            Log.v("Marlonn Carvalhosa", url+ "");

                            Picasso.get().load(url).into(imagemEvento);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });

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
