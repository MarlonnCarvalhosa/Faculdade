package com.marlonncarvalhosa.mamaeeuquero.Views;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.adapter.ImagensAdapter;
import com.marlonncarvalhosa.mamaeeuquero.model.Imagem;
import com.marlonncarvalhosa.mamaeeuquero.model.Produto;

import java.util.ArrayList;

public class ImagensActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ImagensAdapter adapter;
    private Button botao_sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagens);

        initView();
        Produto produto = (Produto) getIntent().getSerializableExtra("PRODUTO");
        ArrayList<Imagem> imagems = new ArrayList<>();
        imagems.add(produto.getImagem1());
        imagems.add(produto.getImagem2());
        imagems.add(produto.getImagem3());

        botao_sair = (Button) findViewById(R.id.botao_sai);
        sairImg();
        hideNavigationbar();

        adapter = new ImagensAdapter(this, imagems);
        viewPager.setAdapter(adapter);

    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);

    }

    public static Intent newIntent(FragmentActivity context, Produto produto) {
        Intent intent = new Intent(context, ImagensActivity.class);
        intent.putExtra("PRODUTO", produto);
        return intent;
    }

    private void sairImg() {
        botao_sair.setOnClickListener(new View.OnClickListener() {
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
