package com.marlonn.devmov2.DAO;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.marlonn.devmov2.MapsActivity;
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
        reference.child(ConstantsUtils.BANCO_USUARIO).child("contas").child(String.valueOf(usuario.getIdUsuario())).child("infos").setValue(usuario);
    }

    public void updateSimpleInfoUser(MapsActivity mapsActivity, Usuario usuario) {
        DatabaseReference reference = ConfiguraçõesFirebase.getFirebase();
        reference.child(ConstantsUtils.BANCO_USUARIO).child(String.valueOf(usuario.getId())).setValue(usuario);
    }

    public static Query getQuerryUsuario(String uId) {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_USUARIO);
    }

}