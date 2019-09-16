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
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity  {

    static final int GOOGLE_SIGN = 123;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private GoogleApiClient googleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private RecyclerView recyclerView;

    FirebaseAuth firebaseAuth;
    private boolean isOpen = false ;
    private ConstraintSet layout1,layout2;
    private ConstraintLayout constraintLayout ;
    private CircleImageView imageViewPhoto;
    private ImageView cover;
    private TextView nomeUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

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

        if (firebaseAuth.getCurrentUser() != null) {
            nomeUsuario.setText(firebaseAuth.getCurrentUser().getDisplayName());
            Picasso.get().load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(imageViewPhoto);
            Picasso.get().load(firebaseAuth.getCurrentUser().getPhotoUrl()).into(cover);
            //Glide.with(ProfileActivity.this).load(firebaseAuth.getCurrentUser().getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(imageViewPhoto);
        }

    }

}
