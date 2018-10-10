package com.example.marlonncarvalhosa.academiapersonal.DAO;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.marlonncarvalhosa.academiapersonal.model.Avaliacao;
import com.example.marlonncarvalhosa.academiapersonal.model.Usuario;
import com.example.marlonncarvalhosa.academiapersonal.utils.ConfiguraçõesFirebase;
import com.example.marlonncarvalhosa.academiapersonal.utils.ConstantsUtils;
import com.example.marlonncarvalhosa.academiapersonal.views.LoginActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DataBaseDAO {

    private boolean sucesso = true;
    private int cont = 0;


    public void saveUsuario(LoginActivity loginActivity, Usuario usuario) {
        usuario.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_USUARIO).child(String.valueOf(usuario.getId())).setValue(usuario);
    }

    public void uploadDados(FragmentActivity activity, Avaliacao avaliacao, ProgressDialog progressDialog) {

        uploadAvaliacao(avaliacao);

    }

    public void updateSimpleInfoUser(Usuario usuario) {
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_USUARIO).child(String.valueOf(usuario.getId())).setValue(usuario);
    }

    private void uploadAvaliacao(Avaliacao avaliacao) {
        avaliacao.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
        Log.v("teste save", avaliacao.getId());
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_AVALIACAO).child(String.valueOf(avaliacao.getId())).setValue(avaliacao);
    }

    private void uploadUsuario(Usuario usuario) {
        usuario.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
        Log.v("teste save", usuario.getId());
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_USUARIO).child(String.valueOf(usuario.getId())).setValue(usuario);
    }

    public static Query getQuerryUsuario(String uId) {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_USUARIO).child(uId);
    }
}