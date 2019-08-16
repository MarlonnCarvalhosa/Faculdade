package com.example.marlonncarvalhosa.academiapersonal.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.marlonncarvalhosa.academiapersonal.R;
import com.example.marlonncarvalhosa.academiapersonal.fragments.CadastrarFragment;
import com.example.marlonncarvalhosa.academiapersonal.model.Usuario;
import com.example.marlonncarvalhosa.academiapersonal.utils.FragmentoUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;
import java.util.List;

public class AdapterAlunos extends RecyclerView.Adapter<AdapterAlunos.ViewHolder> {
    private FragmentActivity activity;
    private List<Usuario> usuarios;
    private Usuario usuario = new Usuario();
    private FirebaseUser currentFirebaseUser;
    private FirebaseAuth auth;

    public AdapterAlunos(FragmentActivity activity, List<Usuario> usuarios){
        this.activity = activity;
        this.usuarios = usuarios;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNome;
        private ImageView fotoGoogle;
        private LinearLayout clickCard;

        public ViewHolder(View itemView) {
            super(itemView);

            auth = FirebaseAuth.getInstance();
            currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

            fotoGoogle = itemView.findViewById(R.id.fotoDoGoogle);
            clickCard = itemView.findViewById(R.id.linearAdapter);
            txtNome = itemView.findViewById(R.id.nomeDoGoogle);

        }
    }

    public void atualiza(List<Usuario> usuarios){
        Collections.reverse(usuarios);
        this.usuarios = usuarios;
        this.notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(R.layout.item_alunos,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final AdapterAlunos.ViewHolder holder, int position) {
        final Usuario usuario = usuarios.get(position);

        holder.txtNome.setText(usuario.getNome());
        holder.clickCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentoUtils.replace(activity, new CadastrarFragment());

            }
        });
        try {
            Glide.with(activity).load(usuario.getFotoPerfilGoogle()).into(holder.fotoGoogle);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        return usuarios.size();
    }
}
