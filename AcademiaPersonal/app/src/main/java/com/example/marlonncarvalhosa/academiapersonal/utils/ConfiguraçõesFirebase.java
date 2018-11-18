package com.example.marlonncarvalhosa.academiapersonal.utils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ConfiguraçõesFirebase {
    private static DatabaseReference referenceFirebase;

    public static DatabaseReference getFirebase(){
        if(referenceFirebase == null){
            referenceFirebase= FirebaseDatabase.getInstance().getReference();

        }
        return referenceFirebase;
    }

    public static Query getProdutos() {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_AVALIACAO);
    }

    public static Query getUsuario() {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_USUARIO);
    }

    public static Query getDias() {
        return FirebaseDatabase.getInstance().getReference(ConstantsUtils.BANCO_DIAS);
    }
}
