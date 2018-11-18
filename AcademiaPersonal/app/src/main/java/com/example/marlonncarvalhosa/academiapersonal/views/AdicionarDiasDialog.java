package com.example.marlonncarvalhosa.academiapersonal.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.marlonncarvalhosa.academiapersonal.DAO.DataBaseDAO;
import com.example.marlonncarvalhosa.academiapersonal.R;
import com.example.marlonncarvalhosa.academiapersonal.model.Dias;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdicionarDiasDialog extends AppCompatDialogFragment {

    private EditText nomeDia;
    private FirebaseAuth firebaseAuth;
    private Dias dias = new Dias();
    FirebaseAuth auth;
    private FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dias_dialog_layout, null);

        campoIds(view);

        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        builder.setView(view)
                .setTitle("Adicione um dia de exercicio")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        uploadAvaliacao(view);

                    }
                });

        return builder.create();
    }

    private void campoIds(View view) {

        nomeDia = view.findViewById(R.id.edit_nome_dia);

    }

    private void uploadAvaliacao(View view) {


        dias.setNome(nomeDia.getText().toString());

        new DataBaseDAO().newDia(getActivity(), dias);

    }

}
