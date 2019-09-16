package com.marlonn.devmov2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marlonn.devmov2.R;
import com.marlonn.devmov2.model.Evento;

import java.util.Collections;
import java.util.List;

public class AdapterEventos extends RecyclerView.Adapter<com.marlonn.devmov2.adapter.AdapterEventos.ViewHolder> {

    private FragmentActivity activity;
    private List<Evento> eventos;
    private Evento evento = new Evento();
    private FirebaseUser currentFirebaseUser;
    private FirebaseAuth auth;

    public AdapterEventos(FragmentActivity activity, List<Evento> eventos){
        this.activity = activity;
        this.eventos = eventos;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNomeEvento;
        private TextView txtDescricaoEvento;
        private LinearLayout clickCard;

        public ViewHolder(View itemView) {
            super(itemView);

            auth = FirebaseAuth.getInstance();
            currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

            txtNomeEvento = itemView.findViewById(R.id.txt_nome_evento);
            txtDescricaoEvento = itemView.findViewById(R.id.txt_decricao_evento);

        }
    }

    public void atualiza(List<Evento> eventos){
        Collections.reverse(eventos);
        this.eventos = eventos;
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AdapterEventos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(R.layout.descricao_evento_dialog,parent,false);
        return new AdapterEventos.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final com.marlonn.devmov2.adapter.AdapterEventos.ViewHolder holder, int position) {
        final Evento evento = eventos.get(position);

        holder.txtNomeEvento.setText(evento.getNomeDoEvento());
        holder.txtDescricaoEvento.setText(evento.getDescricaoDoEvento());

    }

    @Override
    public int getItemCount() {

        return eventos.size();
    }
}
