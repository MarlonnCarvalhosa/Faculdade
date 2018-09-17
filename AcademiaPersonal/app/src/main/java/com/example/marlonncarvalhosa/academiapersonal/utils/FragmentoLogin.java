package com.example.marlonncarvalhosa.academiapersonal.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.marlonncarvalhosa.academiapersonal.R;

public class FragmentoLogin {

    public static void replace(FragmentActivity activity, Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragPrincipal, fragment).commit();
    }
}
