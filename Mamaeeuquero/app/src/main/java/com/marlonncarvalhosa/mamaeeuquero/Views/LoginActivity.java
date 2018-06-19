package com.marlonncarvalhosa.mamaeeuquero.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marlonncarvalhosa.mamaeeuquero.R;

public class LoginActivity extends AppCompatActivity {

    private EditText senha,email;
    private LinearLayout linearLayout;
    private TextView recuperar;
    private Button login;
    private FirebaseAuth auth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        auth = FirebaseAuth.getInstance();
        buscadeid();
        metodobotoes();
        progressBar.setVisibility(View.GONE);

    }

    public void buscadeid(){

        email = findViewById(R.id.editText_email);
        senha = findViewById(R.id.editText_senha);
        recuperar = findViewById(R.id.redefinir);
        progressBar = findViewById(R.id.progressbar);
        login = findViewById(R.id.logar);
    }


        public void metodobotoes() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               login.setVisibility(View.GONE);
               progressBar.setVisibility(View.VISIBLE);

                final String inputemail = email.getText().toString();
                final String password = senha.getText().toString();

                if (TextUtils.isEmpty(inputemail)) {
                    email.setError("Campo Obrigatorio");
                   login.setVisibility(View.VISIBLE);
                   progressBar.setVisibility(View.GONE);



                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    senha.setError("Campo Obrigatorio");
                    login.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    return;
                }


                auth.signInWithEmailAndPassword(inputemail,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful()){
                                    login.setVisibility(View.VISIBLE);
                                    progressBar.setVisibility(View.GONE);
                                    email.setText("");
                                    senha.setText("");

                                }
                                else {
                                    verificaAuth();
                                }

                            }

                        });

            }
        });

    }

    public void verificaAuth(){
        if(auth.getCurrentUser() !=null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {

                Intent transicaoadc = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(transicaoadc);

                finish();

            }

    }

}}


