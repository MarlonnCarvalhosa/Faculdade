package com.marlonn.devmov2.DAO;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.marlonn.devmov2.model.Evento;
import com.marlonn.devmov2.model.Usuario;
import com.marlonn.devmov2.utils.ConfiguraçõesFirebase;
import com.marlonn.devmov2.utils.ConstantsUtils;

public class DataBaseDAO {

    private boolean sucesso = true;
    private int cont = 0;


    public void saveUsuario(FragmentActivity activity, Usuario usuario) {
        usuario.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
        Log.v("teste save", usuario.getId());
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_USUARIO).child(String.valueOf(usuario.getIdUsuario())).child("dados").setValue(usuario);
    }

    public void saveEvento(FragmentActivity activity, Evento evento) {
        evento.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
        Log.v("teste save", evento.getId());
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_EVENTO).child(evento.getId()).setValue(evento);
    }

    public void updateSimpleEvento(FragmentActivity mapsActivity, Evento evento) {
        evento.setId(ConfiguraçõesFirebase.getFirebase().push().getKey());
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_EVENTO).child(String.valueOf(evento.getId())).child("infos").child(evento.getId()).setValue(evento);
    }

    public static Query getQuerryUsuario(String uId) {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_USUARIO);
    }

    public static Query getQuerryEvento(String uid) {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_EVENTO);
    }

}