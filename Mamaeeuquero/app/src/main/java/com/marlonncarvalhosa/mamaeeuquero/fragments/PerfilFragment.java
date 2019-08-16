package com.marlonncarvalhosa.mamaeeuquero.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.marlonncarvalhosa.mamaeeuquero.DAO.DataBaseDAO;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.model.Usuario;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    private FirebaseAuth auth;
    private TextView pessoa, celular, email, leiloes;
    private Query queryPerfil;
    private String idusuario;
    private FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser() ;

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            idusuario=user.getUid();

        }
        getUsuario(usuario.getUid());

        setHasOptionsMenu(true);
        pessoa = view.findViewById(R.id.pessoaPerfil);
        celular = view.findViewById(R.id.celPerfil);
        email = view.findViewById(R.id.emailPerfil);

        metodobotoes(view);

        return view;

    }

    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_desconectar, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.desconectarPerfil:
                desconectarPerfil();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void desconectarPerfil() {
                auth.signOut();
                FragmentoUtils.replace(getActivity(), new LoginFragment());

    }

    public void  metodobotoes (View view){
        leiloes = view.findViewById(R.id.meusleiloes);
        leiloes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("ID",idusuario);

                ProdutoCategoriaFragment produtoCategoriaFragment= new ProdutoCategoriaFragment();
                produtoCategoriaFragment.setArguments(args);
                FragmentoUtils.replace(getActivity(),produtoCategoriaFragment);

            }

        });

    }

    private void getUsuario(String uId){
        queryPerfil = DataBaseDAO.getQuerryUsuario(uId);
        queryPerfil.keepSynced(true);
        queryPerfil.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    exibir(usuario);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }

    private void exibir(Usuario usuario) {

        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            if (user.getDisplayName() != null) {
                pessoa.setText(usuario.getNomeUsuario());
            }
            if (user.getPhoneNumber() != null){
                celular.setText(usuario.getNumeroCelular());
            }
            if (user.getEmail() != null ) {
                email.setText(usuario.getEmail());
            }

        }

    }

}
