package com.marlonn.devmov2.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marlonn.devmov2.DAO.DataBaseDAO;
import com.marlonn.devmov2.R;
import com.marlonn.devmov2.model.Evento;
import com.marlonn.devmov2.utils.LocationData;

public class AdicionarEventoDialog extends AppCompatDialogFragment {

    private String uidEvento, nomeCriadorEvento;
    private Evento evento = new Evento();
    private EditText nomeDoEvento, dataInicioEvento, dataFimEvento, horaInicioEvento, horaFimEvento, descricaoEvento;
    private boolean eventoOn = true;
    private double latitude;
    private double longitude;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.adicionar_evento, null);

        firebaseAuth = FirebaseAuth.getInstance();
        nomeDoEvento = view.findViewById(R.id.edt_nomeEvento);
        nomeCriadorEvento = firebaseAuth.getCurrentUser().getDisplayName();
        dataInicioEvento = view.findViewById(R.id.edt_dataInicioEvento);
        dataFimEvento = view.findViewById(R.id.edt_dataFimEvento);
        horaInicioEvento = view.findViewById(R.id.edt_horaInicio);
        horaFimEvento = view.findViewById(R.id.edt_horaFim);
        descricaoEvento = view.findViewById(R.id.edt_descricao);
        
        if(getArguments() != null){
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("long");
        }

        builder.setView(view);
        builder.setTitle("Crie um evento!");
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Finalzar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                uploadEvento();

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage(AdicionarEventoDialog.this.getString(R.string.aguarde));
                progressDialog.show();
                Runnable progressRunnable = new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.cancel();
                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 2500);
            }
        });

        return builder.create();
    }

    public static AdicionarEventoDialog newInstance(LocationData locationData) {
        AdicionarEventoDialog dialog = new AdicionarEventoDialog();
        Bundle bundle = new Bundle();
        bundle.putDouble("lat", locationData.latitude);
        bundle.putDouble("long", locationData.longitude);
        dialog.setArguments(bundle);
        return dialog;
    }

    private void uploadEvento () {

        uidEvento = user.getUid();
        evento.setIdUsuario(uidEvento);
        evento.setNomeDoEvento(nomeDoEvento.getText().toString());
        evento.setNomeCriadorEvento(nomeCriadorEvento);
        evento.setInicioDoEvento(dataInicioEvento.getText().toString());
        evento.setFimDoEvento(dataFimEvento.getText().toString());
        evento.setHoraInicio(horaInicioEvento.getText().toString());
        evento.setHoraFim(horaFimEvento.getText().toString());
        evento.setDescricaoDoEvento(descricaoEvento.getText().toString());
        evento.setLatitude(latitude);
        evento.setLongitude(longitude);
        evento.setEventoOn(eventoOn);

        new DataBaseDAO().saveEvento(getActivity(), evento);
    }
}