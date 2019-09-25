package com.marlonn.devmov2.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.marlonn.devmov2.DAO.DataBaseDAO;
import com.marlonn.devmov2.R;
import com.marlonn.devmov2.model.Usuario;

import java.util.Objects;

public class LoginDialog extends AppCompatDialogFragment {

    static final int GOOGLE_SIGN = 123;
    FirebaseAuth firebaseAuth;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton login_btn;

    private Usuario usuario = new Usuario();
    private String idUsuario;
    private String nomeUsuario;
    private String fotoPerfilGoogle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.login_dialog, null);

        login_btn = view.findViewById(R.id.btn_login);

        builder.setView(view);
        builder.setIcon(R.drawable.ic_action_warning);
        builder.setTitle("Efetue o Login!");
        builder.setCancelable(true);
        builder.setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (firebaseAuth.getCurrentUser() == null) {

                    Toast.makeText(getActivity(), "Precisa efetuar o login!" , Toast.LENGTH_LONG).show();

                } else {

                    uploadUsuario();

                }

            }
        });

        logar();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);

        firebaseAuth = FirebaseAuth.getInstance();

        googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return builder.create();

    }

    private void logar () {

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (firebaseAuth.getCurrentUser() == null) {

                    signIn();

                } else {

                    Toast.makeText(getActivity(), "Clique em Finaliza!" , Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private  void uploadUsuario () {

        usuario.setIdUsuario(idUsuario);
        usuario.setNome(nomeUsuario);
        usuario.setFotoPerfilGoogle(fotoPerfilGoogle);

        new DataBaseDAO().saveUsuario(getActivity(), usuario);

    }

    public void verificaAuth() {

        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Toast.makeText(getContext(), "logado", Toast.LENGTH_LONG).show();
            }

        }

        if (firebaseAuth.getCurrentUser() == null) {
            Toast.makeText(getContext(), "nao logado", Toast.LENGTH_LONG).show();
            signIn();
        };

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {

                Log.w(TAG, "Google sign in failed", e);

            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Objects.requireNonNull(getActivity()).recreate();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }

                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void signOut() {
        firebaseAuth.signOut();

        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });

    }

    private void revokeAccess() {
        firebaseAuth.signOut();

        mGoogleSignInClient.revokeAccess().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });

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
            idUsuario = user.getUid();
            nomeUsuario = conta.getDisplayName();
            fotoPerfilGoogle = String.valueOf(conta.getPhotoUrl());

        } else {

        }

    }

    @Override
    public void onResume() {

        super.onResume();
        this.onCreate(null);
    }

}
