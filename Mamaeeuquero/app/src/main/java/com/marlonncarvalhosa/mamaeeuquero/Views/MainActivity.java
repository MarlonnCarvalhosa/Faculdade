package com.marlonncarvalhosa.mamaeeuquero.Views;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.fragments.ConversasFragment;
import com.marlonncarvalhosa.mamaeeuquero.fragments.CategoriasFragment;
import com.marlonncarvalhosa.mamaeeuquero.fragments.InicioFragment;
import com.marlonncarvalhosa.mamaeeuquero.fragments.LeiloarFragment;
import com.marlonncarvalhosa.mamaeeuquero.fragments.LoginFragment;
import com.marlonncarvalhosa.mamaeeuquero.utils.BottomNavigationViewHelper;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    private Button button;
    FirebaseAuth auth;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_inicio:
                    FragmentoUtils.replace(MainActivity.this, new InicioFragment());
                    toolbar = (Toolbar) findViewById(R.id.toolbarTopo);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("Postagens Recentes");

                    return true;
                case R.id.navigation_favoritos:
                    FragmentoUtils.replace(MainActivity.this, new CategoriasFragment());
                    toolbar = (Toolbar) findViewById(R.id.toolbarTopo);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("Categorias");

                    return true;
                case R.id.navigation_leiloar:
                    FragmentoUtils.replace(MainActivity.this, new LeiloarFragment());
                    toolbar = (Toolbar) findViewById(R.id.toolbarTopo);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("Leiloar");

                    return true;
                case R.id.navigation_conversas:
                    if (auth.getCurrentUser() != null) {
                        FragmentoUtils.replace(MainActivity.this, new ConversasFragment());
                        toolbar = (Toolbar) findViewById(R.id.toolbarTopo);
                        setSupportActionBar(toolbar);
                        getSupportActionBar().setTitle("Conversas");

                    }else {
                        FragmentoUtils.replace(MainActivity.this, new LoginFragment());
                    }


                    return true;
                case R.id.navigation_perfil:
                    FragmentoUtils.replace(MainActivity.this, new LoginFragment());
                    toolbar = (Toolbar) findViewById(R.id.toolbarTopo);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setTitle("Meu Perfil");

                    return true;
            }
            return false;
        }
    };


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();


        toolbar = (Toolbar) findViewById(R.id.toolbarTopo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Postagens Recentes");

        final BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentoUtils.replace(MainActivity.this, new InicioFragment());

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_logo, menu);
        return true;
    }

}