package com.marlonn.devmov2;

import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marlonn.devmov2.model.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity  {

    FirebaseAuth firebaseAuth;
    private Usuario usuario = new Usuario();
    private boolean isOpen = false ;
    private ConstraintSet layout1,layout2;
    private ConstraintLayout constraintLayout ;
    private CircleImageView imageViewPhoto;
    private ImageView cover;
    private TextView nomeUsuario;

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // changing the status bar color to transparent -----

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        layout1 = new ConstraintSet();
        layout2 = new ConstraintSet();
        nomeUsuario = findViewById(R.id.txt_nome);
        imageViewPhoto = findViewById(R.id.foto_perfil);
        constraintLayout = findViewById(R.id.constraint_layout);
        cover = findViewById(R.id.cover);
        layout2.clone(this,R.layout.profile_expanded);
        layout1.clone(constraintLayout);

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {


                if (!isOpen) {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    isOpen = !isOpen ;
                }

                else {

                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    isOpen = !isOpen ;

                }

            }

        });

        cover.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                if (!isOpen) {
                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout2.applyTo(constraintLayout);
                    isOpen = !isOpen ;
                }

                else {

                    TransitionManager.beginDelayedTransition(constraintLayout);
                    layout1.applyTo(constraintLayout);
                    isOpen = !isOpen ;

                }

            }
        });

    }

}
