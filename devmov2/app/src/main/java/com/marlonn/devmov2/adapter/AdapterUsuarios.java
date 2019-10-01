package com.marlonn.devmov2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marlonn.devmov2.R;
import com.marlonn.devmov2.model.Usuario;

import java.util.Collections;
import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<com.marlonn.devmov2.adapter.AdapterUsuarios.ViewHolder> {
    private FragmentActivity activity;
    private List<Usuario> usuarios;
    private Usuario usuario = new Usuario();
    private FirebaseUser currentFirebaseUser;
    private FirebaseAuth auth;

    public AdapterUsuarios(FragmentActivity activity, List<Usuario> usuarios){
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

            fotoGoogle = itemView.findViewById(R.id.foto_perfil);
            txtNome = itemView.findViewById(R.id.txt_nomeUsuario);

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
        view = mInflater.inflate(R.layout.activity_profile,parent,false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final com.marlonn.devmov2.adapter.AdapterUsuarios.ViewHolder holder, int position) {
        final Usuario usuario = usuarios.get(position);

        holder.txtNome.setText(usuario.getNome());
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
