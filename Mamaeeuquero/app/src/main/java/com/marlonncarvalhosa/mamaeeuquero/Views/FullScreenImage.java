package com.marlonncarvalhosa.mamaeeuquero.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.fragments.DescricaoFragment;
import com.marlonncarvalhosa.mamaeeuquero.model.Produto;
import com.marlonncarvalhosa.mamaeeuquero.utils.ConstantsUtils;

public class FullScreenImage extends AppCompatActivity {

    private ImageView btn_sair;
    private ImageView imageView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        Intent intent = getIntent();
        url = intent.getStringExtra(DescricaoFragment.URL_IMAGEM);
        btn_sair = (ImageView) findViewById(R.id.btn_sair);
        imageView = (ImageView) findViewById(R.id.img_full);
        sairImg();
        hideNavigationbar();
        try {
            Glide.with(getApplicationContext())
                    .load(url)
                    .into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sairImg() {
        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        hideNavigationbar();

    }

    private void hideNavigationbar() {

        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

    }

}
