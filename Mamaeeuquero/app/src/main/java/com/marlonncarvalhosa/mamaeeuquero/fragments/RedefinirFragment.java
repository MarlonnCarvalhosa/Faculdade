package com.marlonncarvalhosa.mamaeeuquero.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;

public class RedefinirFragment extends Fragment {
    AutoCompleteTextView email;
    FirebaseAuth auth;
    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    Button btn_reset;

    public RedefinirFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_redefinir, container, false);

        auth = FirebaseAuth.getInstance();
        email =  view.findViewById(R.id.redefinir_email);
        btn_reset = (Button) view.findViewById(R.id.btn_redefinir);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = email.getText().toString().trim();

                if (TextUtils.isEmpty(strEmail)) {
                    email.setError("Obrigatorio");
                    return;
                }
                redefinir(strEmail);
            }
        });

        return  view;
    }

    private void redefinir(String strEmail) {


        auth.sendPasswordResetEmail(strEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            email.setText("");
                            FragmentoUtils.replace(getActivity(), new LoginFragment() );
                            Toast.makeText(getContext(),"o email de redifinição foi enviado",Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getContext(),"Email não Cadastrado",Toast.LENGTH_LONG).show();
                            email.setText("");
                            return;
                        }

                    }
                });

    }

}

