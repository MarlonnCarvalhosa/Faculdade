package com.marlonncarvalhosa.mamaeeuquero.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.fragments.DescricaoFragment;

public class FragmentoUtils {

    public static void replace(FragmentActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentPrincipal, fragment).commit();
    }


}
