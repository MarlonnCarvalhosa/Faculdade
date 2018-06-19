package com.marlonncarvalhosa.mamaeeuquero.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.marlonncarvalhosa.mamaeeuquero.DAO.DataBaseDAO;
import com.marlonncarvalhosa.mamaeeuquero.R;
import com.marlonncarvalhosa.mamaeeuquero.model.Produto;
import com.marlonncarvalhosa.mamaeeuquero.utils.FragmentoUtils;
import com.marlonncarvalhosa.mamaeeuquero.utils.MoneyTextWatcher;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeiloarFragment extends Fragment {

    private EditText edit_produto, edit_cidade, edit_preco, edit_descricao, lanceComprador;
    private Spinner categoria;
    private Produto produto = new Produto();
    private ImageView image1, image2, image3;
    private String iddovendedor,data;
    String horario;
    int hora, minuto, segundos, dia;
    private FirebaseAuth auth;
    private int imgI = 0;

    FirebaseStorage storage;
    StorageReference storageReference;

    private boolean pikaChamativa = false;

    public LeiloarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_leiloar, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

        auth = FirebaseAuth.getInstance();
        verificaAuth();
        setHasOptionsMenu(true);
        final Calendar c = Calendar.getInstance();
        hora = 0 + c.get(Calendar.HOUR_OF_DAY);
        minuto = 0 + c.get(Calendar.MINUTE);
        segundos = 0 + c.get(Calendar.SECOND);
        dia = 0 + c.get(Calendar.DAY_OF_MONTH);
        horario = (hora + ":" + minuto + ":" + segundos);
        idCampo(view);
        setaBackGround(image1, image2, image3);


        Locale mLocale = new Locale("pt", "BR");
        edit_preco.addTextChangedListener(new MoneyTextWatcher(edit_preco, mLocale));

        return view;

    }

    private void setaBackGround(ImageView image1, ImageView image2, ImageView image3) {
        Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.def)).into(image1);

        Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.def)).into(image2);

        Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.def)).into(image3);

    }

    private void idCampo(View view) {

        image1 = view.findViewById(R.id.image1);
        image2 = view.findViewById(R.id.image2);
        image3 = view.findViewById(R.id.image3);

        edit_produto = view.findViewById(R.id.edit_nomeProduto);
        edit_preco = view.findViewById(R.id.edit_preco);
        edit_cidade = view.findViewById(R.id.edit_nomecidade);
        categoria = view.findViewById(R.id.spinnerclasse);
        edit_descricao = view.findViewById(R.id.edit_DescricaoProduto);
        long date = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        data = formatter.format(date);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(1);

            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(2);
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem(3);

            }

        });

    }

    private void uploadImage() {

        if(pikaChamativa){
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage(getActivity().getString(R.string.aguarde));
            progressDialog.show();
            produto.setPreco(edit_preco.getText().toString().replace("R$", "").replace(",", "."));
            produto.setNome(edit_produto.getText().toString());
            produto.setCat(categoria.getSelectedItem().toString());
            produto.setLocal(edit_cidade.getText().toString());
            produto.setDescrição(edit_descricao.getText().toString());
            produto.setIddovendedor(iddovendedor);
            produto.setLancedocomprador("");
            produto.setNomedocomprador(" ");
            produto.setDataInicial(data);
            produto.setHorarioInicial(horario);
            produto.setHora(hora);
            produto.setMinuto(minuto);
            produto.setSegundos(segundos);
            produto.setDia(dia);

            new DataBaseDAO().uploadDados(getActivity(), image1, image2, image3, produto, progressDialog);

        }else {
            android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(getContext());

            alert
                    .setTitle("ATENÇÃO!")
                    .setIcon(R.drawable.ic_action_alert_red)
                    .setMessage("Para efetuar um leilão é necessário adicionar no mínimo uma foto do produto.")
                    .setCancelable(true)
                    .setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }

                    });

            android.app.AlertDialog alertDialog = alert.create();
            alertDialog.show();

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());


        } else if (requestCode == Crop.REQUEST_CROP) {
            setImage(resultCode, result);

        }

    }

    public void verificaAuth() {

        if (auth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                iddovendedor = user.getUid();
            }

        }

        if (auth.getCurrentUser() == null) {
            FragmentoUtils.replace(getActivity(), new LoginFragment());
        }

    }


    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_leiloar, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.enviar:

                cadastrologar();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void beginCrop(Uri source) {

        Uri destinarion = Uri.fromFile(new File(getActivity().getCacheDir(), "croped" + String.valueOf(System.currentTimeMillis())));
        Crop.of(source, destinarion).asSquare().start(getContext(), LeiloarFragment.this);

    }

    private void setImage(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            pikaChamativa = true;
            switch (imgI) {
                case 1:
                    Glide.with(getActivity()).load(Crop.getOutput(result)).into(image1);

                    break;

                case 2:
                    Glide.with(getActivity()).load(Crop.getOutput(result)).into(image2);

                    break;
                case 3:
                    Glide.with(getActivity()).load(Crop.getOutput(result)).into(image3);

                    break;
            }


        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    private void selecionarImagem( int i) {
        imgI = i;
        Crop.pickImage(getContext(), LeiloarFragment.this);

    }

    private void cadastrologar() {

        final String nome = edit_produto.getText().toString().trim();
        final String descricao = edit_descricao.getText().toString().trim();
        final String cidade = edit_cidade.getText().toString().trim();
        final String preco = edit_preco.getText().toString().trim();


        if (TextUtils.isEmpty(nome)) {
            edit_produto.setError("Campo Obrigatório");


            return;
        }

        if (TextUtils.isEmpty(descricao)) {
            edit_descricao.setError("Campo Obrigatório");


            return;
        }

        if (TextUtils.isEmpty(cidade)) {
            edit_cidade.setError("Campo Obrigatório");


            return;
        }

        if (TextUtils.isEmpty(preco)) {
            edit_preco.setError("Campo Obrigatório");


            return;
        }
        uploadImage();


    }

}