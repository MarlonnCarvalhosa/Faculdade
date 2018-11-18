package com.example.marlonncarvalhosa.academiapersonal.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marlonncarvalhosa.academiapersonal.R;
import com.example.marlonncarvalhosa.academiapersonal.model.Exercicio;
import com.example.marlonncarvalhosa.academiapersonal.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class AdapterExercicios extends RecyclerView.Adapter<AdapterExercicios.ViewHolder> {

    private FragmentActivity activity;
    private List<Exercicio> exercicios;
    private Usuario usuario = new Usuario();
    private FirebaseUser currentFirebaseUser;
    private FirebaseAuth auth;

    public AdapterExercicios(FragmentActivity activity, List<Exercicio> exercicios){
        this.activity = activity;
        this.exercicios = exercicios;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNome;

        public ViewHolder(View itemView) {
            super(itemView);

            auth = FirebaseAuth.getInstance();
            currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

            txtNome = itemView.findViewById(R.id.nomeDoExercicio);

        }
    }

    public void atualiza(List<Exercicio> exercicios){
        Collections.reverse(exercicios);
        this.exercicios = exercicios;
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AdapterExercicios.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(R.layout.item_exercicios,parent,false);
        return new AdapterExercicios.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterExercicios.ViewHolder holder, int position) {
        final Exercicio exercicio = exercicios.get(position);

        holder.txtNome.setText(exercicio.getNome());

        try {
            //Glide.with(activity).load(usuario.getFotoPerfilGoogle()).into(holder.fotoGoogle);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return exercicios.size();
    }
}