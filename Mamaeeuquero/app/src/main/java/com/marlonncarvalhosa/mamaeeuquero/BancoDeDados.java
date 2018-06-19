package com.marlonncarvalhosa.mamaeeuquero;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class BancoDeDados extends Activity {
    private ArrayList<String> nomeDasDisciplinas;
    public static BancoDeDados instance;

    public static BancoDeDados getInstance(){
        if(instance == null){
            instance = new BancoDeDados();
        }

        return instance;
    }

    public void salva(String nomeDaDisciplina) {
        if(nomeDasDisciplinas == null) {
            nomeDasDisciplinas = new ArrayList<>();
        }

        nomeDasDisciplinas.add(nomeDaDisciplina);
    }

    public ArrayList<String> getNomeDasDisciplinas(){
        return nomeDasDisciplinas;
    }

    public boolean temId(Activity activity) {
        return !getId(activity).isEmpty();
    }

    public String getId(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(activity.getString(R.string.shared_config), Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(activity.getString(R.string.id), "");
        return result;
    }

}
