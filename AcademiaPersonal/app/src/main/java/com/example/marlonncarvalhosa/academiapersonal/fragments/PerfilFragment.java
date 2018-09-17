package com.example.marlonncarvalhosa.academiapersonal.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.marlonncarvalhosa.academiapersonal.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private ImageView imagemGoogle;
    private TextView nomeGoogle;
    private TextView emailGoogle;

    private GoogleApiClient googleApiClient;


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        imagemGoogle = (ImageView) view.findViewById(R.id.imagemPerfilGoogle) ;
        nomeGoogle = (TextView) view.findViewById(R.id.nomeGoogle);
        emailGoogle = (TextView) view.findViewById(R.id.emailGoogle);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(getActivity(), null)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleSigninResult(result);
        } else {
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSigninResult(googleSignInResult);
                }
            });
        }

    }

    private void handleSigninResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            GoogleSignInAccount conta = result.getSignInAccount();
            nomeGoogle.setText(conta.getDisplayName());
            emailGoogle.setText(conta.getEmail());


            Glide.with(getActivity()).load(conta.getPhotoUrl()).into(imagemGoogle);

        } else {
            goLogInScreen();

        }

    }

    private void goLogInScreen() {
        Intent intent = new Intent(getActivity(), PerfilFragment.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
