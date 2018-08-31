package com.example.marlonncarvalhosa.academiapersonal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.marlonncarvalhosa.academiapersonal.fragments.AlunosFragment;
import com.example.marlonncarvalhosa.academiapersonal.fragments.CadastrarFragment;
import com.example.marlonncarvalhosa.academiapersonal.fragments.RelatorioFragment;
import com.example.marlonncarvalhosa.academiapersonal.utils.FragmentoUtils;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_alunos:
                    FragmentoUtils.replace(MainActivity.this, new AlunosFragment());
                    return true;
                case R.id.navigation_cadastrar:
                    FragmentoUtils.replace(MainActivity.this, new CadastrarFragment());
                    return true;

                case R.id.navigation_relatorio:
                    FragmentoUtils.replace(MainActivity.this, new RelatorioFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentoUtils.replace(MainActivity.this, new AlunosFragment());

    }

}
