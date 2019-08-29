package com.marlonn.devmov2;

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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.marlonn.devmov2.DAO.DataBaseDAO;
import com.marlonn.devmov2.model.Evento;

public class AdicionarEventoDialog extends AppCompatDialogFragment {

    private GoogleMap mMap;
    private Marker currentLocationMaker;
    private DatabaseReference mDatabase;
    private DatabaseReference idInfo;
    private String uidEvento;
    private Evento evento = new Evento();
    private boolean eventoOn = true;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth firebaseAuth;

    private EditText nomeDoEvento;
    private EditText dataInicioEvento;
    private EditText dataFimEvento;
    private EditText descricaoEvento;
    private double latitude;
    private double longitude;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.adicionar_evento, null);

        nomeDoEvento = view.findViewById(R.id.edt_nomeEvento);
        dataInicioEvento = view.findViewById(R.id.edt_dataInicioEvento);
        dataFimEvento = view.findViewById(R.id.edt_dataFimEvento);
        descricaoEvento = view.findViewById(R.id.edt_descricao);
        
        if(getArguments() != null){
            latitude = getArguments().getDouble("lat");
            longitude = getArguments().getDouble("long");
        }

        builder.setView(view);
        builder.setTitle("Adicionar Evento");
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

        evento.setIdEvento(uidEvento);
        evento.setNomeDoEvento(nomeDoEvento.getText().toString());
        evento.setDescricaoDoEvento(descricaoEvento.getText().toString());
        evento.setFimDoEvento(dataFimEvento.getText().toString());
        evento.setInicioDoEvento(dataInicioEvento.getText().toString());
        evento.setLatitude(latitude);
        evento.setLongitude(longitude);
        evento.setEventoOn(eventoOn);

        new DataBaseDAO().saveEvento(getActivity(), evento);
    }
}