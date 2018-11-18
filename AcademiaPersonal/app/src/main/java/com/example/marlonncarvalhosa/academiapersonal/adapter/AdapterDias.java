package com.example.marlonncarvalhosa.academiapersonal.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marlonncarvalhosa.academiapersonal.R;
import com.example.marlonncarvalhosa.academiapersonal.model.Dias;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class AdapterDias extends RecyclerView.Adapter<AdapterDias.ViewHolder> {

    private FragmentActivity activity;
    private List<Dias> dias;
    private Dias dia = new Dias();
    private FirebaseUser currentFirebaseUser;
    private FirebaseAuth auth;

    public AdapterDias(FragmentActivity activity, List<Dias> dias){
        this.activity = activity;
        this.dias = dias;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNomeDia;

        public ViewHolder(View itemView) {
            super(itemView);

            auth = FirebaseAuth.getInstance();
            currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

            txtNomeDia = itemView.findViewById(R.id.nomeDoDia);

        }
    }

    public void atualiza(List<Dias> dias){
        Collections.reverse(dias);
        this.dias = dias;
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AdapterDias.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(R.layout.item_dias,parent,false);
        return new AdapterDias.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterDias.ViewHolder holder, int position) {
        final Dias dia = dias.get(position);

        holder.txtNomeDia.setText(dia.getNome());

        try {
            //Glide.with(activity).load(usuario.getFotoPerfilGoogle()).into(holder.fotoGoogle);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return dias.size();
    }
}