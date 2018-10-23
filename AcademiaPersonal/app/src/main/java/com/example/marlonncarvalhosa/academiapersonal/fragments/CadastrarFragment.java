package com.example.marlonncarvalhosa.academiapersonal.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.marlonncarvalhosa.academiapersonal.DAO.DataBaseDAO;
import com.example.marlonncarvalhosa.academiapersonal.R;
import com.example.marlonncarvalhosa.academiapersonal.model.Avaliacao;
import com.example.marlonncarvalhosa.academiapersonal.utils.FragmentoUtils;
import com.example.marlonncarvalhosa.academiapersonal.views.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadastrarFragment extends Fragment {

    private EditText id, editBicepesDireito,editBicepesEsquerdo, editAntBracoDireito, editAntBracoEsquerdo, editTorax, editAbdomen, editCintura, editQuadril, editCoxaDireita, editCoxaEsquerda,
    editPanturrilhaDireita, editPanturrilhaEsquerda, editPeso, editAltura;
    private String idAluno, idPersonal;
    private Button irExercicios;
    private FirebaseAuth firebaseAuth;
    private Avaliacao avaliacao = new Avaliacao();


    public CadastrarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastrar, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        verificaAuth();

        campoIds(view);
        adicionarExercicios(view);

        return view;
    }

    public void adicionarExercicios(final View view) {

        irExercicios = view.findViewById(R.id.irExercicios);
        irExercicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadAvaliacao(view);

            }

        });

    }

    private void campoIds(View view) {

        editBicepesDireito = view.findViewById(R.id.id_bracoDireito);
        editBicepesEsquerdo = view.findViewById(R.id.id_bracoEsquerdo);
        editAntBracoDireito = view.findViewById(R.id.id_anteBracoDireito);
        editAntBracoEsquerdo = view.findViewById(R.id.id_anteBracoEsquerdo);
        editTorax = view.findViewById(R.id.id_torax);
        editAbdomen = view.findViewById(R.id.id_abdomen);
        editCintura = view.findViewById(R.id.id_cintura);
        editQuadril = view.findViewById(R.id.id_quadril);
        editCoxaDireita = view.findViewById(R.id.id_coxaDireita);
        editCoxaEsquerda = view.findViewById(R.id.id_coxaEsquerda);
        editPanturrilhaDireita = view.findViewById(R.id.id_panturrilhaDireita);
        editPanturrilhaEsquerda = view.findViewById(R.id.panturrilhaEsquerda);
        editPeso = view.findViewById(R.id.id_peso);
        editAltura = view.findViewById(R.id.id_altura);

    }

    private void uploadAvaliacao(View view) {

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getActivity().getString(R.string.aguarde));
        progressDialog.show();

        Runnable progressRunnable = new Runnable() {
            @Override
            public void run() {
                progressDialog.cancel();
                FragmentoUtils.replace(getActivity(), new ExerciciosFragment());
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 2500);

        avaliacao.setId(idPersonal);
        avaliacao.setIdAluno(idAluno);
        avaliacao.setIdPersonal(idPersonal);
        avaliacao.setBracoDireito(editBicepesDireito.getText().toString());
        avaliacao.setBracoEsquerdo(editBicepesEsquerdo.getText().toString());
        avaliacao.setAnteBracoDireito(editAntBracoDireito.getText().toString());
        avaliacao.setAnteBracoEsquerdo(editAntBracoEsquerdo.getText().toString());
        avaliacao.setTorax(editTorax.getText().toString());
        avaliacao.setAbdomen(editAbdomen.getText().toString());
        avaliacao.setCintura(editCintura.getText().toString());
        avaliacao.setQuadril(editQuadril.getText().toString());
        avaliacao.setCoxaDireita(editCoxaDireita.getText().toString());
        avaliacao.setCoxaEsquerda(editCoxaEsquerda.getText().toString());
        avaliacao.setPanturrilhaDireita(editPanturrilhaDireita.getText().toString());
        avaliacao.setPanturrilhaEsquerda(editPanturrilhaEsquerda.getText().toString());
        avaliacao.setPeso(editPeso.getText().toString());
        avaliacao.setAltura(editAltura.getText().toString());

        new DataBaseDAO().uploadDados(getActivity(), avaliacao, progressDialog);

    }

    public void verificaAuth() {

        if (firebaseAuth.getCurrentUser() != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                idPersonal = user.getUid();
            }

        }

        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);

        };

    }

}
